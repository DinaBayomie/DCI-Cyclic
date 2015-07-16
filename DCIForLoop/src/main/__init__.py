'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt
'''

import sys
import math
#from tree import Tree
#from branch import Branch
#from traceLog import TraceLog
from algorithm import Algorithm
from loop import Loop
import os
import shutil
import csv 
# to calculate the processing time of the algorithm
import time 
#start run time of program
start_time_program = time.time()

from py4j.java_gateway import JavaGateway 

gateway = JavaGateway()
dicStart = gateway.entry_point.getStartActivityName()
startActivity = []
for k in dicStart :
    startActivity.append(k)

print 'startActivity', startActivity

# get the RelationShip
dic = gateway.entry_point.getMatrixRelations()
M = dict()
for k in dic :
    M[k] = dict()
    for n in dic[k]:
        M[k][n] = dic[k][n]

# get the predecessors
dicArr = gateway.entry_point.getAllPredecessors()#getAllParents()# as fun name change
                                                 #dicArr=gateway.entry_point.getPredecessors()
Parents = dict()
for k in dicArr :
    Parents[k] = []
    for i in range(0,len(dicArr[k])):
        n = dicArr[k][i]
        x = []
        for j in range(0,len(n)):
            x.append(n[j])
        Parents[k].append(x)

# get loops elements[[l1],[l2]]
#dicArr=gateway.entry_point.getLoops()
#Loops=[]
#for i in range(0,len(dicArr)):
#        n=dicArr[i]
#        x=[]
#        for j in range(0,len(n)):
#            x.append(n[j])
#        Loops.append(x)
#print 'Loops : ',Loops

# Loops as a list of loopconnection object that contains (list<string>
# elements,List<string> strarts, List <ends> ends)
dicArr = gateway.entry_point.getLoops()
Loops = []
for i in range(0,len(dicArr)):
        n = dicArr[i]
        elements = []
        starts = []
        ends = []
        for x in dicArr[i].getElements():
            elements.append(x.getLabel())
        for x in dicArr[i].getStarts():
            starts.append(x.getLabel())
        for x in dicArr[i].getEnds():
            ends.append(x.getLabel())
        l = Loop(dicArr[i].getId(),elements,starts,ends)
        Loops.append(l)
print 'Loops : ',Loops[0].printLoop()

#print M
gateway.shutdown()
print "Parents " , Parents
S = [] # Extracted unlabeled Sequence from File d directly
'''
#T = {'A':[2,5,10],'B':[3,5,10],'C':[4,7,9],'D':[5,7,12]}
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'none'},'B':{'A':'none','B':'xor','C':'xor','D':'seq'},'C':{'A':'none','B':'xor','C':'xor','D':'seq'},'D':{'A':'none','B':'none','C':'none','D':'xor'}}
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'none'},'B':{'A':'none','B':'xor','C':'and','D':'seq'},'C':{'A':'none','B':'and','C':'xor','D':'seq'},'D':{'A':'none','B':'none','C':'none','D':'xor'}}
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'seq','E':'none'},'B':{'A':'none','B':'xor','C':'and','D':'and','E':'seq'},'C':{'A':'none','B':'and','C':'xor','D':'and','E':'seq'},'D':{'A':'none','B':'and','C':'and','D':'xor','E':'seq'},'E':{'A':'none','B':'none','C':'none','D':'none','E':'xor'}}
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'none','E':'none'},'B':{'A':'none','B':'xor','C':'and','D':'and','E':'seq'},'C':{'A':'none','B':'and','C':'xor','D':'seq','E':'none'},'D':{'A':'none','B':'and','C':'none','D':'xor','E':'seq'},'E':{'A':'none','B':'none','C':'none','D':'none','E':'xor'}}
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'none','E':'none'},'B':{'A':'none','B':'xor','C':'xor','D':'xor','E':'seq'},'C':{'A':'none','B':'xor','C':'xor','D':'seq','E':'none'},'D':{'A':'none','B':'xor','C':'none','D':'xor','E':'seq'},'E':{'A':'none','B':'none','C':'none','D':'none','E':'xor'}}

#XOR complex 
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'none','E':'none'},'B':{'A':'none','B':'xor','C':'xor','D':'xor','E':'seq'},'C':{'A':'none','B':'xor','C':'xor','D':'seq','E':'none'},'D':{'A':'none','B':'xor','C':'none','D':'xor','E':'seq'},'E':{'A':'none','B':'none','C':'none','D':'none','E':'xor'}}
#And complex
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'seq','E':'none','F':'none'},
#   'B':{'A':'none','B':'xor','C':'and','D':'and','E':'seq','F':'none'},
#   'C':{'A':'none','B':'and','C':'xor','D':'and','E':'and','F':'seq'},
#   'D':{'A':'none','B':'and','C':'and','D':'xor','E':'and','F':'seq'},
#   'E':{'A':'none','B':'none','C':'and','D':'and','E':'xor','F':'seq'},
#   'F':{'A':'none','B':'none','C':'none','D':'none','E':'none','F':'xor'}}
M={'A':{'A':'xor','B':'seq','C':'seq','D':'none','E':'none'},
   'B':{'A':'none','B':'xor','C':'and','D':'seq','E':'none'},
   'C':{'A':'none','B':'and','C':'xor','D':'and','E':'seq'},
   'D':{'A':'none','B':'none','C':'and','D':'xor','E':'seq'},
   'E':{'A':'none','B':'none','C':'none','D':'none','E':'xor'}}


# to be addded in bp code as petri net modell
##combine
#M={'A':{'A':'xor','B':'seq','C':'none','D':'seq','E':'none','F':'none','G':'none','H':'none'},
#   'B':{'A':'none','B':'xor','C':'seq','D':'xor','E':'none','F':'none','G':'none','H':'none'},
#   'C':{'A':'none','B':'none','C':'xor','D':'xor','E':'seq','F':'none','G':'none','H':'none'},
#   'D':{'A':'none','B':'xor','C':'xor','D':'xor','E':'seq','F':'none','G':'none','H':'none'},
#   'E':{'A':'none','B':'none','C':'none','D':'none','E':'xor','F':'seq','G':'seq','H':'none'},
#   'F':{'A':'none','B':'none','C':'none','D':'none','E':'none','F':'xor','G':'and','H':'seq'},
#   'G':{'A':'none','B':'none','C':'none','D':'none','E':'none','F':'and','G':'xor','H':'seq'},
#   'H':{'A':'none','B':'none','C':'none','D':'none','E':'none','F':'none','G':'none','H':'none'}}
#nested
#M={'A':{'A':'xor','B':'seq','C':'seq','D':'none','E':'none','F':'none','G':'none','H':'none'},
#   'B':{'A':'none','B':'xor','C':'xor','D':'seq','E':'seq','F':'none','G':'xor','H':'none'},
#   'C':{'A':'none','B':'xor','C':'xor','D':'xor','E':'xor','F':'none','G':'seq','H':'none'},
#   'D':{'A':'none','B':'none','C':'xor','D':'xor','E':'and','F':'seq','G':'xor','H':'none'},
#   'E':{'A':'none','B':'none','C':'xor','D':'and','E':'xor','F':'seq','G':'xor','H':'none'},
#   'F':{'A':'none','B':'none','C':'xor','D':'none','E':'none','F':'xor','G':'xor','H':'seq'},
#   'G':{'A':'none','B':'xor','C':'none','D':'xor','E':'xor','F':'xor','G':'xor','H':'seq'},
#   'H':{'A':'none','B':'none','C':'none','D':'none','E':'none','F':'none','G':'none','H':'none'}}

#Parents={'A': [], 'C': ['A'], 'B': ['A'], 'D': ['B','C']}
#nested
#Parents={'A': [], 'B': ['A'], 'C': ['A'], 'D': ['B'], 'E': ['B'], 'F': ['D','E'], 'G': ['C'], 'H': ['G','F']}
#combine
#Parents={'A': [], 'B': ['A'], 'C': ['B'], 'D': ['A'], 'E': ['C','D'], 'F': ['E'], 'G': ['E'], 'H': ['G','F']}
#XOR complex
#Parents={'A': [], 'C': ['A'], 'B': ['A'], 'D': ['C'],'E':['B','D']}
#Parents={'A': [], 'C': ['A'], 'B': ['A'], 'D': ['A'],'E':['B','D','C']}
#and complex
Parents={'A': [], 'C': ['A'], 'B': ['A'], 'D': ['A'],'E':['B'],'F':['E','D','C']}
startActivity="A"
#M={'start':{'start':0,'A':1,'B':0,'C':0,'D':0,'End':0},'A':{'start':0,'A':0,'B':0.5,'C':0.5,'D':0,'End':0},'B':{'start':0,'A':0,'B':0,'C':0,'D':1,'End':0},'C':{'start':0,'A':0,'B':0,'C':0,'D':1,'End':0},'D':{'start':0,'A':0,'B':0,'C':0,'D':0,'End':0},'End':{'start':0,'A':0,'B':0,'C':0,'D':0,'End':0}}
#M={'start':{'start':0,'A':1,'B':0,'C':0,'D':0,'E':0,'End':0},'A':{'start':0,'A':0,'B':0.3,'C':0.3,'D':0.3,'E':0,'End':0},'B':{'start':0,'A':0,'B':0,'C':0.3,'D':0.3,'E':0.3,'End':0},'C':{'start':0,'A':0,'B':0.3,'C':0,'D':0.3,'E':0.3,'End':0},'D':{'start':0,'A':0,'B':0.3,'C':0.3,'D':0,'E':0.3,'End':0},'E':{'start':0,'A':0,'B':0,'C':0,'D':0,'E':0,'End':1},'End':{'start':0,'A':0,'B':0,'C':0,'D':0,'E':0,'End':0}}
'''
GivenConfidenceLevel = 0#.3
''' removing folders '''
txtDirectory = "labeledEventLog_txt/"
csvDirectory = "labeledEventLog_csv/"
xesDirectory = "labeledEventLog_xes/"
otherDirectory = "otherEventLog_all/"
if os.path.exists(txtDirectory):
    shutil.rmtree(txtDirectory)
if os.path.exists(csvDirectory):
    shutil.rmtree(csvDirectory)
if os.path.exists(xesDirectory):
    shutil.rmtree(xesDirectory)
if os.path.exists(otherDirectory):
    shutil.rmtree(otherDirectory)

print "processing input ....."
print "reading unlabeled event log"
print "model ",M
print "start Activity : ",startActivity

'''reading and preparing event log input 1'''
filename = sys.argv[1]
splitarr = filename.split('.')
extension = splitarr[len(splitarr) - 1]
print extension
print filename
timestamps = []
if (extension == 'txt'):
    fin = open(sys.argv[1], 'r')
    for line in fin:
        tupleLog = line.strip()
        if len(tupleLog) > 0:
            record = tupleLog.split(';')
            S.append(record)    
            
    fin.close()
    print 's',S


elif(extension == 'csv'):
    fin = open(sys.argv[1], "r")
    reader = csv.DictReader(fin)
    fields = reader.fieldnames 
    print fields   
    for row in reader:
        event = []
        for i in range(0, len(fields)):
            event.append(row[reader.fieldnames[i]])
        S.append(event) 
        
    fin.close()
    print 's',S


# reading and preparing T input 2
print "reading Activity execution times metadata"
T = dict()
fin = open(sys.argv[2], "r")
reader = csv.DictReader(fin)
rownum = 0
fields = reader.fieldnames
print fields
for row in reader:

        heuristic = []
        for i in range(1, len(reader.fieldnames)):
            heuristic.append(float(row[reader.fieldnames[i]]))
        T[row[reader.fieldnames[0]]] = heuristic   

fin.close()
print 'T' ,T

rs = int(sys.argv[3])
if (rs > 0):
    GivenConfidenceLevel = rs


start_time_algorithm = time.clock()
print "start apply labeling algorithm"
alg = Algorithm(S,T,M,Loops,Parents,startActivity,GivenConfidenceLevel)       
alg.apply_algorithm()
end_time_algorithm = time.clock()
print "event logs"

i = 1
for eLog in alg.constructedTraces:
   #if(i == 1):
        #eLog.write_traceLog_into_file_csv(i)
        #eLog.write_traceLog_into_file_txt(i)
    eLog.write_traceLog_into_XML(i)
    eLog.prepare_traceLog(i)
    i+=1
#print other traces that have produces but not completely fit
if (len(alg.otherConstructedTraces) > 0):
    print "other event logs "
    for oeLog in alg.otherConstructedTraces:
        #oeLog.write_traceLog_into_file_csv(i,otherDirectory)
        #oeLog.write_traceLog_into_file_txt(i,otherDirectory)
        oeLog.write_traceLog_into_XML(i,otherDirectory)
        oeLog.prepare_traceLog(i)
        i+=1

print"program execution time : %s seconds " % (time.time() - start_time_program)
print"Algorithm execution time :  %s seconds " % (end_time_algorithm - start_time_algorithm)
