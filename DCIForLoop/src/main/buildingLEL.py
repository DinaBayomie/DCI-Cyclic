'''
This code is copyrighted to Dina Bayomie @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt

@author:  Dina Bayomie
'''

from tree import Tree
from traceLog import TraceLog
from branch import Branch
from loop import Loop

import datetime
import math
import time 


class LELController:
    """Building the labeled event log process controller"""
    GivenConfidenceLevel = 0
    activitiesProb = dict()  # check to small letter
    numOfEvents = 0
    
    numOfCases = 0

    tracesLeafs = []
    checkedLeafs = []
    eventLogsDic = dict()#<key :LEL id,value:list of nodes added in traces tree>
    eventLogsIdForCases = dict()#<key : case id , value [ids used for this case in eventLogsDic ]
    currentCaseBranch = []
    constructedTraces = []#right traces
    otherConstructedTraces = []#that may missing some events or cases
    totalBranchesConfidenceLevel = 0.0  # 1.0
    branch = 1
    eventLogID = 1
    
    def __init__(self,GivenConfidenceLevel,activitiesProb,numOfEvents):
        self.GivenConfidenceLevel = GivenConfidenceLevel
        self.activitiesProb = activitiesProb
        self.eventLogsDic = dict()
        self.checkedLeafs = []
        self.numOfEvents = numOfEvents
        self.branchId = 1
        self.eventLogID = 1
        
    def setNumOfCases(self,numOfCases):
        self.numOfCases = numOfCases

    def trunc(self, number, digit=4):
        return (math.floor(number * pow(10, digit) + 0.5)) / pow(10, digit)
    
    def distinctListObjects(self, listObjects):
        distinctObjects = []
        for n in listObjects:
            if(n not in listObjects):
                distinctObjects.append(n)
        return distinctObjects 
    '''Start building the event logs based on the nodes that reach the end activity -- it will be called in a different thread as a consumer'''
    def ConstructLELsForNode(self,leafNode):
        nodes = leafNode.branchNodes
        caseId = leafNode.caseId
        branch = Branch(self.branchId, caseId, nodes) 
        self.branchId += 1
        self.checkedLeafs.append(leafNode)
        duplicated = []
        if(len(self.eventLogsDic) == 0): # when its the first node to come
            newLEL = []
            newLEL.append(branch)
            self.eventLogsDic[self.eventLogID] = newLEL
            self.eventLogID+=1
            
        # handle coming\ following cases
        else:
            currentEventLogIds = [x for x in self.eventLogsDic.keys()] #this line to avoid the error of being change in the iterator object :D
            newLELs = []
            for eLogID in currentEventLogIds:
                if(self.eventLogsDic.get(eLogID) == None):
                    continue
                branches = self.eventLogsDic[eLogID]
                conflictBranches = self.checkRedundantBranches(branch, branches) # do
                if(len(conflictBranches) > 0): # if there are branches that violate the Checks
                    cb = self.getBranch(conflictBranches,caseId)
                    if(cb != None):
                        diff = set(branch.eventIdentifierList) - (set(cb.eventIdentifierList)) # what in the new branch not in the old branch
                        if(len(diff) == 0):
                            continue
                        if(set(branch.eventIdentifierList).issuperset(set(cb.eventIdentifierList))):
                            self.eventLogsDic[eLogID].remove(cb)
                            self.eventLogsDic[eLogID].append(branch)
                            continue
                        elif(set(cb.eventIdentifierList).issuperset(set(branch.eventIdentifierList))):
                            continue
                    newLEL = list(set(branches) - set(conflictBranches))
                    newLEL.append(branch)
                    newLELs.append(newLEL)
                    self.eventLogsDic[self.eventLogID] = newLEL
                    self.eventLogID+=1
                else:
                    self.eventLogsDic[eLogID].append(branch)
            duplicated=self.checkEventLogExistance(newLELs)
            if(len(duplicated)>0):#(not isdicclean):
                for elogid in duplicated:
                    if(self.eventLogsDic.has_key( elogid)):
                        del self.eventLogsDic[elogid]

    '''@return True if branch is from the same case of any of the branches or has common timestamp event , False otherwise '''       
   
    """This method check the reduance events with the log, by hanlding both the case and event identifier voilation
    @param newBranch new branch
    @param logBranches : log branches
    @ return Confilct branches """
    def checkRedundantBranches(self, newBranch, logBranches):
        reduandantBranches = []
        for rb in range(len(logBranches) - 1,-1,-1):
            intersect = list(set(logBranches[rb].eventIdentifierList).intersection(set(newBranch.eventIdentifierList)))
            if len(intersect) != 0:
                reduandantBranches.append(logBranches[rb])  
            elif(logBranches[rb].caseId == newBranch.caseId):
                reduandantBranches.append(logBranches[rb])  
        #reduandantBranches = self.distinctListObjects(reduandantBranches)

        return reduandantBranches 
    '''This method get the branch that crossponding to the given caseId'''
    def getBranch(self,branches,caseId):
        for b in branches: #branches:
            if(b.caseId == caseId):
                return b
        return None
    
    '''try to reduce the duplication that may occurs while creating the logs'''
    '''new event log is a set of the branches that included in the log so by that it both checks the event identifiers and cases'''

    def checkEventLogExistance(self,LELs):
        dublicated = []
        unique=0
        for newEventLog in LELs:
            for elogId in self.eventLogsDic:
               if(newEventLog ==self.eventLogsDic[elogId] and unique==0):
                   unique+=1
               else:
                    if(set(newEventLog).issuperset(set(self.eventLogsDic[elogId]))):
                        dublicated.append(elogId)  
        duplicated=self.distinctListObjects(dublicated)
        return dublicated
           
    ''''For building trace objects only and categorizing between complete and noisy logs'''
    def buildTraceLogs(self):
        #print "total num of branches ", self.branchId
        #print "total number of branches combinations ", len(self.eventLogsDic)
        for elogId in self.eventLogsDic:
            branches = self.eventLogsDic[elogId]
            traceLogObject = TraceLog(elogId,branches,self.activitiesProb)
            if(len(branches) == self.numOfCases and len(traceLogObject.events) == self.numOfEvents and traceLogObject.confidenceLevel >= self.GivenConfidenceLevel):
                self.constructedTraces.append(traceLogObject)
            else:
                self.otherConstructedTraces.append(traceLogObject)
            
    def ConstructLELForListNodes(self,tracesLeafs):
        
        notCheckedLeafs = list(set(tracesLeafs) - set(self.checkedLeafs))
        
        #duplicateEL = []
        for leaf in notCheckedLeafs:#self.traces.get_leafs(self.root):
            self.ConstructLELsForNode(leaf)
          
                     
    #def ConstructLELForListNodes(self,tracesLeafs):
        
    #    notCheckedLeafs=list(set(tracesLeafs) - set(self.checkedLeafs))
        
    #    duplicateEL = []
    #    for leaf in notCheckedLeafs:#self.traces.get_leafs(self.root):
    #        nodes = leaf.branchNodes
    #        caseId = leaf.caseId
    #        branch = Branch(self.branchId, caseId, nodes) 
    #        self.branchId += 1
            
    #        currentEventLogIds = [x for x in self.eventLogsDic.keys()]
    #        for eLogID in currentEventLogIds:#tempCaseBranches:
    #            branches = self.eventLogsDic[eLogID]
    #            conflictBranches = self.checkRedundantBranches(branch, branches)                
    #            if(len(conflictBranches) > 0):
    #                newEvents = list(set(branches) - set(conflictBranches))
    #                newEvents.append(branch)
    #                #duplicateEL=self.checkEventLogExistance(newEvents,self.eventLogsIdForCases,caseId)
    #                self.eventLogsDic[self.eventLogID] = newEvents
    #                self.eventLogID+=1
    #            else:
    #                self.eventLogsDic[eLogID].append(branch)
                    
                     
