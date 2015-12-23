package Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.jbpt.petri.Node;

public class Loop {

	private int id;
	// transition names
	private Collection<Node> elements;
	private Collection<Node> starts;
	private Collection<Node> ends;
	private Collection<Node> loopBranchElements;
	private Collection<Node> innerEnds;
	private boolean silentBranch;

	public Loop(int _id, Collection<Node> _elements, Collection<Node> _starts, Collection<Node> _ends,
			Collection<Node> _loopBranchElements, Collection<Node> _innerEnds) {
		setId(_id);
		setElements(_elements);
		setStart(_starts);
		setEnds(_ends);
		setLoopBranchElements(_loopBranchElements);
		setInnerEnds(_innerEnds);
	}

	public Collection<Node> getElements() {
		return elements;
	}

	public void setElements(Collection<Node> elements) {
		this.elements = elements;
	}

	public Collection<Node> getStarts() {
		return starts;
	}

	public void setStart(Collection<Node> starts) {
		this.starts = starts;
	}

	public Collection<Node> getEnds() {
		return ends;
	}

	public void setEnds(Collection<Node> ends) {
		this.ends = ends;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Node> getLoopBranchElements() {
		return loopBranchElements;
	}

	public void setLoopBranchElements(Collection<Node> loopBranchElements) {
		this.loopBranchElements = loopBranchElements;
		setSilentBranch();
	}

	public boolean isSilentBranch() {
		return silentBranch;
	}

	public void setSilentBranch() {
		this.silentBranch = true;
		for (Node e : this.loopBranchElements) {
			if (!e.getLabel().contains("tau")) {
				this.silentBranch = false;
				return;
			}
		}

	}

	public Collection<Node> getInnerEnds() {
		return innerEnds;
	}

	public void setInnerEnds(Collection<Node> innerEnds) {
		this.innerEnds = innerEnds;
	}

	public ArrayList<String> getElementsNames() {
		ArrayList<String> elements = new ArrayList<String>();
		for (Node n : this.elements) {
			elements.add(n.getLabel());
		}
		return elements;
	}

	public void print() {
		System.out.println("loop Start");
		for (Node n : this.starts) {
			System.out.print(n.getLabel() + " , ");
		}
		System.out.println("");
		System.out.println("loop element");
		for (Node n : this.elements) {
			System.out.print(n.getLabel() + " , ");
		}
		System.out.println("");
		System.out.println("loop end");
		for (Node n : this.ends) {
			System.out.print(n.getLabel() + " , ");
		}
	}
}
