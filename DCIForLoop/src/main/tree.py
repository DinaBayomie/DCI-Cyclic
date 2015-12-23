'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt
'''
from node import Node
import math

(_ROOT, _DEPTH, _BREADTH) = range(3)


class Tree:

    def __init__(self):
        self.__nodes = {}

    @property
    def nodes(self):
        return self.__nodes

    def add_node(self, percentage ,casePercentage,eventIdentifier ,identifier ,activity = 'start', caseId= 0 , parent=None,timestampDatetime=None,branchActivities=[],nodeIdentifier='0 0',branchNodes=[],branchEventIdentifiers=[]):
        node = Node(percentage,casePercentage,eventIdentifier,identifier ,activity , caseId , parent,timestampDatetime,branchActivities,nodeIdentifier,branchNodes,branchEventIdentifiers )
        node_identifier=node.nodeIdentifier#identifier
        if parent is not None:
            parent.add_child(node)
        #else:
        self[node_identifier] = node
        return node

    def display(self, node, depth=_ROOT):
        children = node.children
        if depth == _ROOT:
            #print( node.identifier,' ','  ',node.activity,'  ', self.trunc(node.percentage, 5))
            print node.identifier,' ',node.eventIdentifier,'  ',node.activity,'  ', self.trunc(node.percentage, 5)

        else:
            #print ('\t'*depth, node.identifier,' ','  ',node.activity,'  ', self.trunc(node.percentage, 5))
            print '\t'*depth, node.identifier,' ',node.eventIdentifier,'  ',node.activity,'  ', self.trunc(node.percentage, 5)

        depth += 1
        for child in children:
            self.display(child, depth)  # recursive call


    def traverse(self, node, mode=_DEPTH):
        yield node
        queue = node.children
        while queue:
            yield queue[0]
            expansion = queue[0].children
            if mode == _DEPTH:
                queue = expansion + queue[1:]  # depth-first
            elif mode == _BREADTH:
                queue = queue[1:] + expansion  # width-first

    def __getitem__(self, key):
        return self.__nodes[key]

    def __setitem__(self, key, item):
        self.__nodes[key] = item
    
    def get_leafs(self, root):
        queue = root.children
        if(len(queue)==0):
            yield root
        
        while queue:
            expansion = queue[0].children
            if(len(expansion)==0):
                yield queue[0]
            queue = expansion + queue[1:]  # depth-first
    
    def get_root(self,identifier='0 0'):  
        return self.__getitem__(identifier)   
      
    def get_node(self,identifier):
        return self.__getitem__(identifier)  
   
    def get_branch_confidenceLevel(self, node,cl=1 ):
               
        parent = node.parent
        cl=cl*node.percentage     
        if(parent==self.get_root()):      
            return cl
        else:
            return self.get_branch_confidenceLevel(parent,cl)
        
    
    def get_branch_nodes(self, node , nodes):
               
        parent = node.parent
        nodes.append(node)     
        if(parent==self.get_root()):
            return nodes
        else:
            return self.get_branch_nodes(parent,nodes)     
    
    
    def check_existance_in_branch(self,branchNode,activityName):  
        if(activityName in branchNode.branchActivities):
            return True
        return False
        #rootNode=self.get_root()
        #while (branchNode!=rootNode):
        #    if(branchNode.activity==activityName):
        #        return True
        #    branchNode=branchNode.parent
        #return False
    
    def get_node_in_branch(self,branchNode,activityName):  
        rootNode=self.get_root()
        index=len(branchNode.branchActivities)
        while (branchNode!=rootNode):
            if(branchNode.activity==activityName):
                return {index:branchNode}
            branchNode=branchNode.parent
            index=index-1
        return None

    def get_node(self,nodeIdentifier):
        return self__getitem__(nodeIdentifier)

    def trunc(self,number,digit=4):
        return (math.floor(number*pow(10, digit)+0.5))/pow(10, digit) 


    #def add_node(self, percentage ,casePercentage,eventIdentifier ,identifier ,activity = 'start', caseId= 0 , parent=0,timestampDatetime=None,branchActivities=[],branchEventIdentifiers=[],nodeIdentifier='0 0'):
    #    node = Node(percentage,casePercentage,eventIdentifier,identifier ,activity , caseId , parent,timestampDatetime,branchActivities,branchEventIdentifiers,nodeIdentifier )
    #    node_identifier=node.nodeIdentifier#identifier
    #    if parent is not None:
    #        parent.add_child(node)
    #    #else:
    #    self[node_identifier] = node
    #    return node