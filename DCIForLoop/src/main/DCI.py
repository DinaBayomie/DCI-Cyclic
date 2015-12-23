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
from buildingCTree import CTreeController

from node import Node
import datetime
import math
import time 
from buildingLEL import LELController
from threading import *

from Queue import Queue 
#from asyncio import queues
#from DCIversion5.buildingLEL import LELController

ManagerCTree=None
ManagerLEL=None
leafsQueue=Queue()
#leafsQueue=queues.Queue()

endActivities = []
stillWorking=True
#lock= Lock()
"""Apply the cDCI Algorithm"""
class DCIController:
    start_time_program = time.time()
    S = []  # Unlabeled Log [timestamp , activity]
    T = dict() # heuristic data {activity:[min,avg,max]}
    M = dict() # Relation Matrix
    Predecessors = dict() # parent {activity:[[],[]]
    Loops = []  # list of loops in the business model
    startActivities = [] # based on the Relation Matrix, list to support multiple starts activities
     # based on the Relation Matrix, list to support multiple ends activities
    GivenConfidenceLevel = 0
    numOfEvents=0
    activitiesProb=dict()


    # for thread

    def __init__(self, S, T, M,Loops, Predecessors, startActivities,endActivitiesP,GivenConfidenceLevel):
        global ManagerCTree 
        ManagerCTree = CTreeController(S,T,M,Loops,Predecessors,startActivities,endActivitiesP)  
        global endActivities
        endActivities=endActivitiesP
        self.calculate_activity_probability(S)
        global ManagerLEL
        ManagerLEL=LELController(GivenConfidenceLevel,self.activitiesProb,len(S))
    def trunc(self, number, digit=4):
        return (math.floor(number * pow(10, digit) + 0.5)) / pow(10, digit)
    def calculate_activity_probability(self, sequence):
        sequnceLength = len(sequence)
        appearances = dict()
        for curr in sequence:
            if( appearances.get(curr[1])==None):
                appearances[curr[1]] = 1.0
            else:
                appearances[curr[1]] = appearances[curr[1]] + 1.0
        for a in appearances:
            value = self.trunc(appearances[a] / sequnceLength, 4)
            self.activitiesProb[a] = value
    
    def applyDCI(self):# define 2 threads and join them in main thread, then call buildingforlist
        start_time_algorithm= time.clock()
        pt=ProducerThread()
        ct=ConsumerThread()
        pt.start()
        ct.start()
        pt.join()
        ct.join()
        end_time_algorithm = time.clock()
        print("Algorithm execution time of threads:  %s seconds " % (end_time_algorithm - start_time_algorithm))
        ManagerLEL.ConstructLELForListNodes(ManagerCTree.tracesLeafs)
        numOfCases=len(ManagerCTree.root.children)
        ManagerLEL.setNumOfCases(numOfCases)
        ManagerLEL.buildTraceLogs()
        end_time_algorithm = time.clock()        
        print("Algorithm execution time :  %s seconds " % (end_time_algorithm - start_time_algorithm))
        print "event logs"
        #ManagerCTree.build_CTree()
        #ManagerLEL.ConstructLELForListNodes(ManagerCTree.tracesLeafs)
        ManagerCTree.traces.display(ManagerCTree.root)
        i = 1
        for eLog in ManagerLEL.constructedTraces:
            eLog.write_traceLog_into_file_csv(i)
            eLog.write_traceLog_into_XML(i)
            eLog.prepare_traceLog(i)
            i+=1
        ##print other traces that have produces but not completely fit
        if (len(ManagerLEL.otherConstructedTraces) > 0):
            print "other event logs "
            for oeLog in ManagerLEL.otherConstructedTraces:
                oeLog.write_traceLog_into_file_csv(i, "otherEventLog_all/")
                #oeLog.write_traceLog_into_file_txt(i,otherDirectory)
                oeLog.write_traceLog_into_XML(i, "otherEventLog_all/")
                oeLog.prepare_traceLog(i)
                i+=1
        print("program execution time : %s seconds " % (time.time() - start_time_algorithm))
        



# for threading 
class ProducerThread(Thread):
    def run(self):
        global ManagerCTree
        global leafsQueue
        global stillWorking
        sequence=ManagerCTree.S
        for symbol in sequence:
            ##print symbol
            nodes=ManagerCTree.buildCTreeUsingSymbol(symbol)
            #print symbol
            if(symbol[1] in endActivities):
                for n in nodes:
                    leafsQueue.put(n)
        #lock.acquire()
        stillWorking=False
        #lock.release()


class ConsumerThread(Thread):
    def run(self):
        global ManagerLEL
        global leafsQueue
        global stillWorking
        
        while(stillWorking or  not leafsQueue.empty()):
            if not leafsQueue.empty():
                node = leafsQueue.get()
                leafsQueue.task_done()
                #print "cons:",node.eventIdentifier
                ManagerLEL.ConstructLELsForNode(node)
                
       
