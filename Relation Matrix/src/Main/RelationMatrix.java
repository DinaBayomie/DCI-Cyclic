/***
 * This code is copyrighted to Dina Bayomie @2015 Research work
 * Information System Department, Faculty of computers and Information System
 * Cairo University, Egypt
	@author:  Dina Bayomie
 */

// need alot of changes to do and resturcting and more systemitic way in development

package Main;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jbpt.algo.graph.StronglyConnectedComponents;
import org.jbpt.bp.BehaviouralProfile;
import org.jbpt.bp.CausalBehaviouralProfile;
import org.jbpt.bp.RelSetType;
import org.jbpt.bp.construct.BPCreatorNet;
import org.jbpt.bp.construct.CBPCreatorNet;
import org.jbpt.petri.*;
import org.jbpt.petri.io.PNMLSerializer;

public class RelationMatrix {

	private BehaviouralProfile<NetSystem, Node> bpOrginial;
	private BehaviouralProfile<NetSystem, Node> bpLoopFree;
	private CausalBehaviouralProfile<NetSystem, Node> cbp2;
	// private java.util.List<Node> entities;
	private PetriNet pn;
	public RelationMatrix bpep = null;
	public ArrayList<String> startActivities = null;
	public ArrayList<String> endActivities = null;
	private NetSystem orginialModel = null;
	private NetSystem nloopFree = null;
	private Set<Transition> transitions = null;
	private Collection<Loop> pnLoops = null;
	private Hashtable<String, Hashtable> matrix = null;
	private Set<Node> elementsInLoops = null;
	private Dictionary<String, ArrayList<ArrayList<String>>> predecessors = null;

	public RelationMatrix(NetSystem n) {
		this.orginialModel = n;
		constructRelationMatrix();
	}

	public RelationMatrix(PetriNet pn) {
		this.orginialModel = new NetSystem(pn);
		this.pn = pn;
		constructRelationMatrix();
		predecessors = getPredecessors();
	}

	/**
	 * This Function construct the Relation Matrix based on the given Model.
	 * Also it initials the predecessors and the Loop list
	 */
	private void constructRelationMatrix() {

		orginialModel.loadNaturalMarking();
		startActivities = new ArrayList<String>();
		endActivities = new ArrayList<String>();
		Set<Transition> starts = orginialModel.getEnabledTransitionsAtMarking(orginialModel.getMarking());
		// to remove all silent activities from the start of the petri net 1
		// level only
		for (Transition t : starts) {
			if (t.getLabel().contains("tau")) {
				// to escape the places from the successors
				Collection<Node> succnodes = getModelSuccessors(t, new HashSet<Node>(), new HashSet<Node>(),
						new HashSet<Node>(), true);// orginialModel.getDirectSuccessors(orginialModel.getDirectSuccessors(t));
				for (Node n : succnodes) {
					startActivities.add(n.getLabel());
				}
			} else {
				startActivities.add(t.getLabel());
			}
		}

		Set<Transition> ends = orginialModel.getPresetTransitions(orginialModel.getSinkPlaces());
		for (Transition t : ends) {
			if (t.getLabel().contains("tau")) {
				// to escape the places from the successors
				Collection<Node> prenodes = getModelPredecessors(t, new HashSet<Node>(), new HashSet<Node>(),
						new HashSet<Node>(), true);// orginialModel.getDirectSuccessors(orginialModel.getDirectSuccessors(t));
				for (Node n : prenodes) {
					endActivities.add(n.getLabel());
				}
			} else {
				endActivities.add(t.getLabel());
			}
		}
		transitions = orginialModel.getTransitions();
		pnLoops = new HashSet<Loop>();
		elementsInLoops = new HashSet<Node>();

		// start to create RM
		setBpOrginial(BPCreatorNet.getInstance().deriveRelationSet(orginialModel));
		nloopFree = constructAcyclicModel(new NetSystem(pn));
		setBpLoopFree(new BehaviouralProfile<NetSystem, Node>(0));
		// if (nloopFree != null) {
		if (pnLoops.size() > 0) {
			nloopFree.loadNaturalMarking();
			setBpLoopFree(BPCreatorNet.getInstance().deriveRelationSet(nloopFree));
			pnLoops = ModifyLoops(pnLoops, orginialModel);
			matrix = getMatrixRelations(getBpOrginial(), getBpLoopFree(), transitions, pnLoops);
		} else {
			matrix = getMatrixRelations(getBpOrginial(), transitions);
		}
	}

	/**
	 * This method return the start activities for the given model
	 * 
	 * @return list of the start activities
	 */
	public ArrayList<String> getStartActivitiesName() {
		return startActivities;// .toLowerCase();
	}

	/**
	 * This method return the end Activities for the given model
	 * 
	 * @return list of the end activities
	 */
	public ArrayList<String> getEndActivitiesName() {
		return endActivities;// .toLowerCase();
	}

	/**
	 * This function create acyclic model for the give model by removing all the
	 * existed loops,i.e nested loops.
	 * 
	 * @param NetSystem
	 *            net : given cyclic model
	 * @return NetSystem : acyclic model
	 */
	private NetSystem constructAcyclicModel(NetSystem net) {
		NetSystem LoopFree = PNLoopFree(net);
		if (LoopFree == null)
			return null;
		while (true) {
			StronglyConnectedComponents<Flow, Node> sccs = new StronglyConnectedComponents<Flow, Node>();
			Set<Set<Node>> loops = sccs.compute(LoopFree);
			if (loops.size() == net.getVertices().size()) {
				break;
			}
			LoopFree = PNLoopFree(LoopFree);
		}
		return LoopFree;
	}

	/**
	 * This function determine the cyclics within the model using Tarjan
	 * Algorithm. For each loop detect, a new loop object created. Also remove
	 * the branch loop from the given model
	 * 
	 * @param NetSystem
	 *            net : given cyclic model
	 * @return NetSystem : acyclic model
	 */
	private NetSystem PNLoopFree(NetSystem net) {
		// use Tarjan's to get the graph component
		StronglyConnectedComponents<Flow, Node> sccs = new StronglyConnectedComponents<Flow, Node>();
		// return loops if exist
		Set<Set<Node>> loops = sccs.compute(net);
		// there is no loops , its acyclic model
		if (loops.size() == net.getVertices().size()) {
			return null;
		}
		int id = 1;
		// check each set if it's a loop
		// pnLoops = new HashSet<Loop>();
		// elementsInLoops = new HashSet<Node>();

		for (Set<Node> l : loops) {
			Set<Node> loopPlaces = new HashSet<Node>();
			if (l.size() == 1)
				continue;
			Loop loop = null;
			Collection<Node> elements = new HashSet<Node>();
			Collection<Node> ends = new HashSet<Node>();
			Collection<Node> starts = new HashSet<Node>();
			Collection<Node> loopBranchElements = new HashSet<Node>();
			for (Node node : l) {

				elements.add(node);

				Collection<Node> prenodes = net.getDirectPredecessors(node);
				Collection<Node> succnodes = net.getDirectSuccessors(node);
				if (prenodes.size() == 1 && succnodes.size() == 1)
					continue;
				if (prenodes.size() > 1) {
					// check if nodes has 1 external node
					prenodes.removeAll(l);
					if (prenodes.size() > 0) {
						loopPlaces.add(node);
						starts.add(node);
						continue;
					}
				}
				if (succnodes.size() > 1) {
					// check if nodes has 1 external node
					succnodes.removeAll(l);
					if (succnodes.size() > 0) {
						loopPlaces.add(node);
						ends.add(node);
						continue;
					}
				}
			}
			// to remove cyclic branch
			if (loopPlaces.size() > 0) {
				Collection<Node> nodesTobeRemoved = new HashSet<Node>();
				for (Node lp : loopPlaces) {
					Node inLoop = null;
					Collection<Node> succnodes = net.getDirectSuccessors(lp);
					Collection<Node> outOfLoopnodes = new HashSet<Node>(succnodes);
					outOfLoopnodes.removeAll(l);
					if (outOfLoopnodes.size() == 0)
						continue;
					succnodes.removeAll(outOfLoopnodes);
					inLoop = succnodes.iterator().next();
					Node node = inLoop;
					// remove callback branch from the end to the start of loop
					// assume loop call back just sequence of activities no
					// gates
					while (!loopPlaces.contains(node)) {
						nodesTobeRemoved.add(node);
						succnodes = net.getDirectSuccessors(node);
						inLoop = succnodes.iterator().next();
						node = inLoop;
					}
				}
				if (nodesTobeRemoved.size() > 0) {
					loopBranchElements.addAll(nodesTobeRemoved);
					net.removeNodes(nodesTobeRemoved);

				}

			}
			elementsInLoops.addAll(elements);
			loop = new Loop(id, elements, starts, ends, loopBranchElements, new HashSet<Node>());
			id++;
			pnLoops.add(loop);
		}
		return net;

	}

	/**
	 * This function modify the loop object to consider the right start and end
	 * activities of the loop. That by get the start\end activities instead of
	 * places. and also pass the silent activity, by considering its
	 * predecessors if its a part of loop end or its successors if its a part of
	 * loop start. Also handle the existence of business activity on the loop
	 * branch.
	 * 
	 * @param Collection<Loop>:
	 *            initial list of loop objects
	 * @param NetSystem
	 *            net : given model
	 * @return Collection<Loop> : list of modified loop objects
	 */
	private Collection<Loop> ModifyLoops(Collection<Loop> loops, NetSystem net) {
		if (loops.size() == 0)
			return null;

		for (Loop l : loops) {
			// get start transition
			Collection<Node> starts = new HashSet<Node>();
			Collection<Node> ends = new HashSet<Node>();
			Collection<Node> loopBranchElements = l.getLoopBranchElements();
			// default start and end based on both start and end set of loop
			for (Node s : l.getStarts()) {
				Collection<Node> succnodes = getModelSuccessors(s, new HashSet<>(), new HashSet<>(), new HashSet<>(),
						false);
				starts.addAll(succnodes);
			}
			for (Node e : l.getEnds()) {
				Collection<Node> prenodes = getModelPredecessors(e, new HashSet<>(), new HashSet<>(), new HashSet<>(),
						false);
				// net.getDirectPredecessors(e);
				ends.addAll(prenodes);
			}

			// determine end based on loop branch if it contains real activities
			// or just silent "tau" activities
			loopBranchElements.removeAll(net.getPlaces());
			l.setLoopBranchElements(loopBranchElements);
			if (l.isSilentBranch()) {
				l.setEnds(ends);
			} else {
				{
					// get end transition based on start nodes
					l.setInnerEnds(ends);
					ends = new HashSet<Node>();
					for (Node e : l.getStarts()) {
						Collection<Node> prednodes = net.getDirectPredecessors(e);
						Collection<Node> outOfLoopnodes = new HashSet<Node>(prednodes);
						outOfLoopnodes.removeAll(l.getElements());
						if (outOfLoopnodes.size() == 0)
							continue;
						prednodes.removeAll(outOfLoopnodes);
						ends.addAll(prednodes);

					}
					l.setEnds(ends);
				}
			}
			l.setStart(starts);
		}
		return loops;
	}

	/** Adapted BP part */

	/**
	 * This function get the constructed Relation matrix
	 * 
	 * @return Hashtable<String, Hashtable> : Relation Matrix
	 */
	public Hashtable<String, Hashtable> getMatrixRelations() {
		return matrix;
	}

	/**
	 * This function implements the first modification on Behavioral profile by
	 * enforcing the order in sequence relation
	 * 
	 * @param BehaviouralProfile
	 *            bpOrginial: Behavior profile for the given acyclic model
	 * @param Set<Transition>
	 *            transitions: represent the transitions\activities in the given
	 *            acyclic model
	 * @return Relation Matrix
	 */
	public Hashtable<String, Hashtable> getMatrixRelations(BehaviouralProfile<NetSystem, Node> bpOrginial,
			Set<Transition> transitions) {
		Hashtable<String, Hashtable> matrix = null;
		matrix = new Hashtable<String, Hashtable>();
		Dictionary<String, String> subDic = new Hashtable<String, String>();
		for (Node node : transitions) {
			if (node.getLabel().startsWith("tau"))
				continue;
			Hashtable<String, String> nodeRelationSubDic = new Hashtable<String, String>();
			for (Node node1 : transitions) {
				if (node1.getLabel().startsWith("tau"))
					continue;
				String relation = "ign";
				RelSetType rel = bpOrginial.getRelationForEntities(node, node1);
				if (rel == RelSetType.Exclusive) {
					relation = "xor";
				} else if (rel == RelSetType.Interleaving) {
					relation = "and";
				} else if (rel == RelSetType.ReverseOrder) {
					relation = "tra";
				} else if (rel == RelSetType.Order) {
					Collection<Node> node1Pred = getModelPredecessors(node1, new HashSet<>(), new HashSet<>(),
							new HashSet<>(), true);
					if (node1Pred.contains(node)) {
						relation = "seq";
					}
				}
				nodeRelationSubDic.put(node1.getLabel(), relation);
			}
			matrix.put(node.getLabel(), nodeRelationSubDic);
		}
		return matrix;
	}

	/**
	 * In this method the Relation Matrix is constructed based on behavioral
	 * profile of the given cyclic model and the behavioral profile of the
	 * created acyclic model.
	 * 
	 * @param BehaviouralProfile
	 *            bpOrginial: Behavior profile for the given cyclic model
	 * @param BehaviouralProfile
	 *            acyclicBP: Behavior profile for the given acyclic model
	 * @param Set<Transition>
	 *            transitions: represent the transitions\activities in the given
	 *            acyclic model
	 * @param Collection<Loop>
	 *            loops: represent the discovered loops based on tarjan
	 *            algorithm
	 * @return Relation Matrix that support the cyclic model
	 */
	public Hashtable<String, Hashtable> getMatrixRelations(BehaviouralProfile<NetSystem, Node> bpOrginial,
			BehaviouralProfile<NetSystem, Node> acyclicBP, Set<Transition> transitions, Collection<Loop> loops) {

		Hashtable<String, Hashtable> matrix = new Hashtable<String, Hashtable>();
		for (Node node : transitions) {
			if (node.getLabel().startsWith("tau"))
				continue;
			Hashtable<String, String> nodeRelationSubDic = new Hashtable<String, String>();// row
			for (Node node1 : transitions) {
				if (node1.getLabel().startsWith("tau"))
					continue;
				String relation = "ign";
				RelSetType rel = bpOrginial.getRelationForEntities(node, node1);
				if (rel == RelSetType.Exclusive) {
					relation = "xor";
				} else if (rel == RelSetType.ReverseOrder) {
					relation = "tra";
				} else if (rel == RelSetType.Interleaving) {
					relation = "and";
					// if the both nodes exist in a loop
					if (elementsInLoops.contains(node) && elementsInLoops.contains(node1)) {
						Loop loop = getIncludedLoop(node, node1, loops);
						if (loop != null) {
							// relation between the end and start activities of
							// the loop
							
							if (loop.getLoopBranchElements().contains(node)
									&& loop.getLoopBranchElements().contains(node1)) {
								// check if node 1 is a direct successor for
								// node or not
								// Collection<Node> nodeSucc =
								// bpOrginial.getModel().getDirectSuccessors(bpOrginial.getModel().getDirectSuccessors(node));
								Collection<Node> node1Pred = getModelPredecessors(node1, new HashSet<>(),
										new HashSet<>(), new HashSet<>(), true);
								// if (nodeSucc.contains(node1)) {
								if (node1Pred.contains(node)) {
									relation = "seq";
								} else if (node == node1) {
									relation = "xor";
								} else {
									relation = "tra";
								}
								if (node == node1) {
									relation = "xor";
								}
							} else if (loop.getLoopBranchElements().contains(node)
									|| loop.getLoopBranchElements().contains(node1)) {
								relation = "tra";
								if (loop.getEnds().contains(node) && loop.getStarts().contains(node1)) {
									relation = "seq";
								} else if (loop.getEnds().contains(node1) && loop.getInnerEnds().contains(node)) {
									relation = "seq";
								}
							} 
							else if (acyclicBP.getEntities().contains(node) && acyclicBP.getEntities().contains(node1) &&getRelationName(acyclicBP.getRelationForEntities(node, node1)) == "and") {
								relation = "and";
							} else if (loop.getEnds().contains(node) && loop.getStarts().contains(node1)) {
								relation = "seq";
							} else if (loop.getStarts().contains(node) && loop.getEnds().contains(node1)) {
								relation = getRelationName(acyclicBP.getRelationForEntities(node, node1));
								Collection<Node> node1Pred = getModelPredecessors(node1, new HashSet<>(),
										new HashSet<>(), new HashSet<>(), true);
								if (node1Pred.contains(node)) {
									relation = "seq";
								}
							} else if (node != node1 && loop.getInnerEnds().contains(node)
									&& loop.getEnds().contains(node1)) {
								relation = "seq";
							}
							// both nodes are part of the loop branch
							else { // for inner activities relations
								relation = getRelationName(acyclicBP.getRelationForEntities(node, node1));
							if(relation=="seq"){
								Collection<Node> node1Pred = getModelPredecessors(node1, new HashSet<>(), new HashSet<>(),
										new HashSet<>(), true);
								if (node1Pred.contains(node)) {
									relation = "seq";
								}
								else
									relation= "ign";
							}
							}
						}
					}
				} else if (rel == RelSetType.Order) {
					Collection<Node> node1Pred = getModelPredecessors(node1, new HashSet<>(), new HashSet<>(),
							new HashSet<>(), true);
					if (node1Pred.contains(node)) {
						relation = "seq";
					}
				}
				nodeRelationSubDic.put(node1.getLabel(), relation);
			}
			matrix.put(node.getLabel(), nodeRelationSubDic);
		}
		return matrix;
	}

	private String getRelationName(RelSetType rel) {
		if (rel == RelSetType.Exclusive)
			return "xor";
		else if (rel == RelSetType.ReverseOrder)
			return "tra";

		else if (rel == RelSetType.Interleaving)
			return "and";
		else if (rel == RelSetType.Order)
			return "seq";
		return "ign";
	}

	private Loop getIncludedLoop(Node node, Node node1, Collection<Loop> loops) {
		Collection<Loop> selectedLoops = new HashSet<Loop>();
		for (Loop l : loops) {
			if (l.getElements().contains(node) && l.getElements().contains(node1))
				selectedLoops.add(l);
		}
		Loop selectedLoop = null;
		if (selectedLoops.size() > 0) {
			selectedLoop = (Loop) selectedLoops.toArray()[0];
			for (Loop l : selectedLoops) {
				if (selectedLoop.getElements().contains(l.getElements()))
					selectedLoop = l;
			}
		}
		return selectedLoop;
	}

	public ArrayList<Loop> getLoops() {
		ArrayList<Loop> elements = new ArrayList<Loop>();
		if (pnLoops.size() > 0) {
			for (Loop l : pnLoops) {
				elements.add(l);
			}
		}
		return elements;
	}

	/** Predecessor part TO be removed */
	/**
	 * This function gets the predecessors of the model with ignoring all the
	 * silent activities. Its a recursion method that will be targeted when the
	 * predecessor node is a silent activity
	 * 
	 * @param Node
	 *            node: the node that request the predecessors
	 * @param List<Node>
	 *            predecessors: this the composite list with all the
	 *            predecessors of the give node
	 * @return List<Node> predecessors:
	 */
	public Collection<Node> getModelPredecessors(Node node, Collection<Node> PredTransNodes,
			Collection<Node> RemoveNodes, Collection<Node> AlreadyChecked, boolean isTransition) {
		Collection<Node> preNodes = new HashSet<>();
		if (isTransition)
			preNodes = orginialModel.getDirectPredecessors(orginialModel.getDirectPredecessors(node));
		else
			preNodes = orginialModel.getDirectPredecessors(node);

		PredTransNodes.addAll(preNodes);
		if (AlreadyChecked.contains(node))
			return PredTransNodes;

		for (Node tran : preNodes) {
			if (tran.getLabel().toLowerCase().contains("tau")) {
				RemoveNodes.add(tran);
				AlreadyChecked.add(node);
				PredTransNodes.addAll(getModelPredecessors(tran, PredTransNodes, RemoveNodes, AlreadyChecked, true));
				AlreadyChecked.add(node);
			}
		}
		PredTransNodes.removeAll(RemoveNodes);
		return PredTransNodes;
	}

	public Collection<Node> getModelSuccessors(Node node, Collection<Node> SuccTransNodes, Collection<Node> RemoveNodes,
			Collection<Node> AlreadyChecked, boolean isTransition) {
		Collection<Node> sucNodes = new HashSet<>();
		if (isTransition)
			sucNodes = orginialModel.getDirectSuccessors(orginialModel.getDirectSuccessors(node));
		else
			sucNodes = orginialModel.getDirectSuccessors(node);

		SuccTransNodes.addAll(sucNodes);
		if (AlreadyChecked.contains(node))
			return SuccTransNodes;

		for (Node tran : sucNodes) {
			if (tran.getLabel().toLowerCase().contains("tau")) {
				RemoveNodes.add(tran);
				AlreadyChecked.add(node);
				SuccTransNodes.addAll(getModelSuccessors(tran, SuccTransNodes, RemoveNodes, AlreadyChecked, true));
				AlreadyChecked.add(node);
			}
		}
		SuccTransNodes.removeAll(RemoveNodes);
		return SuccTransNodes;
	}

	public Hashtable<String, HashSet<HashSet<String>>> predcessors() {
		Hashtable<String, HashSet<HashSet<String>>> parentsMatrix = new Hashtable<String, HashSet<HashSet<String>>>();
		for (Transition t : transitions) {
			if (t.getLabel().startsWith("tau"))
				continue;
			parentsMatrix.put(t.getLabel(), predcessors(t));
		}
		return parentsMatrix;
	}

	public Dictionary<String, ArrayList<ArrayList<String>>> getAllPredecessors() {
		return predecessors;
	}

	public Dictionary<String, ArrayList<ArrayList<String>>> getPredecessors() {
		Hashtable<String, HashSet<HashSet<String>>> predecessors = predcessors();
		Dictionary<String, ArrayList<ArrayList<String>>> parentsMatrix = new Hashtable<String, ArrayList<ArrayList<String>>>();
		for (String key : predecessors.keySet()) {
			ArrayList<ArrayList<String>> arrFromSet = new ArrayList<ArrayList<String>>();
			for (HashSet<String> hset : predecessors.get(key)) {
				arrFromSet.add(new ArrayList<String>(hset));
			}
			parentsMatrix.put(key, arrFromSet);
		}
		return parentsMatrix;
	}

	public HashSet<HashSet<String>> predcessors(Node node) {
		HashSet<HashSet<String>> pred = new HashSet<HashSet<String>>();
		Collection<Node> predNodes = getModelPredecessors(node, new HashSet<>(), new HashSet<>(), new HashSet<>(),
				true);
		ArrayList<String> activities = getLabelOfActivities(predNodes);

		for (String act : activities) {
			int index = activities.indexOf(act);
			if (index == 0) {
				HashSet<String> temp = new HashSet<>();
				temp.add(act);
				pred.add(temp);
				continue;
			}
			List<String> subActivities = activities.subList(0, index);
			for (String act1 : subActivities) {
				String relation = getRelationForEntities(act, act1);
				String relation1= getRelationForEntities(act1, act);
				if (relation == "and" || relation1=="and") {
					HashSet<String> temp = new HashSet<>();
					temp.add(act);
					temp.add(act1);
					if (!pred.contains(temp))
						pred.add(temp);
				} else { // to contain all type of relation + --> <-- ignore
					HashSet<String> temp = new HashSet<>();
					temp.add(act);
					HashSet<String> temp1 = new HashSet<>();
					temp1.add(act1);
					if (!pred.contains(temp))
						pred.add(temp);
					if (!pred.contains(temp1))
						pred.add(temp1);
				}
			}
		}
		HashSet<HashSet<String>> processedPredecessors = _predecessorsRecursion(pred);
		return _cleanPredecessors(processedPredecessors);
	}

	private HashSet<HashSet<String>> _predecessorsRecursion(HashSet<HashSet<String>> initialPred) {
		System.out.println(initialPred);
		HashSet<HashSet<String>> processedPredecessors = new HashSet<HashSet<String>>();
		processedPredecessors.addAll(initialPred);
		HashSet<HashSet<String>> toBeRemoved = new HashSet<HashSet<String>>();

		for (HashSet<String> xorPred : initialPred) {
			for (HashSet<String> xorPred1 : initialPred) {
				String Relation = getRelationForEntitiesSet(xorPred, xorPred1);
				String Relation1 = getRelationForEntitiesSet(xorPred1, xorPred);
				if (Relation == "and" || Relation1 == "and") {
					HashSet<String> temp = new HashSet<>();
					temp.addAll(xorPred);
					temp.addAll(xorPred1);
					processedPredecessors.add(temp);
					toBeRemoved.add(xorPred1);
					toBeRemoved.add(xorPred);
				}
			}
		}
		System.out.println(processedPredecessors);
		processedPredecessors.removeAll(toBeRemoved);
		if (processedPredecessors.equals(initialPred))
			return processedPredecessors;
		else
			return _predecessorsRecursion(processedPredecessors);
		// return processedPredecessors;
	}

	private HashSet<HashSet<String>> _cleanPredecessors(HashSet<HashSet<String>> processedPred) {
		// HashSet<HashSet<String>> cleanedPredecessors = new
		// HashSet<HashSet<String>>();
		HashSet<HashSet<String>> toBeRemoved = new HashSet<HashSet<String>>();
		for (HashSet<String> xorPred : processedPred) {
			int x = 1;
			for (HashSet<String> xorPred1 : processedPred) {
				if ((!xorPred.equals(xorPred1)) && xorPred.containsAll(xorPred1))
					toBeRemoved.add(xorPred1);
			}
		}
		processedPred.removeAll(toBeRemoved);
		return processedPred;
	}

	private ArrayList<String> getLabelOfActivities(Collection<Node> nodes) {
		ArrayList<String> names = new ArrayList<>();
		for (Node n : nodes)
			names.add(n.getLabel());
		return names;
	}

	private String getRelationForEntitiesSet(HashSet<String> node, HashSet<String> node1) {
		String relation = "none";
		for (String sNode : node) {
			for (String sNode1 : node1) {
				relation = getRelationForEntities(sNode, sNode1);
				if (relation == "xor" || relation == "none")
					return relation;
			}
		}
		return relation;
	}

	private String getRelationForEntities(String node, String node1) {
		String relation = "none";
		try {
			relation = matrix.get(node).get(node1).toString();
		} catch (Exception ex) {
			relation = "none";
		}
		return relation;
	}

	public void printRM() {
		System.out.print("	");
		for (String e : matrix.keySet()) {
			if (e.length() > 6) {
				System.out.print(e.substring(0, 6) + "	");
			} else {
				System.out.print(e + "	");
			}
		}
		for (String e : matrix.keySet()) {
			System.out.println();
			if (e.length() > 6) {
				System.out.print(e.substring(0, 6) + "	");
			} else {
				System.out.print(e + "	");
			}
			Hashtable<String, String> rel = matrix.get(e);
			for (String km : rel.keySet()) {
				System.out.print(rel.get(km) + "	");
			}
		}
	}

	public void printBP(BehaviouralProfile<NetSystem, Node> bp) {
		System.out.print("	");
		Set<Transition> transitions = bp.getModel().getTransitions();
		for (Transition transition : transitions) {
			if (transition.getLabel().contains("tau"))
				continue;
			if (transition.getLabel().length() > 4) {
				System.out.print(transition.getLabel().substring(0, 4) + "	");
			} else {
				System.out.print(transition.getLabel() + "	");
			}
		}
		for (Transition transition : transitions) {
			if (transition.getLabel().contains("tau"))
				continue;
			System.out.println();
			if (transition.getLabel().length() > 4) {
				System.out.print(transition.getLabel().substring(0, 4) + "	");
			} else {
				System.out.print(transition.getLabel() + "	");
			}
			for (Transition transition1 : transitions) {
				if (transition1.getLabel().contains("tau"))
					continue;
				System.out.print(bp.getRelationForEntities(transition, transition1) + "	 ");
			}
		}
	}

	public BehaviouralProfile<NetSystem, Node> getBpOrginial() {
		return bpOrginial;
	}

	public void setBpOrginial(BehaviouralProfile<NetSystem, Node> bpOrginial) {
		this.bpOrginial = bpOrginial;
	}

	public BehaviouralProfile<NetSystem, Node> getBpLoopFree() {
		return bpLoopFree;
	}

	public void setBpLoopFree(BehaviouralProfile<NetSystem, Node> bpLoopFree) {
		this.bpLoopFree = bpLoopFree;
	}
}
