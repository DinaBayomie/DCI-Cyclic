'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt
'''


class Branch:
    '''
    classdocs
    '''
    def __init__(self, identifier,caseId,nodes):
        self.__identifier=identifier
        self.__caseId=caseId
        self.__nodes=nodes
        #self.__confidenceLevel=confidenceLevel
        self.__timestampList=self.__setTimestamp()
        self.__eventIdentifierList=self.__seteventIdentifierList()
        #self.__nonConflictBranches=[]# all branches that applicable with this branch and his parents 
        #self.__parent=parent    
    @property
    def identifier(self):
        return self.__identifier
    
    @property
    def caseId(self):
        return self.__caseId
    
    @property
    def nodes(self):
        return self.__nodes
    
    
    @property
    def timestampList(self):
        return self.__timestampList
    @property
    def eventIdentifierList(self):
        return self.__eventIdentifierList
    '''
    @property
    def confidenceLevel(self):
        return self.__confidenceLevel
    
    @property
    def parent(self):
        return self.__parent

    
    @property
    def nonConflictBranches(self):
        return self.__nonConflictBranches
    
    def add_child(self, node):
        self.__nonConflictBranches.append(node)
    '''
    def __seteventIdentifierList(self):
        eventIdentifierL=[]
        for n in self.__nodes:
            eventIdentifierL.append(n.eventIdentifier)
        #eventIdentifierL=list(sorted(eventIdentifierL))
        return eventIdentifierL   
     
    def __setTimestamp(self):
        timestampL=[]
        for n in self.__nodes:
            if(n.timestampDatetime != None):
                timestampL.append(n.timestampDatetime)
            else:
                timestampL.append(n.timestamp)
        timestampL=list(sorted(timestampL))
        return timestampL
    
    def display(self):
        print 'branch # ',self.__identifier,' ',self.__caseId , ' ' , self.__confidenceLevel
        print 'branch Nodes :',''.join(str(i.timestamp+":"+i.activity+" ") for i in self.__nodes)
        
    def display_nodes(self):   
        print 'branch id :',self.identifier,' cid: ',self.__caseId,'    ',"nodes : ",' '.join(str(i.timestamp+":"+i.activity+" ") for i in self.__nodes)#,' Confidence :', self.__confidenceLevel
    