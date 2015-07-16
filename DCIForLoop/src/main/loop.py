'''
This code is copyrighted to Dina Bayomie @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt

@author:  Dina Bayomie
'''

class Loop:
    """description of class"""
    def __init__(self, id,elements,starts,ends):
        self.__id=id
        self.__elements=elements
        self.__ends=ends
        self.__starts=starts

    @property
    def id(self):
        return self.__id    
    @property
    def elements(self):
        return self.__elements
    
    @property
    def starts(self):
        return self.__starts
    
    @property
    def ends(self):
        return self.__ends

    def printLoop (self):
        print ""
        print "------",self.id,"-----"
        print "starts :",self.starts
        print "ends : ",self.ends
        print "elements : ",self.elements
