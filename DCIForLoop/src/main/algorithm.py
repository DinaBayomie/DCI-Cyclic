# contain buiding CTree only 

'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt

@author:  Dina Bayomie, Iman Helal
'''
from tree import Tree
from traceLog import TraceLog
from branch import Branch
from loop import Loop

import datetime
import math
import time 

start_time_program = time.time()

class Algorithm:
    '''
    classdocs
    '''
    S = []  # Extracted unlabeled Sequence from File directly [timestamp , activity]
    T = dict()
    M = dict()
    Parents = dict()
    Loops = []
    GivenConfidenceLevel = 0
    
    activitiesProb = dict()  # check to small letter
    traces = Tree()    
    tracesLeafs = []
    root = None  # root of traces\cases tree [tree]
    startActivity = ''
    numOfCases = 0
    activitiesNotCheckBranches = dict() # { activity:[event id]}
    LoopElements = []
    # related to constructing labeled logs

    eventLogsDic = dict()#<key :case id,value:list of nodes added in traces tree>
    eventLogsIdForCases = dict()#<key : case id , value [ids used for this case in eventLogsDic ]
    eventLogID = 1
    currentCaseBranch = []
    constructedTraces = []#right traces
    otherConstructedTraces = []#that may missing some events or cases
    branchId = 1
    #BranchConfidenceLevel = dict() # key leaf node : value calculated
    #confidence level p(leaf| branch)
    #Branches = []
   
    root = None  # root of traces\cases tree [tree]
    branchRoot = None  # root of traceLogs tree [branchTree]
    branches = []
    startActivity = ''
    totalBranchesConfidenceLevel = 0.0  # 1.0
    numOfCases = 0
    CasesBranches = dict()


    def __init__(self, S, T, M,Loops, Predecessors, startActivity, GivenConfidenceLevel):
        self.M = M
        self.Predecessors = Predecessors
        self.T = T
        self.S = S
        self.Loops = Loops
        self.mergeLoops()
        self.startActivity = startActivity
        self.traces.add_node(1, 1, 0,0, 'start', 0, None)
        self.root = self.traces.get_root()
        self.calculate_activity_probability(self.S)
        self.GivenConfidenceLevel = GivenConfidenceLevel
        self.activitiesNotCheckBranches = dict()

    '''utilities'''   
    def mergeLoops(self):
        for l in self.Loops:
            self.LoopElements = self.LoopElements + l.elements


    def trunc(self, number, digit=4):
        return (math.floor(number * pow(10, digit) + 0.5)) / pow(10, digit)
    
    '''Calculate probability of each activity withing giving sequence'''
    def calculate_activity_probability(self, sequence):
        sequnceLength = len(sequence)
        appearances = dict()
        for curr in sequence:
            if(not appearances.has_key(curr[1])):
                appearances[curr[1]] = 1.0
                #appearances[curr[1].lower()] = 1.0
            else:
                appearances[curr[1]] = appearances[curr[1]] + 1.0
                #appearances[curr[1].lower()] = appearances[curr[1].lower()] +
                #1.0
        for a in appearances:
            value = self.trunc(appearances[a] / sequnceLength, 4)
            self.activitiesProb[a] = value
    
    def distinct_possible_cases(self, possibleNodes):
        distinctNodes = []
        for n in possibleNodes:
            if(n not in distinctNodes):
                distinctNodes.append(n)
        return distinctNodes 
    
    #####################################''''''''''''''########################################

    '''Building Ctree'''
         
    '''
    Filtering process  
    @author:  Dina Bayomie,
    '''    
    def check_possible_branches_based_on_Model(self,symbol):
        #possibleNodes = dict() # {tree parent Node:[model predecessor node]}
        eventActivity = symbol[1]#.lower()
        avgArr = []
        other = []
        
        if(self.M.get(eventActivity) == None):# to check if activity is not exist in model[based on miner model], if so DCI
                                              # ignores the activity
            print "this activity (",eventActivity ,") does not exist in mode"
            #return possibleNodes
            return dict()

        #if(eventActivity == self.startActivity):# if activity is the start activity
        if(eventActivity in self.startActivity):# if activity is the start activity
            #possibleNodes[self.root] = None
            avgArr.append(self.root)
        else: 
            symbolTimestamp = symbol[0]
            metadataTime = self.T.get(symbol[1])#.lower())
            avg = metadataTime[1]
            minR = metadataTime[0] - 1
            maxR = metadataTime[2] + 1
            # when have SD
            #minR = avg - metadataTime[1] - 1
            #maxR = avg + metadataTime[1] + 1
            for l in self.tracesLeafs:#self.traces.get_leafs(self.root):#contain last added nodes in tree
                node = l
                # check if the current node is marked as out of range for this
                # activity before
                
                # traverse branch from current node till parent
                #nodeTraverseList = []
                while (node != self.root):
                    if(self.activitiesNotCheckBranches.has_key(eventActivity)):
                        if(node.eventIdentifier in self.activitiesNotCheckBranches[eventActivity]):
                         #  print "For Trace :: between
                         #  ",activity,":",node.eventIdentifier," and ",
                         #  eventActivity,"wont check out of
                         #  range",node.eventIdentifier
                            break
                        #Need rethinking
                        #elif(len(list(set(node.branchEventIdentifiers[2:]).intersection(set(self.activitiesNotCheckBranches[eventActivity])))) > 0):
                        #    break
                    activity = node.activity#.lower()
                    relation = self.M.get(activity).get(eventActivity)
                    
                    #check if there is no relation object which will be rarel
                    if(relation == None):
                        print "there is no defined Relation between ",activity,"and ", eventActivity
                        break
                    # if relation is none,i wont traverse the branch , instead
                    # ignore it totally
                    elif(relation.lower().find("ign") != -1):#==none_ignore" ):
                        #print "For Trace : this relation is none_ignore ::
                                                                                     #between ",activity,"and ", eventActivity
                        break
                    
                    elif(relation.lower() == "xor"):                        
                        #print "For Trace : this relation is XOR :: between
                        #",activity,"and ", eventActivity
                        #nt = node
                        #nodeTraverseList.append(nt)
                        node = node.parent
                        continue
                    elif(relation.lower().find("tra") != -1):# == "none_traverse"):
                       # print "For Trace : this relation is none_traverse ::
                                                                                    # between ",activity,"and ", eventActivity
                        #nt = node
                                                                                     #nodeTraverseList.append(nt)
                        node = node.parent
                        continue
                    
                    # atwal we a5teer part
                    elif(relation.lower() == "seq"):
                        # check heuristic even before predecessors
                        checkHeur = False
                        print "for trace : this relation is sequence ::between ",activity,"and ", eventActivity
                        # check the existance of event predecessors -that
                        # because of parallel case
                        arrPredecessor = self.Predecessors[eventActivity]
                        flag = 0
                        for j in range(0, len(arrPredecessor)):
                            if(len(arrPredecessor[j]) == 1):
                                if(self.traces.check_existance_in_branch(node, arrPredecessor[j][0])):
                                    checkHeur = True
                                    break
                            else:
                                andSet = arrPredecessor[j]
                                for k in range(0,len(andSet)):
                                    checkHeur = True
                                    if(not self.traces.check_existance_in_branch(node, andSet[k])):
                                        checkHeur = False
                                        break
                        # check Heuristic before considering node as a parent
                        # node
                        outOfRange = False
                        if(checkHeur):
                            diff = 0 
                            if(symbolTimestamp.isdigit()) :  # handling time units
                                diff = int(symbolTimestamp) - int(node.timestamp)
                            else:# handling time format # to be changes take timeformat as an input
                                try:
                                    symbolTimestampDatetime = datetime.datetime.strptime(symbolTimestamp,'%m/%d/%Y  %H:%M')
                                    nodeDateTime = node.timestampDatetime #datetime.datetime.strptime(node.timestampDatetime,'%m/%d/%Y %I:%M:%S %p')
                                    secDiff = symbolTimestampDatetime - nodeDateTime
                                    diff = secDiff.total_seconds()#/60
                                except ValueError:
                                    print  "error in parsing datetime from string "
                                    print ValueError
            
                            if(diff == avg):                
                                avgArr.append(node)
                            elif(diff > minR and diff < maxR):
                                other.append(node)
                            elif (diff > maxR):# to handle out of range
                                outOfRange = True
                                if(not self.activitiesNotCheckBranches.has_key(eventActivity)):
                                    self.activitiesNotCheckBranches[eventActivity] = []
                                self.activitiesNotCheckBranches[eventActivity].append(node.eventIdentifier)
                                # that wrong and very risky step to do because
                                # it leads
                                # to lose possibility
                                # to add child node of out of range node == to
                                # leaf that cause this
                                #if(len(nodeTraverseList) > 0):
                                #    for nT in nodeTraverseList:
                                #        self.activitiesNotCheckBranches[eventActivity].append(nT.eventIdentifier)
                                #nodeTraverseList = []
                                #print "in this event ",node.eventIdentifier,"
                                #its disabled"
                        if((eventActivity in self.LoopElements) and not outOfRange):# till more investigation
                           node = node.parent
                        else:
                            break
                    # Not implemented for loop yet
                    elif(relation.lower() == "and"):
                        #print "For Trace : this relation is and :: between
                        #",activity,"and ", eventActivity
                        # this condition will be changes based on element in
                        # loop or not
                        cont = False
                        if(eventActivity in self.LoopElements):
                            cont = self.IsRunningLoop(eventActivity,node.branchActivities)
                        else:
                            isExist = self.traces.check_existance_in_branch(node, eventActivity)
                            cont = False if isExist == True else True
                        #if(not isExist or Allow):
                        if(cont):
                            modelSeqNode = None
                            # to get predecessor of and gate
                            #arrPredecessor = self.Predecessors[activity]
                            #for p in arrPredecessor:# suppose to be 1 and only
                            #one node
                            #    modelSeqNode =
                            #    self.traces.get_existed_activity_in_branch(node,
                            #    p)
                            ##possibleNodes[node] = modelSeqNode
                        arrPredecessor = self.Predecessors[eventActivity]
                        flag = 0
                        toBreak = False
                        for j in range(0, len(arrPredecessor)):
                            if(len(arrPredecessor[j]) == 1):
                                modelSeqNode = self.traces.get_existed_activity_in_branch(node, arrPredecessor[j][0])
                                toBreak = True   
                            else:
                                andSet = arrPredecessor[j]
                                
                                for k in range(0,len(andSet)):
                                    modelSeqNode = self.traces.get_existed_activity_in_branch(node,andSet[k])
                                    if(modelSeqNode != None):
                                        toBreak = True
                                        break
                            if(toBreak):
                                break
                            diff = 0 
                            if(symbolTimestamp.isdigit()) :  # handling time units
                                diff = int(symbolTimestamp) - int(modelSeqNode.timestamp)
                            else:# handling time format # to be changes take timeformat as an input
                                try:
                                    symbolTimestampDatetime = datetime.datetime.strptime(symbolTimestamp,'%m/%d/%Y  %H:%M')
                                    nodeDateTime = modelSeqNode.timestampDatetime #datetime.datetime.strptime(node.timestampDatetime,'%m/%d/%Y %I:%M:%S %p')
                                    secDiff = symbolTimestampDatetime - nodeDateTime
                                    diff = secDiff.total_seconds()#/60
                                except ValueError:
                                    print  "error in parsing datetime from string "
                                    print ValueError
            
                        
                            if(diff == avg):                
                                avgArr.append(node)
                            elif(diff > minR and diff < maxR):
                                other.append(node)
                            elif (diff > maxR):# to handle out of range
                                if(not self.activitiesNotCheckBranches.has_key(eventActivity)):
                                    self.activitiesNotCheckBranches[eventActivity] = []
                                self.activitiesNotCheckBranches[eventActivity].append(node.eventIdentifier)
                                #if(len(nodeTraverseList) > 0):
                                #    for nT in nodeTraverseList:
                                #        self.activitiesNotCheckBranches[eventActivity].append(nT.eventIdentifier)
                                #nodeTraverseList = []
                        node = node.parent
                        continue
                    
        #return possibleNodes
        calcPool = {'avg':avgArr, 'other':other}
        return calcPool


    def IsRunningLoop(self,eventActivity,BranchActivities):
        loop = None
        for l in self.Loops:
            if(eventActivity in l.elements):
                loop = l
        if(loop == None):
            return False
        
        intersect = list(set(loop.starts).intersection(set(BranchActivities)))
        if(len(intersect) == 0):
            return False
        reversed = [z for z in BranchActivities]
        reversed.reverse()
        startIndex = -1
        for s in loop.starts:
            try:
                startIndex = (len(BranchActivities) - 1) - BranchActivities[::-1].index(s)
                break
            except ValueError:
                    startIndex = -1
                    print  "error : there is no such node [ ",s," ] within branch  "
                    print ValueError
        if(startIndex == -1):
            return False
        endIndex
        for e in loop.ends:
            try:
                endIndex = (len(BranchActivities) - 1) - BranchActivities[::-1].index(e)
                break
            except ValueError:
                    endIndex = -1
                    print  "error : there is no such node [ ",s," ] within branch  "
                    print ValueError
        if(endIndex > startIndex):
            return False
       # check from start index till end of list if event happened before or
       # not
        currentLoop = BranchActivities[startIndex:]
        if(eventActivity in currentLoop):
            return False
        return True
    '''Heuristic calculation method [min, avg , max]'''
    def check_possible_branches_based_on_heuristics(self, symbol, possibleNodes):
        symbolTimestamp = symbol[0]
        activity = symbol[1].lower()
        avgArr = []
        other = []
        metadataTime = self.T.get(activity)
        if(len(possibleNodes) == 0):
            return dict()
        for n in possibleNodes:
            node = n
            if(possibleNodes[n] != None):# to get gate predecessor [and gate]
                node = possibleNodes[n]
            if(n == self.root):
                avgArr.append(n)
                break    
            diff = 0 
            if(symbolTimestamp.isdigit()) :  # handling time units
                diff = int(symbolTimestamp) - int(node.timestamp)
            else:# handling time format # to be changes take timeformat as an input
                try:
                    symbolTimestampDatetime = datetime.datetime.strptime(symbolTimestamp,'%m/%d/%Y  %H:%M')
                    nodeDateTime = node.timestampDatetime #datetime.datetime.strptime(node.timestampDatetime,'%m/%d/%Y %I:%M:%S %p')
                    secDiff = symbolTimestampDatetime - nodeDateTime
                    diff = secDiff.total_seconds()#/60
                except ValueError:
                    print  "error in parsing datetime from string "
                    print ValueError
            
            avg = metadataTime[0]
            minR = avg - metadataTime[1] - 1
            maxR = avg + metadataTime[1] + 1
            if(diff == avg):                
                avgArr.append(n)
            elif(diff > minR and diff < maxR):
                other.append(n)
            elif (diff > maxR):# to handle out of range
                if(not self.activitiesNotCheckBranches.has_key(activity)):
                    self.activitiesNotCheckBranches[activity] = []
                self.activitiesNotCheckBranches[activity].append(n.eventIdentifier)
        calcPool = {'avg':avgArr, 'other':other}
        return calcPool
    
    
    '''calculate given probability of  node per branch   -- check if all is avg'''
    def calculate_percentage(self, calcPool, highestPriority='avg'):
        calcPrecentage = dict()
        noOfPNodes = len(sum(calcPool.values(), []))
        #otherHeuristic = list(set(sum(calcPool.values(), [])) -
        #set(calcPool[highestPriority])) # to gather min and max set
        otherHeuristic = calcPool['other'] 
        noOfHighestPriority = len(calcPool[highestPriority])
        noOfOtherHeuristic = len(otherHeuristic)
        #total = 0
        totalNumber = noOfPNodes * noOfPNodes
        if(noOfPNodes == 1):
            totalNumber = 1#2.0
        totalNumber *= 1.0
        if(noOfOtherHeuristic > 0):
            for aNode in calcPool[highestPriority]:
                calcPrecentage[aNode] = ((noOfPNodes + 1.0) / totalNumber)
                #total += calcPrecentage[aNode]
            for oNode in otherHeuristic:
                calcPrecentage[oNode] = ((noOfPNodes - (noOfHighestPriority * 1.0 / noOfOtherHeuristic * 1.0)) / totalNumber) * 1.0
                #total += calcPrecentage[oNode]
        else:
            for a in calcPool[highestPriority]:
                calcPrecentage[a] = ((noOfPNodes) / totalNumber)
                #total += calcPrecentage[a]
            
        return calcPrecentage   
    
    '''Return all possible nodes based on model and heuristic calculation'''
    def filter_possible_cases(self, symbol):
        
        #possibleCasesFromM =
        #self.check_possible_branches_based_on_Model(symbol[1].lower())
        calcPool = self.check_possible_branches_based_on_Model(symbol)
        if(len(calcPool) == 0):
            return dict()
        #calcPool = self.check_possible_branches_based_on_heuristics(symbol,
        #possibleCasesFromM)
        possibleNodes = self.calculate_percentage(calcPool, 'avg')
        
        return possibleNodes   

     
    '''building tree for given unlabeled event log and distribute the event to cases tree '''
    # add fun to remove all keys that have 1 branch from dic
    def build_branches_tree(self):
        eventIdentifier = 0               
        labelCaseId = 1
        for symbol in self.S:
            print "event id",eventIdentifier," event : ",symbol[0],":",symbol[1]
            dictionary = self.filter_possible_cases(symbol)
            numPossibleBranches = len(dictionary)
            eventIdentifier = eventIdentifier + 1
            activity = symbol[1]#.lower()
            for n in dictionary :
                caseId = n.caseId
                if(caseId == 0):
                    caseId = labelCaseId
                    labelCaseId = labelCaseId + 1        
                percentage = dictionary[n]  
                timestamp = 0
                timestampDatetime = None
                if(symbol[0].isdigit()):
                    timestamp = symbol[0]
                else:
                    try:
                        timestampDatetime = datetime.datetime.strptime(symbol[0],'%m/%d/%Y  %H:%M')
                        #timestamp=timestampDatetime.hour
                        timestamp = eventIdentifier
                    except ValueError:
                        print  "error in parsing datetime from string "
                        print ValueError
                branchActivities = []
                branchActivities = [x for x in n.branchActivities]
                branchActivities.append(activity)
                branchEventIdentifiers = []
                branchEventIdentifiers = [x for x in n.branchEventIdentifiers]
                branchEventIdentifiers.append(eventIdentifier)
                symbolNode = self.traces.add_node(percentage, 1.0 / numPossibleBranches,eventIdentifier ,timestamp, activity, caseId, n,timestampDatetime,branchActivities,branchEventIdentifiers)
                if(n in self.tracesLeafs):
                    self.tracesLeafs.remove(n)
                if(symbolNode not in self.tracesLeafs):
                    self.tracesLeafs.append(symbolNode)

    #def getBranch(self,branches,caseId):
    #    for b in range(len(branches) - 1,-1,-1): #branches:
    #        if(branches[b].caseId == caseId):
    #            return branches[b]
    #    return None
    

    #####################################''''''''''''''########################################

    '''Composite the labeled event logs '''
    '''Calculate confidence level for each branch in cases traces tree and also build the branches trace log tree'''
    # will change this function so instead of adding to tracelog tree it will
    # add branch to event log dict
    def calculate_confidence_level_branch(self):
        #self.CasesBranches = dict()
        self.branchId = 1
        tracesLeafs = sorted(self.tracesLeafs, key=lambda Node: Node.caseId) 
        eventLogsIdForCases = dict()# <key case id ,value [] of eventlog id used for this case>store ids used per
        # case so when start new case remove all un
                                            # used event log to decrease the
                                                                                # size
        isDicClean = False
        duplicateEL = []
        for leaf in tracesLeafs:#self.traces.get_leafs(self.root):
            nodes = []
            caseId = leaf.caseId
            # create branch
            if(leaf.parent != self.root):
                nodes = self.traces.get_branch_nodes(leaf, nodes)
            else:
                nodes.append(leaf)
            branch = Branch(self.branchId, caseId, nodes) 
            self.branchId += 1
            #print "case id : ",caseId," timestamp : ",branch.timestampList
            
            #handle first case
            if(branch.caseId == 1):
                newEvents = []
                newEvents.append(branch)
                isAdded = False
                for eLogID in self.eventLogsDic:
                    cb = self.getBranch(self.eventLogsDic[eLogID],caseId)
                    if(cb != None):
                        diff = list(set(branch.timestampList) - set(cb.timestampList))
                        if(len(diff) == 0):
                            isAdded = True
                            continue
                        if(set(branch.timestampList).issuperset(set(cb.timestampList))):
                            if(self.checkEventLogExistance(newEvents,self.eventLogsIdForCases,1)):
                                #del self.eventLogsDic[eLogID]
                                duplicateEL.append(eLogID)
                                continue
                            self.eventLogsDic[eLogID].remove(cb)
                            self.eventLogsDic[eLogID].append(branch)
                            isAdded = True
                            #if(self.checkEventLogExistance(self.eventLogsDic[eLogID],self.eventLogsIdForCases[1],1)):
                            #    del self.eventLogsDic[eLogID]
                            #    continue
                            continue
                if(not isAdded):
                    self.eventLogsDic[self.eventLogID] = newEvents
                    if(not self.eventLogsIdForCases.has_key(caseId)):
                        self.eventLogsIdForCases[caseId] = []
                    self.eventLogsIdForCases[caseId].append(self.eventLogID)
                    self.eventLogID+=1
                continue
            
            # handle comin\ following cases
            if(len(duplicateEL) > 0):#(not isDicClean):
                for eLogID in duplicateEL:
                    del self.eventLogsDic[eLogID]
                self.eventLogsIdForCases[caseId - 1] = [x for x in self.eventLogsDic.keys()]
                duplicateEL = []
                

            if(self.eventLogsIdForCases.has_key(caseId - 1)):
                currentEventLogIds = self.eventLogsIdForCases[caseId - 1]
            else:
                currentEventLogIds = [x for x in self.eventLogsDic.keys()]
            
            
            currentEventLogIds = list(sorted(currentEventLogIds))
            for eLogID in currentEventLogIds:#tempCaseBranches:
                branches = self.eventLogsDic[eLogID]
               
                conflictBranches = self.CheckViolatingBranches(branch, branches)                
                if(len(conflictBranches) > 0):
                    if(self.checkParentCasesExistence(conflictBranches,caseId)):
                        continue
                    cb = self.getBranch(conflictBranches,caseId)
                    if(cb != None):
                        diff = list(set(branch.timestampList) - set(cb.timestampList))
                        if(len(diff) == 0):
                            continue
                        if(set(branch.timestampList).issuperset(set(cb.timestampList))):
                            self.eventLogsDic[eLogID].remove(cb)
                            self.eventLogsDic[eLogID].append(branch)
                            continue
                        elif(set(cb.timestampList).issuperset(set(branch.timestampList))):
                            continue
                    newEvents = list(set(branches) - set(conflictBranches))
                    newEvents.append(branch)
                    #duplicateEL=self.checkEventLogExistance(newEvents,self.eventLogsIdForCases,caseId)
                    self.eventLogsDic[self.eventLogID] = newEvents
                    if(not self.eventLogsIdForCases.has_key(caseId)):
                        self.eventLogsIdForCases[caseId] = []
                    if(self.eventLogID not in self.eventLogsIdForCases[caseId]):
                        self.eventLogsIdForCases[caseId].append(self.eventLogID)
                    self.eventLogID+=1
                else:
                    self.eventLogsDic[eLogID].append(branch)
                    if(not self.eventLogsIdForCases.has_key(caseId)):
                        self.eventLogsIdForCases[caseId] = []
                    if(eLogID not in self.eventLogsIdForCases[caseId]):
                        self.eventLogsIdForCases[caseId].append(eLogID)  
    def checkParentCasesExistence(self,branches,caseId):
        for b in range(len(branches) - 1,-1,-1): #branches:
            if(branches[b].caseId in range(1,caseId)):
                return True
        return False
    def getBranch(self,branches,caseId):
        for b in range(len(branches) - 1,-1,-1): #branches:
            if(branches[b].caseId == caseId):
                return branches[b]
        return None
    def checkEventLogExistance(self,newEvents,eventLogsIdForCases,caseId):
        dublicated = []
        if(eventLogsIdForCases.has_key(caseId)):
            for elogId in eventLogsIdForCases[caseId]:
                if(set(newEvents).issuperset((self.eventLogsDic[elogId]))):
                    dublicated.append(elogId)  
        return dublicated
    '''@return True if branch is from the same case of any of the branches or has common timestamp event , False otherwise '''       
    def CheckViolatingBranches(self, selectedBranch, remainingBranches):
        #isSameTimestamp = False
        violatedBranches = []
        for rb in range(len(remainingBranches) - 1,-1,-1):
            #intersect =
            #list(set(remainingBranches[rb].timestampList).intersection(set(selectedBranch.timestampList)))
            intersect = list(set(remainingBranches[rb].eventIdentifierList).intersection(set(selectedBranch.eventIdentifierList)))
            #print 'selectedBranch.timestampList
            #',selectedBranch.eventIdentifierList
            #print "there is intersect : ",intersect
            if len(intersect) != 0:
                violatedBranches.append(remainingBranches[rb])  
            if(remainingBranches[rb].caseId == selectedBranch.caseId):
                violatedBranches.append(remainingBranches[rb])  
        violatedBranches = self.distinct_possible_cases(violatedBranches)

        return violatedBranches 
    def build_traceLog(self):
        print "total num of branches ", self.branchId
        print "total number of branches combinations ", len(self.eventLogsDic)
        traceLogId = 1
        for elogId in self.eventLogsDic:
            branches = []
            branches = self.eventLogsDic[elogId]
            '''traceLogObject = TraceLog(traceLogId,branches,self.activitiesProb,self.root)
            self.otherConstructedTraces.append(traceLogObject)
            traceLogId+=1'''
            #if(len(branches) == self.numOfCases):
            traceLogObject = TraceLog(traceLogId,branches,self.activitiesProb,self.root)
            #if(len(branches)==self.numOfCases and
            #len(traceLogObject.events)==len(self.S) and
            #traceLogObject.confidenceLevel >= self.GivenConfidenceLevel):
            if(len(traceLogObject.events) == len(self.S) and traceLogObject.confidenceLevel >= self.GivenConfidenceLevel):
                #print "the same no of cases"
                self.constructedTraces.append(traceLogObject)
            else:
                self.otherConstructedTraces.append(traceLogObject)
            traceLogId+=1
                    




    def apply_algorithm(self):
        print "Algorithm version : Building CTree only "
        print "build cases tree"
        start_time_build_tree = time.clock()
        self.build_branches_tree()
        self.numOfCases = len(self.root.children)
        end_time_build_tree = time.clock()
        print"Algorithm execution time time_build_tree :  %s seconds " % (end_time_build_tree - start_time_build_tree)

        print "number of cases in given event log : ",self.numOfCases
        self.traces.display(self.root)
        print "no of leaves : ",len(self.tracesLeafs)
        print "prepare labeled event logs "
        print "construct branches tree from cases tree"
        start_time_build_tree = time.clock()
        self.calculate_confidence_level_branch()
        end_time_build_tree = time.clock()
        print"Algorithm execution time build event log dic :  %s seconds " % (end_time_build_tree - start_time_build_tree)
        print "total num of branches ", self.branchId
        print "total number of branches combinations ", len(self.eventLogsDic)

        #self.traceLogsTree.display(self.branchRoot)
        print "compose event logs from branches tree"
        start_time_build_tree = time.clock()

        self.build_traceLog()
        end_time_build_tree = time.clock()
        print"Algorithm execution time build_traceLog :  %s seconds " % (end_time_build_tree - start_time_build_tree)


        self.constructedTraces = sorted(self.constructedTraces, key=lambda TraceLog: TraceLog.confidenceLevel, reverse=True)
        #print "number of labeled event logs :" , self.constructedTraces
