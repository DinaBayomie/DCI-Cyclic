package Main;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import org.jbpt.bp.BehaviouralProfile;
import org.jbpt.petri.NetSystem;
import org.jbpt.petri.Node;
import org.jbpt.petri.Transition;

public class Relationship {
	private Hashtable<String, Hashtable> matrix;
	public Relationship (){
		setMatrix(new Hashtable<String, Hashtable>());
	}
	
	public Relationship (BehaviouralProfile<NetSystem, Node> bp1,
			BehaviouralProfile<NetSystem, Node> bpLoopFree,
			Set<Transition> transitions, Collection<Loop> loops){
		setMatrix(new Hashtable<String, Hashtable>());
	}
	
	public Hashtable<String, Hashtable> getMatrix() {
		return matrix;
	}
	public void setMatrix(Hashtable<String, Hashtable> matrix) {
		this.matrix = matrix;
	}

	
}
