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
    GivenConfidenceLevel = 0
    
    activitiesProb = dict()  # check to small letter
    traces = Tree()    
    tracesLeafs = []
    root = None  # root of traces\cases tree [tree]
    startActivity = ''
    numOfCases = 0
    activitiesNotCheckBranches=dict() # { activity:[event id]}


    def __init__(self, S, T, M, Predecessors, startActivity, GivenConfidenceLevel):
        self.M = M
        self.Predecessors = Predecessors
        self.T = T
        self.S = S
        self.startActivity = startActivity
        self.traces.add_node(1, 1, 0,0, 'start', 0, None)
        self.root = self.traces.get_root()
        self.calculate_activity_probability(self.S)
        self.GivenConfidenceLevel = GivenConfidenceLevel
        self.activitiesNotCheckBranches=dict()
   
    def trunc(self, number, digit=4):
        return (math.floor(number * pow(10, digit) + 0.5)) / pow(10, digit)
    
    '''Calculate probability of each activity withing giving sequence'''
    def calculate_activity_probability(self, sequence):
        sequnceLength = len(sequence)
        appearances = dict()
        for curr in sequence:
            if(not appearances.has_key(curr[1])):
                appearances[curr[1]] = 1.0
            else:
                appearances[curr[1]] = appearances[curr[1]] + 1.0
        for a in appearances:
            value = self.trunc(appearances[a] / sequnceLength, 4)
            self.activitiesProb[a] = value
    
    def distinct_possible_cases(self, possibleNodes):
        distinctNodes = []
        for n in possibleNodes:
            if(n not in distinctNodes):
                distinctNodes.append(n)
        return distinctNodes 
         
    '''Explore all possible cases for each symbol'''    
    def check_possible_branches_based_on_Model(self, eventActivity):#symbol):
        possibleNodes = dict() # {tree parent Node:[model predecessor node]}
        #eventActivity = symbol[1].lower()
        
        if(self.M.get(eventActivity)==None):# to check if activity is not exist in model[based on miner model], if so DCI ignores the activity 
            print activity        
            return possibleNodes

        if(eventActivity == self.startActivity):# if activity is the start activity    
            possibleNodes[self.root] = None
            
        else: 
            for l in self.tracesLeafs:#self.traces.get_leafs(self.root):#contain last added nodes in tree
                node = l
                # check if the current node is marked as out of range for this activity before 
                if(self.activitiesNotCheckBranches.has_key(eventActivity)):
                    if(node.eventIdentifier in self.activitiesNotCheckBranches[eventActivity]):
                        continue
                # traverse branch from current node till parent 
                while (node != self.root):
                    activity = node.activity#.lower()
                    relation = self.M.get(activity).get(eventActivity)
                    
                    #check if there is no relation object which will be rarel 
                    if(relation == None):
                        print "Relation object is none :: between ",activity,"and ", eventActivity
                        break
                    # if relation is none,i wont traverse the branch , instead ignore it totally 
                    elif(relation.lower() == "none"):                        
                        print "For Trace : this relation is none :: between ",activity,"and ", eventActivity
                        break
                    elif(relation.lower() == "xor"):                        
                        print "For Trace : this relation is XOR :: between ",activity,"and ", eventActivity
                        node = node.parent
                        continue
                    
                    # atwal we a5teer part 
                    elif(relation.lower() == "seq"):
                        # to be changed ::: after change predecessor list from BP object 
                        # what implemented here has something wrong :(
                        print "For Trace : this relation is Sequence  :: between ",activity,"and ", eventActivity
                        arrPredecessor = self.Predecessors[eventActivity]
                        flag = 0
                        if(len(arrPredecessor) == 1):
                            possibleNodes[node] = None
                            break
                        for j in range(0, len(arrPredecessor)):
                            if(flag == 1):
                                break
                            for i in range(1, len(arrPredecessor)):
                                if i == j :
                                    continue
                                rel = self.M.get(arrPredecessor[j]).get(arrPredecessor[i])
                                if(rel == 'and'):
                                    f1 = self.traces.check_existance_in_branch(node, arrPredecessor[j])
                                    f2 = self.traces.check_existance_in_branch(node, arrPredecessor[i])
                                    if(f1 and f2):
                                        possibleNodes[node] = None
                                        flag = 1
                                        break
                                elif(rel == 'xor'):
                                    possibleNodes[node] = None
                        break
                    
                    elif(relation.lower() == "and"):
                        print "For Trace : this relation is and :: between ",activity,"and ", eventActivity

                        isExist = self.traces.check_existance_in_branch(node, eventActivity)
                        if(not isExist):
                            modelSeqNode = None
                            # to get predecessor of and gate  
                            arrPredecessor = self.Predecessors[activity]
                            for p in arrParents:# suppose to be 1 and only one node 
                                modelSeqNode = self.traces.get_existed_activity_in_branch(node, p)
                            possibleNodes[node] = modelSeqNode
                        node = node.parent
                        continue
        return possibleNodes            

    '''Heuristic calculation method [min, avg , max]'''
    def check_possible_branches_based_on_heuristics(self, symbol, possibleNodes):
        symbolTimestamp = symbol[0]
        activity=symbol[1].lower()
        avgArr = []
        other=[]
        metadataTime = self.T.get(activity)
        if(len(possibleNodes)==0):
            return dict()
        for n in possibleNodes:
            node = n
            if(possibleNodes[n] != None):# to get gate predecessor [and gate]
                node = possibleNodes[n]
            if(n == self.root):
                avgArr.append(n)
                break    
            diff=0 
            if(symbolTimestamp.isdigit()) :  # handling time units
                diff = int(symbolTimestamp) - int(node.timestamp)
            else:# handling time format # to be changes take timeformat as an input
                try:
                    symbolTimestampDatetime=datetime.datetime.strptime(symbolTimestamp,'%m/%d/%Y  %H:%M')
                    nodeDateTime=node.timestampDatetime #datetime.datetime.strptime(node.timestampDatetime,'%m/%d/%Y  %I:%M:%S %p') 
                    secDiff=symbolTimestampDatetime-nodeDateTime
                    diff= secDiff.total_seconds()#/60                    
                except ValueError:
                    print  "error in parsing datetime from string "
                    print ValueError
            
            avg=metadataTime[0]
            minR=avg-metadataTime[1]+1
            maxR=avg+metadataTime[1]+1
            if(diff==avg):                
                avgArr.append(n)
            elif(diff >minR and diff < maxR):
                other.append(n)
            elif (diff>maxR):# to handle out of range 
                if(not self.activitiesNotCheckBranches.has_key(activity)):
                    self.activitiesNotCheckBranches[activity]=[]
                self.activitiesNotCheckBranches[activity].append(n.eventIdentifier);
        calcPool = {'avg':avgArr, 'other':other}
        return calcPool
    
    
    '''calculate given probability of  node per branch   -- check if all is avg'''
    def calculate_percentage(self, calcPool, highestPriority='avg'):
        calcPrecentage = dict()
        noOfPNodes = len(sum(calcPool.values(), []))
        #otherHeuristic = list(set(sum(calcPool.values(), [])) - set(calcPool[highestPriority]))  # to gather min and max set
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
        
        possibleCasesFromM = self.check_possible_branches_based_on_Model(symbol[1].lower())
        if(len(possibleCasesFromM )== 0):
            return dict()
        calcPool = self.check_possible_branches_based_on_heuristics(symbol, possibleCasesFromM)
        possibleNodes = self.calculate_percentage(calcPool, 'avg')
        
        return possibleNodes   

     
    '''building tree for given unlabeled event log and distribute the event to cases tree '''
    # add fun to remove all keys that have 1 branch from dic
    def build_branches_tree(self):
        eventIdentifier=0               
        labelCaseId = 1
        for symbol in self.S:
            dictionary = self.filter_possible_cases(symbol)
            numPossibleBranches = len(dictionary)
            eventIdentifier=eventIdentifier+1
            print "event id",eventIdentifier
            activity=symbol[1].lower()
            for n in dictionary :
                caseId = n.caseId
                if(caseId == 0):
                    caseId = labelCaseId
                    labelCaseId = labelCaseId + 1        
                percentage = dictionary[n]  
                timestamp=0
                timestampDatetime=None
                if(symbol[0].isdigit()):
                    timestamp=symbol[0]
                else:
                    try:
                        timestampDatetime=datetime.datetime.strptime(symbol[0],'%m/%d/%Y  %H:%M')
                        #timestamp=timestampDatetime.hour
                        timestamp=eventIdentifier
                    except ValueError:
                        print  "error in parsing datetime from string "
                        print ValueError
                symbolNode = self.traces.add_node(percentage, 1.0 / numPossibleBranches,eventIdentifier ,timestamp, activity, caseId, n,timestampDatetime)
                if(n in self.tracesLeafs):
                    self.tracesLeafs.remove(n)
                if(symbolNode not in self.tracesLeafs):
                    self.tracesLeafs.append(symbolNode)


      

    def getBranch(self,branches,caseId):
        for b in range(len(branches) - 1,-1,-1): #branches:
            if(branches[b].caseId == caseId):
                return branches[b]
        return None
    
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
