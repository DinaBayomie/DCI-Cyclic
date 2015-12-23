'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt
'''

import csv
import os
import xml.etree.ElementTree as ET
from datetime import datetime

class TraceLog:
    '''
    classdocs
    '''


    def __init__(self, identifier,cases,ActivitiesProb):
        self.__identifier=identifier
        self.__cases=cases
        self.__events=self.set_events()
        self.__branchesIds=self.set_branches_ids()
        self.__confidenceLevel=self.calculate_confidence(ActivitiesProb)
        self.__timestamps=self.set_log_timestamp()
    @property
    def identifier(self):
        return self.__identifier
    
    
    @property
    def cases(self):
        return self.__cases
    
    @property
    def confidenceLevel(self):
        return self.__confidenceLevel
    
    @property
    def events(self):
        return self.__events
    
    @property
    def branchesIds(self):
        return self.__branchesIds
    
    @property
    def timestamps(self):
        return self.__timestamps
    
    def set_branches_ids(self):
        ids=[]
        for b in self.__cases:
            ids.append(b.identifier)
        sortedIds=sorted(ids)
        return sortedIds
    
    def display_trace(self):
        print ('trace id:',self.__identifier, self.__confidenceLevel)
        for i in self.__cases:
            i.display_nodes()     
    
    def set_events(self):
        events=[]
        for c in self.__cases:
            events=events+c.nodes
        events=sorted(events, key=lambda Node: Node.timestamp) 
        return events
    
    def prepare_traceLog(self,i=0):
        if(i==0):
            i=self.identifier
        print (i , 'Ranking Score :',self.__confidenceLevel)
        print (''.join(str(str(i.caseId)+':'+str(i.timestamp)+":"+i.activity+" ") for i in self.events))

        #print events
        print ('----------------------------')
    
    def set_log_timestamp(self):
        timestamps=[]
        for c in self.events:
            timestamps.append(c.timestamp)
        return timestamps
        
        
    '''calculate the confidence level of the trace as p(trace)=sum(p(branch))
    def calculate_confidence(self,totalBranchesConfidenceL):
        cl=0.0#1.0
        p=1.0/len(self.__cases)
        for branch in self.__cases:
            #cl=cl+branch.confidenceLevel
            temp=p*branch.confidenceLevel
            cl=cl+temp
        #cl= cl/totalBranchesConfidenceL  # dr.ahmed
        #cl=cl/len(self.__cases) #total/no of cases
        return cl
    '''
    def calculate_confidence(self,ActivitiesProb):
        confidenceLevel=0.0
        
        for c in self.cases:
            cEvents=c.nodes
            for index in range(1,len(cEvents)):
                e=cEvents[index]
                p=ActivitiesProb.get(e.activity)
            #print 'activity' ,e.caseId,e.timestamp, e.activity
            #print 'prob of activity',p
            #cp=e.casePercentage
                cp= e.percentage
            #print 'case percentage', cp
                confidenceLevel= confidenceLevel +(p*cp)
        #print '---------------------------------'  
        confidenceLevel=confidenceLevel/len(self.branchesIds) 
        return confidenceLevel 
    '''
    def calculate_confidence(self,ActivitiesProb,root):
        confidenceLevel=0.0
        
        for b in self.cases:
            confidenceLevel+=b.confidenceLevel
        #confidenceLevel = confidenceLevel/len(self.cases)
        return confidenceLevel 
    '''
    
    def write_traceLog_into_file_txt(self,i=0,directory="labeledEventLog_txt/"):
        if(i==0):
            i=self.identifier

        name=''.join(str(str(i)+" - RS- "+str(self.confidenceLevel)))
        filename=directory+name+".txt"
        #directory="labeledEventLog_txt/"
        if not os.path.exists(directory):
            os.makedirs(directory)
        #file_name=str('labelledEventLog'+str(self.identifier)+'.txt')
        fout = open(filename,'w')
        for i in self.__events:
            strNode=i.print_node()
            fout.write(strNode)
            fout.write("\n")
            #fout.writelines(strNode,)

        fout.close()
   
   
    
    
    def write_traceLog_into_file_csv(self,i=0,directory="labeledEventLog_csv/"):
        if(i==0):
            i=self.identifier

        name=''.join(str(str(i)+" - RS- "+str(self.confidenceLevel)))
        data=[]
        header = ['case id;timestamp;activity']
        events=[]
        for c in self.__cases:
            events=events+c.nodes
        events=sorted(events, key=lambda Node: Node.timestamp)  
        
        for e in events:
            data.append(str(e.caseId)+';'+str(e.timestamp)+";"+e.activity)
        #print events
        #print '----------------------------'
        filename=directory+name+".csv"
        #directory="labeledEventLog_csv/"
        if not os.path.exists(directory):
            os.makedirs(directory)
        #data = ['Me;You','293;219','54;13']
        f = open(filename,'w')
        w = csv.writer(f, delimiter = ',')
        w.writerows([x.split(';') for x in header])
        w.writerows([x.split(';') for x in data])

        f.close()
        
    def write_traceLog_into_XML(self,i=0,directory="labeledEventLog_xes/"):
        if(i==0):
            i=self.identifier

        name=''.join(str(str(i)+" - RS- "+str(self.confidenceLevel)))
        
        filename=directory+name+".xes"
        #directory="labeledEventLog_xes/"
        if not os.path.exists(directory):
            os.makedirs(directory)
           
         
        xmlRoot=ET.Element("log")
        # some declaration for xes attribute
        
        extensions=ET.fromstring('  <extension name=""/>')
        extensions=ET.fromstring('  <extension name="Time" prefix="time" uri="http://code.fluxicon.com/xes/time.xesext"/>')
        xmlRoot.append(extensions)   
        extensions=ET.fromstring('<extension name="Concept" prefix="concept" uri="http://code.fluxicon.com/xes/concept.xesext"/>')
        xmlRoot.append(extensions)   
        extensions=ET.fromstring('<extension name="Identity" prefix="identity" uri="http://www.xes-standard.org/identity.xesext"/>')
        xmlRoot.append(extensions) 
        extensions=ET.fromstring('<global scope="trace"><int key="identity:caseId" value="0"/></global>')
        xmlRoot.append(extensions)
        extensions=ET.fromstring('<global scope="event"><string key="concept:name" value=""/> <date key="time:timestamp" value="1970-01-01T01:00:00.000+01:00"/></global>')
        xmlRoot.append(extensions)
        
                
        for c in self.__cases:
            xmlTrace=ET.SubElement(xmlRoot, "trace")
            xmlId=ET.SubElement(xmlTrace,"int",{"key":"identity:caseId","value":str(c.caseId)})
            events=sorted(c.nodes, key=lambda Node: Node.timestamp) 
            
            for n in events:
                xmlElement=ET.SubElement(xmlTrace,"event")
                xmlActivity=ET.SubElement(xmlElement,"string",{"key":"concept:name","value":n.activity})
                if (isinstance(n.timestamp,datetime)):
                    xmlTimeStamp=ET.SubElement(xmlElement,"string",{"key":"time:timestamp","value":n.timestamp}) 
        
        xmlTree=ET.ElementTree(xmlRoot)
        #xmlTree.write(filename)
        output_file = open(filename, 'w' )
        output_file.write( '<?xml version="1.0"?>' )
        #output_file.write( ET.tostring( xmlRoot ) )
        xmlTree.write(filename)
        output_file.close()
        
       
        return 