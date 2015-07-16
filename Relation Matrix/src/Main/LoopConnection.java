package Main;

import java.util.ArrayList;
import java.util.Collection;

import org.jbpt.petri.Node;

public class LoopConnection {
	private int id;
	// transition names
	public ArrayList<String> elements;
	public ArrayList<String> starts;
	public ArrayList<String> ends;

	public LoopConnection(int _id, Collection<Node> _elements,
			Collection<Node> _starts, Collection<Node> _ends) {
		setId(_id);
		setElements(_elements);
		setStart(_starts);
		setEnds(_ends);
	}

	public LoopConnection(Loop l) {
		// TODO Auto-generated constructor stub
		setId(l.getId());
		setElements(l.getElements());
		setStart(l.getStarts());
		setEnds(l.getEnds());
	}

	public ArrayList<String> getElements() {
		return elements;
	}

	public void setElements(Collection<Node> elements) {
		this.elements = new ArrayList<String>();
		for (Node n : elements) {
			this.elements.add(n.getLabel());
		}
	}

	public ArrayList<String> getStarts() {
		return starts;
	}

	public void setStart(Collection<Node> starts) {
		this.starts = new ArrayList<String>();
		for (Node n : starts) {
			this.starts.add(n.getLabel());
		}
	}

	public ArrayList<String> getEnds() {
		return ends;
	}

	public void setEnds(Collection<Node> ends) {
		this.ends = new ArrayList<String>();
		for (Node n : ends) {
			this.ends.add(n.getLabel());
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
