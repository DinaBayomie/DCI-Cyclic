'''
This code is copyrighted to Dina Bayomie and Iman Helal @2015 Research work
Information System Department, Faculty of computers and Information System
Cairo University, Egypt
'''
from branch import Branch
import math

(_ROOT, _DEPTH, _BREADTH) = range(3)


class BranchTree:

    def __init__(self):
        self.__branches = {}

    @property
    def branches(self):
        return self.__branches

    def add_branch(self, identifier,caseId,nodes,confidenceLevel,branchParent=None):
        branch = Branch( identifier,caseId,nodes,confidenceLevel,branchParent )
        branch_identifier=branch.identifier
        if branchParent is not None:
            branchParent.add_child(branch)
        else:
            self[branch_identifier] = branch
        return branch
    
    def add_branch_object(self, branchObject,branchParent=None):
        return self.add_branch(branchObject.identifier, branchObject.caseId, branchObject.nodes, branchObject.confidenceLevel, branchParent)
        
    def display(self, branch, depth=_ROOT):
        children = branch.nonConflictBranches
        if depth == _ROOT:
            print branch.identifier,'-',branch.caseId#,'  ', self.trunc(branch.confidenceLevel, 5)

        else:
            print '\t'*depth, branch.identifier,'-',branch.caseId#,'  ', self.trunc(branch.confidenceLevel, 5)
            
        depth += 1
        for child in children:
            self.display(child, depth)  # noderecursive call


    def traverse(self, branch, mode=_DEPTH):
        yield branch
        queue = branch.nonConflictBranches
        while queue:
            yield queue[0]
            expansion = queue[0].nonConflictBranches
            if mode == _DEPTH:
                queue = expansion + queue[1:]  # depth-first
            elif mode == _BREADTH:
                queue = queue[1:] + expansion  # width-first


    def __getitem__(self, key):
        return self.__branches.get(key)#[key]

    def __setitem__(self, key, item):
        self.__branches[key] = item
    
    def get_leafs(self, root):
        leaves=[]
        queue = root.nonConflictBranches
        if(len(queue)==0):
            leaves.append(root)
            #return root
        
        while queue:
            expansion = queue[0].nonConflictBranches
            if(len(expansion)==0):
                #yield queue[0]
                leaves.append(queue[0])
            queue = expansion + queue[1:]  # depth-first
        return leaves
    
    def get_branches_of_case(self, root,caseId):
        caseBranches=[]
        if (root.caseId==caseId):
            caseBranches.append(root)
        else:
            queue= root.nonConflictBranches
            while(queue):
                nonCBranch=queue[0]
                if(nonCBranch.caseId==caseId):
                    caseBranches.append(nonCBranch)
                    queue = queue[1:] 
                else:
                    expansion = nonCBranch.nonConflictBranches
                    queue = queue[1:] +expansion
        return caseBranches     
        
      
    
    
    def get_root(self,identifier=0):  
        return self.__getitem__(identifier)   
      
    def get_bnode(self,identifier):
        return self.__getitem__(identifier)  
   
    def get_trace_branches(self, branch , branches):
        
        if(branch==self.get_root()):
            return branches
        branches.append(branch)  
        branch = branch.parent           
        return self.get_trace_branches(branch,branches)     
    
        
    def trunc(self,number,digit=4):
        return (math.floor(number*pow(10, digit)+0.5))/pow(10, digit) 