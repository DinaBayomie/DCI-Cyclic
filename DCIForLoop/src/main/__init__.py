'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt
'''

import sys
import math
from DCI import DCIController
from loop import Loop
import os
import shutil
import csv 
import time 
start_time_program = time.time()
import profile
from py4j.java_gateway import JavaGateway 

gateway = JavaGateway()
dicStart = gateway.entry_point.getStartActivitiesName()
startActivity = []
for k in dicStart :
    startActivity.append(k)

dicEnd = gateway.entry_point.getEndActivitiesName()
endActivity = []
for k in dicEnd :
    endActivity.append(k)

# get the RelationShip
dic = gateway.entry_point.getMatrixRelations()
M = dict()
for k in dic :
    M[k] = dict()
    for n in dic[k]:
        M[k][n] = dic[k][n]

# get the predecessors
dicArr = gateway.entry_point.getAllPredecessors()
Parents = dict()
for k in dicArr :
    Parents[k] = []
    for i in range(0,len(dicArr[k])):
        n = dicArr[k][i]
        x = []
        for j in range(0,len(n)):
            x.append(n[j])
        Parents[k].append(x)

# Loops as a list of loopconnection object that contains (list<string>
# elements,List<string> strarts, List <ends> ends)
dicarr = gateway.entry_point.getLoops()
Loops = []
for i in range(0,len(dicarr)):
        n = dicarr[i]
        elements = []
        starts = []
        ends = []
        for x in dicarr[i].getElements():
            elements.append(x.getLabel())
        for x in dicarr[i].getStarts():
            starts.append(x.getLabel())
        for x in dicarr[i].getEnds():
            ends.append(x.getLabel())
        l = Loop(dicarr[i].getId(),elements,starts,ends)
        Loops.append(l)

gateway.shutdown()
GivenConfidenceLevel = 0
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

print("processing input .....")
print("reading unlabeled event log")
#print("model ",M)
#print("start Activity : ",startActivity)
#print("end Activity : ",endActivity)
'''reading and preparing event log input 1'''
filename = sys.argv[1]
splitarr = filename.split('.')
extension = splitarr[len(splitarr) - 1]
#print (extension)
#print( filename)
timestamps = []
S = []
if (extension == 'txt'):
    fin = open(sys.argv[1], 'r')
    for line in fin:
        tupleLog = line.strip()
        if len(tupleLog) > 0:
            record = tupleLog.split(';')
            S.append(record)    
            
    fin.close()
    print('events : ',len(S))


elif(extension == 'csv'):
    fin = open(sys.argv[1], "r")
    reader = csv.DictReader(fin)
    fields = reader.fieldnames 
    print(fields)
    for row in reader:
        event = []
        for i in range(0, len(fields)):
            event.append(row[reader.fieldnames[i]])
        S.append(event) 
        
    fin.close() 
    print('events : ',len(S))



# reading and preparing T input 2
print("reading Activity execution times metadata")
T = dict()
fin = open(sys.argv[2], "r")
reader = csv.DictReader(fin)
rownum = 0
fields = reader.fieldnames
print(fields)
for row in reader:

        heuristic = []
        for i in range(1, len(reader.fieldnames)):
            if(row[reader.fieldnames[i]] != None):
                heuristic.append(float(row[reader.fieldnames[i]]))
        T[row[reader.fieldnames[0]]] = heuristic   

fin.close()
#print ('T' ,T)
rs = int(sys.argv[3])
if (rs > 0):
    GivenConfidenceLevel = rs


start_time_algorithm = time.clock()
print("start apply labeling algorithm")

dcialg = DCIController(S,T,M,Loops,Parents,startActivity,endActivity,GivenConfidenceLevel)
dcialg.applyDCI()
end_time_algorithm = time.clock()
print("program execution time : %s seconds " % (time.time() - start_time_program))
