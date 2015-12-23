# contain buiding CTree only 
'''
This code is copyrighted to Dina Bayomie @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt

@author:  Dina Bayomie
'''
'''
This class to control building the CTree with both tree structure and path list
'''
from tree import Tree
from traceLog import TraceLog
from branch import Branch
from loop import Loop

import datetime
import math
import time 

start_time_program = time.time()

class CTreeController:
    '''
    classdocs
    '''
    # inputs
    S = []  # Unlabeled Log [timestamp , activity]
    T = dict() # heuristic data {activity:[min,avg,max]}
    M = dict() # Relation Matrix
    Predecessors = dict() # parent {activity:[[],[]]
    Loops = []  # list of loops in the business model
    startActivity = [] # based on the Relation Matrix, list to support multiple starts activities
    endActivity = [] # based on the Relation Matrix, list to support multiple ends activities

    # required fields for building the tree
    traces = Tree()    # CTree
    tracesLeafs = [] # to store leaves only
    root = None  # root of traces\cases tree [tree]
    numOfCases = 0
    activitiesNotCheckBranches = dict() # Contains the Max out of range events for each activity {
                                        # activity:[event_id]}
    LoopElements = [] # contains all the activities that included in the acyclic behaviour
    pathsList = dict() # contains all paths with crosspounding nodes
    seqAndMatrix = dict()

    # for threading use
    eventIdentifier = 1               
    labelCaseId = 1
    nodeIdentifier = 1

    def __init__(self, S, T, M,Loops, Predecessors, startActivities,endActivities):
        self.M = M
        self.Predecessors = Predecessors
        self.T = T
        self.S = S
        self.Loops = Loops
        self.mergeLoops() # to fill loopelements
        self.startActivity = startActivities
        self.endActivity = endActivities
        self.traces.add_node(1,1,0,0) # add root
        self.root = self.traces.get_root()
        self.activitiesNotCheckBranches = dict()
        self.pathsList = dict()
        self.seqAndMatrix = self.prepare_seq_and_matrix()

    '''utilities'''   
    '''this function to merge all loops elements'''
    def mergeLoops(self):
        for l in self.Loops:
            self.LoopElements = self.LoopElements + l.elements

    '''To improve declaration of seq and parallel relation, and improve search in event allocation '''
    def prepare_seq_and_matrix(self):
        seqAndActivities = dict()
        for m in self.M:
            for mm in self.M[m]:
                if(self.M[m][mm] == "seq" or self.M[m][mm] == "and"):
                    if( seqAndActivities.get(mm)==None):
                        seqAndActivities[mm] = []
                    seqAndActivities[mm].append(m)
        return seqAndActivities

    #####################################''''''''''''''########################################
    '''Building Ctree Methods '''

    def IsRunningLoop(self,eventActivity,BranchActivities):
        currentLoop = self.RunningLoop(eventActivity,BranchActivities)
        if(eventActivity in currentLoop):
            return False
        return True
    
    '''Get the running loop from the branches'''    # i change return into yield and make all in the if of finding the activity within loop to be check if it in inner and outer or only inner
    def GetRunningLoop(self,eventActivity,BranchActivities):
        loop = None
        loops=[]
        for l in self.Loops:
            if(eventActivity in l.elements):
                loop = l
                
                if(loop == None):
                    loops.append(BranchActivities)
                    continue
        
                intersect = list(set(loop.starts).intersection(set(BranchActivities))) #check if loop start or not
                if(len(intersect) == 0):
                    loops.append( BranchActivities)
                    continue
                startIndex = -1
                for s in loop.starts:
                    if(s not in BranchActivities):
                        continue
                    try:
                        temp = (len(BranchActivities) - 1) - BranchActivities[::-1].index(s) # get the index of the last occurence of an start loop activity
                        if(temp > startIndex):
                            startIndex = temp
                    except ValueError:
                            startIndex = -1
                            print  ("error : there is no such activity [ ",s," ] within branch  ")
                            print (ValueError)
                if(startIndex == -1):
                    loops.append(  BranchActivities)
                    continue
                endIndex = -1
                for e in loop.ends:
                    if(e not in BranchActivities):
                        continue
                    try:
                        temp = (len(BranchActivities) - 1) - BranchActivities[::-1].index(e)# get the index of the last occurence of an end loop activity
                        if(temp > endIndex):
                            endIndex = temp
                        break
                    except ValueError:
                            endIndex = -1
                            print  ("error : there is no such activity [ ",s," ] within branch  ")
                            print (ValueError)
                if(endIndex > startIndex):
                    loops.append( BranchActivities[endIndex:])
                    continue
                currentLoop = BranchActivities[startIndex:] # cut list to get running loop from the start activity till rest
                loops.append( currentLoop)
        return loops
        

    '''calculate given probability of  node per branch   -- check if all is avg'''
    '''Try to figure anther way to handle the calculation'''
    def calculate_percentage(self, calcPool, highestPriority='avg'):
        calcPrecentage = dict()
        noOfPNodes = len(sum(calcPool.values(), []))
        otherHeuristic = calcPool['other'] 
        noOfHighestPriority = len(calcPool[highestPriority])
        noOfOtherHeuristic = len(otherHeuristic)
        totalNumber = noOfPNodes * noOfPNodes
        if(noOfPNodes == 1):
            totalNumber = 1#2.0
        totalNumber *= 1.0
        if(noOfOtherHeuristic > 0):
            for aNode in calcPool[highestPriority]:
                calcPrecentage[aNode] = ((noOfPNodes + 1.0) / totalNumber)
            for oNode in otherHeuristic:
                calcPrecentage[oNode] = ((noOfPNodes - (noOfHighestPriority * 1.0 / noOfOtherHeuristic * 1.0)) / totalNumber) * 1.0
        else:
            for a in calcPool[highestPriority]:
                calcPrecentage[a] = ((noOfPNodes) / totalNumber)
        return calcPrecentage   

    '''check candiate parents based on model and the heuristic calculations from the paths list not based on leaves '''
    def check_possible_branches_based_on_Model(self,symbol):
        symbolActivity = symbol[1]#.lower()
        avgArr = []
        other = []
        # Check the existence of the activity in the given model
        if(self.M.get(symbolActivity) == None):
            print ("This activity (",symbolActivity ,") does not exist in the bussiness model")
            return dict()
        # check if its a start of a new case
        if(symbolActivity in self.startActivity):
            avgArr.append(self.root)
        else: 
            symbolTimestamp = symbol[0]
            # Get the heuristic info
            metadataTime = self.T.get(symbolActivity)#.lower())
            avg = metadataTime[1]
            minR = metadataTime[0] - 1
            maxR = metadataTime[2] + 1
            # when have SD
            #minR = avg - metadataTime[1] - 1
            #maxR = avg + metadataTime[1] + 1

            # 1- Use the list to get which nodes should be checked based on
            # seqand matrix
            seqAndForEventList = self.seqAndMatrix[symbolActivity]
            if(len(seqAndForEventList) == 0):
                print ("This activity (",symbolActivity ,") is independent in this model")
                return dict()
            AcceptedNodes = []
            for path in self.pathsList.keys():
                #for seqAnd in seqAndForEventList:
                    #if(path.endswith(seqAnd)):
                if(path.endswith(tuple(seqAndForEventList))):
                    AcceptedNodes.extend(self.pathsList[path])
            #1.1 check each accepted Node
            for node in AcceptedNodes:#self.tracesLeafs:#self.traces.get_leafs(self.root):#contain last added nodes
                                      #in tree
                # 2-1 check if node is in event activity out of range
                if(self.activitiesNotCheckBranches.get(symbolActivity)!=None and node.eventIdentifier in self.activitiesNotCheckBranches[symbolActivity]):
                    continue

                # Get parent node by checking the existance of predecessor
                # with branch and of course for the running version of loop
                # to calculate the Heuristic
                # 2- 2 get the current running loop [ from start till last
                # element in branch for now  ] 
                #check yield again 
                #currentLoop = node.branchActivities
                #if(symbolActivity in self.LoopElements):
                #    currentLoop = self.GetRunningLoop(symbolActivity,node.branchActivities)
                symbolLoops=[]
                symbolLoops.append(node.branchActivities)
                if(symbolActivity in self.LoopElements):
                     symbolLoops = self.GetRunningLoop(symbolActivity,node.branchActivities)
                for currentLoop in symbolLoops:
                    # 2-2.1 check if i exist in the curent loop that condition is especially for handling and relation
                   # should handle this later cant because of self loop need to be rewrite in case of ||
                    #if(symbolActivity in currentLoop):
                    #    continue

                    # 2-3 check existence of the predecessors in current loop, and
                    # return value
                    modelPredecessors = self.Predecessors[symbolActivity]
                    flag = 0
                    toBreak = False
                    modelparentNodes = dict()
                    for p in range(0, len(modelPredecessors)):
                        pred = modelPredecessors[p]
                        if(len(pred) == 1):# element of an xor
                           if(pred[0] in currentLoop):
                                modelparentNodes.update(self.traces.get_node_in_branch(node, pred[0]))
                        else: # elements in and relation
                            interesectedList = list(set(pred).intersection(set(currentLoop)))
                            if(len(interesectedList) == len(pred)):
                                for k in range(0,len(pred)):
                                    modelparentNodes.update(self.traces.get_node_in_branch(node, pred[k]))
                
                    if(len(modelparentNodes) == 0):
                        #print 'unable to add this event',symbolActivity,' in this branch'
                        continue
                    maxIndex = max(modelparentNodes.keys())     
                    modelParentNode = modelparentNodes[maxIndex]    
                
                    #2-4 calcuate heuristic and categorize the node based on
                    #calcuation
                    diff = 0 
                    if(symbolTimestamp.isdigit()) :  # handling time units
                        diff = int(symbolTimestamp) - int(modelParentNode.timestamp)
                    else:# handling time format # to be changes take timeformat as an input
                        try:
                            symbolTimestampDatetime = datetime.datetime.strptime(symbolTimestamp,'%m/%d/%Y  %H:%M')
                            nodeDateTime = modelParentNode.timestampDatetime #datetime.datetime.strptime(node.timestampDatetime,'%m/%d/%Y %I:%M:%S %p')
                            secDiff = symbolTimestampDatetime - nodeDateTime
                            diff = secDiff.total_seconds()#/60
                        except ValueError:
                            print  ("error in parsing datetime from string ")
                            print (ValueError)
                    #print "diff: ",diff," min: ",minR," max: ",maxR
                    if(diff == avg):                
                        avgArr.append(node)
                    elif(diff > minR and diff < maxR):
                        other.append(node)
                    elif (diff > maxR):# to handle out of range
                        if( self.activitiesNotCheckBranches.get(symbolActivity)==None):
                            self.activitiesNotCheckBranches[symbolActivity] = []
                        self.activitiesNotCheckBranches[symbolActivity].append(modelParentNode.eventIdentifier)
        calcPool = {'avg':avgArr, 'other':other}
        return calcPool
        
    '''Return all possible nodes based on model and heuristic calculation'''
    def filter_possible_cases(self, symbol):
       
        calcPool = self.check_possible_branches_based_on_Model(symbol)
        if(len(calcPool) == 0):
            return dict()
        possibleNodes = self.calculate_percentage(calcPool, 'avg')
        return possibleNodes   
         
    '''building tree for given unlabeled event log and distribute the event to cases tree '''
    def build_CTree(self):
        eventIdentifier = 1               
        labelCaseId = 1
        nodeIdentifier = 1
        for symbol in self.S:
            #print "event id",eventIdentifier," event : ",symbol[0],":",symbol[1]
            
            dictionary = self.filter_possible_cases(symbol) # get the possible parents
            
            numPossibleBranches = len(dictionary) # used to get case percentage [check that later]
            activity = symbol[1]#.lower()
            for pn in dictionary : # pn : parent node
                
                caseId = pn.caseId
                if(caseId == 0): # declare new node
                    caseId = labelCaseId
                    labelCaseId = labelCaseId + 1        
                percentage = dictionary[pn]  

                # handle datetime and time unit
                timestamp = 0
                timestampDatetime = None
                if(symbol[0].isdigit()):
                    timestamp = symbol[0]
                else:
                    try:
                        timestampDatetime = datetime.datetime.strptime(symbol[0],'%m/%d/%Y  %H:%M')
                        timestamp = eventIdentifier
                    except ValueError:
                        print  ("error in parsing datetime from string ")
                        print (ValueError)
                
                # list contains all activities within this branch
                branchActivities = [] 
                branchActivities = [x for x in pn.branchActivities]
                branchActivities.append(activity)
                 
                # list contains all nodes within this branch
                branchNodes = [] 
                branchNodes = [x for x in pn.branchNodes]

                                               
                casePercentage = 1.0 / numPossibleBranches
                
                branchEventIdentifiers = []
                branchEventIdentifiers = [x for x in pn.branchEventIdentifiers]
                branchEventIdentifiers.append(eventIdentifier)    
                # declare new node for the event per each parent node
                symbolNode = self.traces.add_node(percentage, casePercentage, eventIdentifier, timestamp, activity, caseId, pn, timestampDatetime, branchActivities,nodeIdentifier,branchNodes,branchEventIdentifiers)
                symbolNode.branchNodes.append(symbolNode)

                if(pn in self.tracesLeafs):
                    self.tracesLeafs.remove(pn)
                if(symbolNode not in self.tracesLeafs):
                    self.tracesLeafs.append(symbolNode)

                # handle paths list , increment path in the list and also the
                # node
                path = ''.join(branchActivities)
                if( self.pathsList.get(path)==None):
                    self.pathsList[path] = []
                self.pathsList[path].append(symbolNode)
                nodeIdentifier = nodeIdentifier + 1
            eventIdentifier = eventIdentifier + 1

    def apply_CTree_algorithm(self):
        print ("Algorithm version : Building CTree only ")
        print ("Start building CTree")
        start_time_build_tree = time.clock()
        self.build_CTree()
        print ("Finish building CTree")
        self.numOfCases = len(self.root.children)
        end_time_build_tree = time.clock()
        print("Execution time of building CTree:  %s seconds " % (end_time_build_tree - start_time_build_tree))
        print ("number of cases in given event log : ",self.numOfCases)
        self.traces.display(self.root)
        print ("no of leaves : ",len(self.tracesLeafs))


    '''building tree for given unlabeled event log and distribute the event to cases tree '''
    def buildCTreeUsingSymbol(self,symbol):
        #print "event id",self.eventIdentifier," event : ",symbol[0],":",symbol[1]
        symbolNodes=[]    
        dictionary = self.filter_possible_cases(symbol) # get the possible parents
            
        numPossibleBranches = len(dictionary) # used to get case percentage [check that later]
        activity = symbol[1]#.lower()
        for pn in dictionary : # pn : parent node
                
            caseId = pn.caseId
            if(caseId == 0): # declare new node
                caseId = self.labelCaseId
                self.labelCaseId = self.labelCaseId + 1        
            percentage = dictionary[pn]  

            # handle datetime and time unit
            timestamp = 0
            timestampDatetime = None
            if(symbol[0].isdigit()):
                timestamp = symbol[0]
            else:
                try:
                    timestampDatetime = datetime.datetime.strptime(symbol[0],'%m/%d/%Y  %H:%M')
                    timestamp = self.eventIdentifier
                except ValueError:
                    print  ("error in parsing datetime from string ")
                    print( ValueError)
 
            # list contains all activities within this branch
            branchActivities = [] 
            branchActivities = [x for x in pn.branchActivities]
            branchActivities.append(activity)
                 
            # list contains all nodes within this branch
            branchNodes = [] 
            branchNodes = [x for x in pn.branchNodes]
                                               
            casePercentage = 1.0 / numPossibleBranches
 
            branchEventIdentifiers = []
            branchEventIdentifiers = [x for x in pn.branchEventIdentifiers]
            branchEventIdentifiers.append(self.eventIdentifier)    
            # declare new node for the event per each parent node
            symbolNode = self.traces.add_node(percentage, casePercentage, self.eventIdentifier, timestamp, activity, caseId, pn, timestampDatetime, branchActivities,self.nodeIdentifier,branchNodes,branchEventIdentifiers)
            symbolNode.branchNodes.append(symbolNode)
            symbolNodes.append(symbolNode)
 
            if(pn in self.tracesLeafs):
                self.tracesLeafs.remove(pn)
            if(symbolNode not in self.tracesLeafs):
                self.tracesLeafs.append(symbolNode)

            # handle paths list , increment path in the list and also the
            # node
            path = ''.join(branchActivities)
            if( self.pathsList.get(path)==None):
                self.pathsList[path] = []
            self.pathsList[path].append(symbolNode)
            self.nodeIdentifier = self.nodeIdentifier + 1
        
        self.eventIdentifier = self.eventIdentifier + 1
        return symbolNodes