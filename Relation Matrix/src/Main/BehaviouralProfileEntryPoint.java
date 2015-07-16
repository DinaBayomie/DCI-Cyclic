/***
 * This code is copyrighted to Dina Bayomie @2015 Research work
 * Information System Department, Faculty of computers and Information System
 * Cairo University, Egypt
	@author:  Dina Bayomie
*/

package Main;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
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

import py4j.GatewayServer;

public class BehaviouralProfileEntryPoint {

	private BehaviouralProfile<NetSystem, Node> bp1;
	private BehaviouralProfile<NetSystem, Node> bpLoopFree;
	private CausalBehaviouralProfile<NetSystem, Node> cbp2;
	// private java.util.List<Node> entities;
	private PetriNet pn;
	public BehaviouralProfileEntryPoint bpep = null;
	public ArrayList<String> startActivity = null;
	private NetSystem n = null;
	private NetSystem nloopFree = null;
	private Set<Transition> transitions = null;
	private Collection<Loop> pnLoops = null;
	private Hashtable<String, Hashtable> matrix = null;

	public BehaviouralProfileEntryPoint() {

		PetriNet pn = new PetriNet();

		// // BPI challenges 2012
		
		 Place p1 = new Place("1");
		 Place p2 = new Place("2");
		 Place p3 = new Place("3");
		 Place p4 = new Place("4");
		 Place p5 = new Place("5");
		 Place p6 = new Place("6");
		 Place p7 = new Place("7");
		 Place p8 = new Place("8");
		 Place p9 = new Place("9");
		 Place p10 = new Place("10");
		 Place p11 = new Place("11");
		 Place p12 = new Place("12");
		 Place p13 = new Place("13");
		 Place p14 = new Place("14");
		 Place p15 = new Place("15");
		 Place p16 = new Place("16");
		 Place p17 = new Place("17");
		 Place p18 = new Place("18");
		 Place p19 = new Place("19");
		 Place p20 = new Place("20");
		 Place p21 = new Place("21");
		 Place p22 = new Place("22");
		 Place p23 = new Place("23");
		 Place p24 = new Place("24");
		 Place p25 = new Place("25");
		 Place p26 = new Place("26");
		 Place p27 = new Place("27");
		 Place p28 = new Place("28");
		 Place p29 = new Place("29");
		 Place p30 = new Place("30");
		 Place p31 = new Place("31");
		 Place p32 = new Place("32");
		 Place p33 = new Place("33");
		 Place p34 = new Place("34");
		 Place p35 = new Place("35");
		 Place p36 = new Place("36");
		 Place p37 = new Place("37");
		 Place p38 = new Place("38");
		 Place p39 = new Place("39");
		 Place p40 = new Place("40");
		 Place p43 = new Place("43");
		 Place p41 = new Place("41");
		 Place p44 = new Place("44");
		 Place p42 = new Place("42");
		
		 pn.addNode(p1);
		 pn.addNode(p2);
		 pn.addNode(p3);
		 pn.addNode(p4);
		 pn.addNode(p5);
		 pn.addNode(p6);
		 pn.addNode(p7);
		 pn.addNode(p8);
		 pn.addNode(p9);
		 pn.addNode(p10);
		 pn.addNode(p11);
		 pn.addNode(p12);
		 pn.addNode(p13);
		 pn.addNode(p14);
		 pn.addNode(p15);
		 pn.addNode(p16);
		 pn.addNode(p17);
		 pn.addNode(p18);
		 pn.addNode(p19);
		 pn.addNode(p20);
		 pn.addNode(p21);
		 pn.addNode(p22);
		 pn.addNode(p23);
		 pn.addNode(p24);
		 pn.addNode(p25);
		 pn.addNode(p26);
		 pn.addNode(p27);
		 pn.addNode(p28);
		 pn.addNode(p29);
		 pn.addNode(p30);
		 pn.addNode(p31);
		 pn.addNode(p32);
		 pn.addNode(p33);
		 pn.addNode(p34);
		 pn.addNode(p35);
		 pn.addNode(p36);
		 pn.addNode(p37);
		 pn.addNode(p38);
		 pn.addNode(p39);
		 pn.addNode(p40);
		 pn.addNode(p41);
		 pn.addNode(p42);
		 pn.addNode(p43);
		 pn.addNode(p44);
		
		 Transition t1 = new Transition("A_SUBMITTED");
		 Transition t2 = new Transition("A_PARTLYSUBMITTED");
		 Transition t3 = new Transition("W_Nabellen incomplete dossiers");
		 Transition t4 = new Transition("A_PREACCEPTED");// W_Afhandelen		 leads");
		 Transition t5 = new Transition("W_Completeren aanvraag");
		 Transition t6 = new Transition("A_ACCEPTED");
		 Transition t7 = new Transition("O_SELECTED");
		 Transition t8 = new Transition("A_FINALIZED");
		 Transition t9 = new Transition("O_DECLINED");
		 Transition t10 = new Transition("A_CANCELLED");// W_Wijzigen
		 // contractgegevens
		 Transition t11 = new Transition("O_CREATED");
		 Transition t12 = new Transition("O_SENT");
		 Transition t13 = new Transition("O_SENT_BACKs");
		 Transition t14 = new Transition("A_CANCELLED");
		 Transition t15 = new Transition("W_Nabellen offertes");// W_Nabellen
		 // incomplete
		 // dossiers
		 Transition t16 = new Transition("W_Beoordelen fraude");
		 Transition t17 = new Transition("A_DECLINED");
		 Transition t19 = new Transition("W_Afhandelen leads");
		 Transition t20 = new Transition("W_Valideren aanvraag");
		 Transition t21 = new Transition("W_Wijzigen contractgegevens");
		
		 Transition tauD3 = new Transition("tauD3");
		 Transition taul3 = new Transition("taul3");
		 Transition tauD1 = new Transition("tauD1");
		 Transition taul1 = new Transition("taul1");
		
		 Transition tauD6 = new Transition("tauD6");
		 Transition taul6 = new Transition("taul6");
		 Transition tauD5 = new Transition("tauD5");
		 Transition taul5 = new Transition("taul5");
		 Transition tauD4 = new Transition("tauD4");
		 Transition taul4 = new Transition("taul4");
		 Transition tauD2 = new Transition("tauD2");
		 Transition taul2 = new Transition("taul2");
		
		 Transition tau1 = new Transition("tau1");
		 Transition tau2 = new Transition("tau2");
		 Transition tau3 = new Transition("tau3");
		 Transition tau4 = new Transition("tau4");
		 Transition tau5 = new Transition("tau5");
		 Transition tau6 = new Transition("tau6");
		 Transition tau7 = new Transition("tau7");
		 Transition tau8 = new Transition("tau8");
		 Transition tau9 = new Transition("tau9");
		 Transition tau10 = new Transition("tau10");
		 Transition tau11 = new Transition("tau11");
		 Transition tau12 = new Transition("tau12");
		 Transition tau13 = new Transition("tau13");
		 Transition tau14 = new Transition("tau14");
		 Transition tau15 = new Transition("tau15");
		 Transition tau16 = new Transition("tau16");
		 Transition tau17 = new Transition("tau17");
		 Transition tau18 = new Transition("tau18");
		 Transition tau19 = new Transition("tau19");
		 Transition tau20 = new Transition("tau20");
		 Transition tau21 = new Transition("tau21");
		 Transition tau22 = new Transition("tau22");
		 Transition tau23 = new Transition("tau23");
		 Transition tau24 = new Transition("tau24");
		 Transition tau25 = new Transition("tau25");
		 Transition tau26 = new Transition("tau26");
		 Transition tau27 = new Transition("tau27");
		 Transition tau28 = new Transition("tau28");
		
		 pn.addNode(t1);
		 pn.addNode(t2);
		 pn.addNode(t3);
		 pn.addNode(t4);
		 pn.addNode(t5);
		 pn.addNode(t6);
		 pn.addNode(t7);
		 pn.addNode(t8);
		 pn.addNode(t9);
		 pn.addNode(t10);
		 pn.addNode(t11);
		 pn.addNode(t12);
		 pn.addNode(t13);
		 pn.addNode(t14);
		 pn.addNode(t15);
		 pn.addNode(t16);
		 pn.addNode(t17);
		 pn.addNode(t19);
		 pn.addNode(t20);
		 pn.addNode(t21);
		
		 pn.addNode(tau1);
		 pn.addNode(tau2);
		 pn.addNode(tau3);
		 pn.addNode(tau4);
		 pn.addNode(tau5);
		 pn.addNode(tau6);
		 pn.addNode(tau7);
		 pn.addNode(tau8);
		 pn.addNode(tau9);
		 pn.addNode(tau10);
		 pn.addNode(tau11);
		 pn.addNode(tau12);
		 pn.addNode(tau13);
		 pn.addNode(tau14);
		 pn.addNode(tau15);
		 pn.addNode(tau16);
		 pn.addNode(tau17);
		 pn.addNode(tau18);
		 pn.addNode(tau19);
		 pn.addNode(tau20);
		 pn.addNode(tau21);
		 pn.addNode(tau22);
		 pn.addNode(tau23);
		 pn.addNode(tau24);
		 pn.addNode(tau25);
		 pn.addNode(tau26);
		 pn.addNode(tau27);
		 pn.addNode(tau28);
		
		 pn.addNode(taul1);
		 pn.addNode(taul2);
		 pn.addNode(taul3);
		 pn.addNode(taul4);
		 pn.addNode(taul5);
		 pn.addNode(taul6);
		 pn.addNode(tauD6);
		 pn.addNode(tauD5);
		 pn.addNode(tauD4);
		 pn.addNode(tauD3);
		 pn.addNode(tauD2);
		 pn.addNode(tauD1);
		
		 pn.addFlow(p1, t1);
		 pn.addFlow(t1, p2);
		 pn.addFlow(p2, t2);
		 pn.addFlow(t2, p3);
		 pn.addFlow(p3, tau1);
		 pn.addFlow(tau1, p4);
		
		 pn.addFlow(tau1, p40);
		
		 // 1st level
		 pn.addFlow(p4, tau2);
		 pn.addFlow(tau2, p5);
		 // loop
		 pn.addFlow(p5, tauD3);
		 pn.addFlow(tauD3, p6);
		 pn.addFlow(p5, t3);
		 pn.addFlow(t3, p6);
		 pn.addFlow(p6, taul3);
		 pn.addFlow(taul3, p5);
		 // end loop
		 pn.addFlow(p6, tau6);
		 pn.addFlow(tau6, p39);
		 pn.addFlow(p39, tau25);
		
		 pn.addFlow(tau25, p43);
		 pn.addFlow(p43, tau26);
		
		 pn.addFlow(p43, t21);
		 pn.addFlow(t21, p44);
		 pn.addFlow(tau26, p44);
		
		 pn.addFlow(p4, tau3);
		 pn.addFlow(tau3, p7);
		 pn.addFlow(p7, tau4);
		 pn.addFlow(p7, t4);
		 pn.addFlow(tau4, p8);
		 pn.addFlow(t4, p8);
		 pn.addFlow(p8, tau5);
		
		 pn.addFlow(tau3, p12);
		 // loop
		 pn.addFlow(p12, t19);
		 pn.addFlow(p12, tauD2);
		 pn.addFlow(t19, p13);
		 pn.addFlow(tauD2, p13);
		 pn.addFlow(p13, taul2);
		 pn.addFlow(taul2, p12);
		 pn.addFlow(p13, tau27);
		 pn.addFlow(tau27, p14);
		 pn.addFlow(p14, tau24);
		
		 // end loop
		 // loop
		 pn.addFlow(p40, tauD1);
		 pn.addFlow(tauD1, p41);
		 pn.addFlow(p40, t20);
		 pn.addFlow(t20, p41);
		 pn.addFlow(p41, taul1);
		 pn.addFlow(taul1, p40);
		 // end loop
		 pn.addFlow(p41, tau28);
		 pn.addFlow(tau28, p42);
		 pn.addFlow(p42, tau25);
		
		 pn.addFlow(tau5, p9);
		 // loop
		 pn.addFlow(p9, t5);
		 pn.addFlow(p9, tauD4);
		 pn.addFlow(tauD4, p10);
		 pn.addFlow(t5, p10);
		 pn.addFlow(p10, taul4);
		 pn.addFlow(taul4, p9);
		 // end of loop
		 pn.addFlow(p10, tau8);
		 pn.addFlow(tau8, p11);
		 pn.addFlow(p11, tau23);
		 pn.addFlow(tau23, p38);
		 pn.addFlow(p38, tau24);
		 pn.addFlow(tau24, p39);
		
		 pn.addFlow(tau5, p15);
		 pn.addFlow(p15, tau7);
		 pn.addFlow(p15, t6);
		 pn.addFlow(tau7, p16);
		 pn.addFlow(t6, p16);
		 pn.addFlow(p16, tau9);
		
		 pn.addFlow(tau9, p17);
		 pn.addFlow(p17, tau10);
		 pn.addFlow(p17, t7);
		 pn.addFlow(tau10, p18);
		 pn.addFlow(t7, p18);
		 pn.addFlow(p18, tau22);
		 pn.addFlow(tau22, p37);
		 pn.addFlow(p37, tau23);
		
		 pn.addFlow(tau9, p19);
		 pn.addFlow(p19, tau11);
		 pn.addFlow(p19, t8);
		 pn.addFlow(tau11, p20);
		 pn.addFlow(t8, p20);
		 pn.addFlow(p20, tau12);
		
		 pn.addFlow(tau12, p21);
		 pn.addFlow(p21, tau13);
		 pn.addFlow(p21, t9);
		 pn.addFlow(tau13, p22);
		 pn.addFlow(t9, p22);
		 pn.addFlow(p22, tau21);
		 pn.addFlow(tau21, p36);
		 pn.addFlow(p36, tau22);
		
		 pn.addFlow(tau12, p23);
		 pn.addFlow(p23, t10);
		 // loop
		 pn.addFlow(t10, p28);
		 pn.addFlow(p28, tauD6);
		 pn.addFlow(tauD6, p34);
		 pn.addFlow(p28, t15);
		 pn.addFlow(t15, p34);
		 pn.addFlow(p34, taul6);
		 pn.addFlow(taul6, p28);
		 // end loop
		 pn.addFlow(p34, tau20);
		 pn.addFlow(tau20, p35);
		 pn.addFlow(p35, tau21);
		
		 pn.addFlow(p23, tau14);
		 pn.addFlow(tau14, p24);
		 // loop
		 pn.addFlow(p24, t11);
		 pn.addFlow(t11, p25);
		 pn.addFlow(p25, t12);
		 pn.addFlow(t12, p26);
		 pn.addFlow(p26, tau18);
		 pn.addFlow(p26, t13);
		 pn.addFlow(t13, p27);
		 pn.addFlow(tau18, p27);
		 pn.addFlow(p27, t14);
		 pn.addFlow(t14, p24);
		 // end loop
		 pn.addFlow(p27, tau19);
		 pn.addFlow(tau19, p28);
		
		 pn.addFlow(p23, tau15);
		 pn.addFlow(tau15, p29);
		 // loop
		 pn.addFlow(p29, t16);
		 pn.addFlow(t16, p30);
		 pn.addFlow(p29, tauD5);
		 pn.addFlow(tauD5, p30);
		 pn.addFlow(p30, taul5);
		 pn.addFlow(taul5, p29);
		 // end loop
		 pn.addFlow(p30, tau16);
		 pn.addFlow(tau16, p31);
		 pn.addFlow(p31, tau17);
		 pn.addFlow(tau17, p35);
		
		 pn.addFlow(tau15, p32);
		 pn.addFlow(p32, t17);
		 pn.addFlow(t17, p33);
		 pn.addFlow(p33, tau17);

		// ////// Loop example [loop contains XOR gate ]
		// Transition a = new Transition("A");
		// Transition b = new Transition("B");
		// Transition c = new Transition("C");
		// Transition d = new Transition("D");
		// // Transition e = new Transition("E");
		// // Transition f = new Transition("F");
		// // Transition g = new Transition("G");
		// Transition tau1 = new Transition("tau");

		// pn.addNode(a);
		// pn.addNode(b);
		// pn.addNode(c);
		// pn.addNode(d);
		// // pn.addNode(e);
		// // pn.addNode(f);
		// // pn.addNode(g);
		// pn.addNode(tau1);
		//
		// Place p1 = new Place("1");
		// Place p2 = new Place("2");
		// Place p3 = new Place("3");
		// Place p4 = new Place("4");
		// // Place p5 = new Place("5");
		// // Place p6 = new Place("6");
		// // Place p7 = new Place("7");
		// // Place p8 = new Place("8");
		// pn.addNode(p1);
		// pn.addNode(p2);
		// pn.addNode(p3);
		// pn.addNode(p4);
		// pn.addNode(p5);
		// pn.addNode(p6);
		// pn.addNode(p7);
		// pn.addNode(p8);

		// pn.addFlow(p1, a);
		// pn.addFlow(a, p2);
		// // in loop
		// pn.addFlow(p2, b);
		// pn.addFlow(p2, c);
		// // //// b->e
		// // pn.addFlow(b, p5);
		// // pn.addFlow(p5, e);
		// pn.addFlow(b, p3);
		// // pn.addFlow(e, p3);
		//
		// pn.addFlow(c, p3);
		// // for xor join
		// pn.addFlow(p3, tau1);
		// pn.addFlow(tau1, p2);
		// // //
		// pn.addFlow(p3, d);
		// pn.addFlow(d, p4);
		// // and
		// // pn.addFlow(p4, e);
		// // pn.addFlow(e, p5);
		// // pn.addFlow(d, p6);
		// // pn.addFlow(p6, f);
		// // pn.addFlow(f, p7);
		// // pn.addFlow(p5, g);
		// // pn.addFlow(p7, g);
		// // pn.addFlow(g, p8);

//		// BPI 2013 not working as it has start and complete
//		Transition a = new Transition("Unmatched");
//		Transition b = new Transition("Accepted");
//		Transition c = new Transition("Queued");
//		Transition d = new Transition("Completed");
//		Transition tau1 = new Transition("tau1");
//		pn.addNode(a);
//		pn.addNode(b);
//		pn.addNode(c);
//		pn.addNode(d);
//		pn.addNode(tau1);
//
//		Place p1 = new Place("1");
//		Place p2 = new Place("2");
//		Place p3 = new Place("3");
//		Place p4 = new Place("4");
//		
//		pn.addNode(p1);
//		pn.addNode(p2);
//		pn.addNode(p3);
//		pn.addNode(p4);
//		
//
//		pn.addFlow(p1, a);
//		pn.addFlow(p1,tau1);
//		pn.addFlow(tau1,p2);
//		pn.addFlow(a,p2);
//		pn.addFlow(p2,b);
//		pn.addFlow(b,p3);
//		pn.addFlow(p3,c);
//		pn.addFlow(c,p2);
//		pn.addFlow(p3,d);
//		pn.addFlow(d,p4);
		
//		Transition a = new Transition("A");
//		Transition b = new Transition("B");
//		Transition c = new Transition("C");
//		Transition d = new Transition("D");
//		 Transition e = new Transition("E");
//		// Transition f = new Transition("F");
//		// Transition g = new Transition("G");
//		Transition tau1 = new Transition("tau7");
//		pn.addNode(a);
//		pn.addNode(b);
//		pn.addNode(c);
//		pn.addNode(d);
//		 pn.addNode(e);
//		// pn.addNode(f);
//		// pn.addNode(g);
//		pn.addNode(tau1);
//
//		Place p1 = new Place("1");
//		Place p2 = new Place("2");
//		Place p3 = new Place("3");
//		Place p4 = new Place("4");
//		 Place p5 = new Place("5");
//		// Place p6 = new Place("6");
//		// Place p7 = new Place("7");
//		// Place p8 = new Place("8");
//		pn.addNode(p1);
//		pn.addNode(p2);
//		pn.addNode(p3);
//		pn.addNode(p4);
//		 pn.addNode(p5);
//		// pn.addNode(p6);
//		// pn.addNode(p7);
//		// pn.addNode(p8);
//
//		pn.addFlow(p1, a);
//		pn.addFlow(a, p2);
//		// in loop
//		pn.addFlow(p2, b);
//		pn.addFlow(p2, c);
////		 /////c->e
//		 pn.addFlow(c, p5);
//		 pn.addFlow(p5, e);
//		 pn.addFlow(e, p3);
//		 
//		 pn.addFlow(b, p3);
//		 
//
////		pn.addFlow(c, p3);
//		// for xor join
//		pn.addFlow(p3, tau1);
//		pn.addFlow(tau1, p2);
//		// //
//		pn.addFlow(p3, d);
//		pn.addFlow(d, p4);
//
		
		n = new NetSystem(pn);
		n.loadNaturalMarking();
//		Node Start_Activity = n.getEnabledTransitionsAtMarking(n.getMarking())
//				.iterator().next();
		startActivity=new ArrayList<String>();
		Set<Transition> starts= n.getEnabledTransitionsAtMarking(n.getMarking());
		for(Transition t : starts){
			if(t.getLabel().contains("tau")){
				Collection<Node> succnodes = pn.getDirectSuccessors(pn.getDirectSuccessors(t));	
				for(Node n : succnodes){
					startActivity.add(n.getLabel());
				}
			}
			else{
				startActivity.add(t.getLabel());
			}
		}
		// entities = bp1.getEntities();
		// transitions =bp1.getModel().getTransitions();

		transitions = n.getTransitions();
//		startActivity = Start_Activity.getLabel();

		bp1 = BPCreatorNet.getInstance().deriveRelationSet(n);
		nloopFree = PNLoopFree(pn);// petri net loop free
		if (nloopFree != null) {
			nloopFree.loadNaturalMarking();
			bpLoopFree = BPCreatorNet.getInstance()
					.deriveRelationSet(nloopFree);
			pnLoops = ModifyLoops(pnLoops, n);
			matrix = getMatrixRelations(bp1, bpLoopFree, transitions, pnLoops);
		} else {
			matrix = getMatrixRelations(bp1, transitions);
		}
	}

	public ArrayList<String> getStartActivityName() {
		return startActivity;// .toLowerCase();
	}

	public NetSystem PNLoopFree(PetriNet pn) {
		NetSystem net = new NetSystem(pn);
		StronglyConnectedComponents<Flow, Node> sccs = new StronglyConnectedComponents<Flow, Node>();
		// return loops if exist
		Set<Set<Node>> loops = sccs.compute(net);
		// there is no loops , its acyclic model
		if (loops.size() == n.getVertices().size()) {
			return null;
		}
		int id = 1;

		// check each set if it's a loop
		pnLoops = new HashSet<Loop>();
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
					// check if nodes has 1 extrenal node
					prenodes.removeAll(l);
					if (prenodes.size() > 0) {
						loopPlaces.add(node);
						starts.add(node);
						continue;
					}
				}
				if (succnodes.size() > 1) {
					// check if nodes has 1 extrenal node
					succnodes.removeAll(l);
					if (succnodes.size() > 0) {
						loopPlaces.add(node);
						ends.add(node);
						continue;
					}

				}
			}

			if (loopPlaces.size() > 0) {
				// Collection<Flow> flowsTobeRemoved=new HashSet<Flow>();
				Collection<Node> nodesTobeRemoved = new HashSet<Node>();
				for (Node lp : loopPlaces) {
					Node inLoop = null;

					Collection<Node> succnodes = net.getDirectSuccessors(lp);
					Collection<Node> outOfLoopnodes = new HashSet<Node>(
							succnodes);
					outOfLoopnodes.removeAll(l);
					if (outOfLoopnodes.size() == 0)
						continue;
					succnodes.removeAll(outOfLoopnodes);
					inLoop = succnodes.iterator().next();
					Node node = inLoop;
					// flowsTobeRemoved.add(net.getEdge(lp, node));
					while (!loopPlaces.contains(node)) {
						succnodes = net.getDirectSuccessors(node);
						inLoop = succnodes.iterator().next(); // assume loop
																// call back
																// just sequence
																// of activities
																// no gates
						// flowsTobeRemoved.add(net.getEdge(node, inLoop));
						nodesTobeRemoved.add(node);
						node = inLoop;
					}
				}
				if (nodesTobeRemoved.size() > 0) {
					// net.removeEdges(flowsTobeRemoved);
					loopBranchElements.addAll(nodesTobeRemoved);
					net.removeNodes(nodesTobeRemoved);

				}

			}
			loop = new Loop(id, elements, starts, ends, loopBranchElements,
					new HashSet<Node>());
			id++;
			pnLoops.add(loop);
		}
		return net;

	}

	// MODIFY START & END OF LOOP BY TRANSITIONS INSTEAD OF PLACES
	public Collection<Loop> ModifyLoops(Collection<Loop> loops, NetSystem net) {
		if (loops.size() == 0)
			return null;

		for (Loop l : loops) {
			// get start transition
			Collection<Node> starts = new HashSet<Node>();
			Collection<Node> ends = new HashSet<Node>();
			Collection<Node> loopBranchElements = l.getLoopBranchElements();
			// default start and end based on both start and end set of loop
			for (Node s : l.getStarts()) {
				Collection<Node> succnodes = net.getDirectSuccessors(s);
				starts.addAll(succnodes);
			}
			for (Node e : l.getEnds()) {
				Collection<Node> prenodes = net.getDirectPredecessors(e);
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
						Collection<Node> prednodes = net
								.getDirectPredecessors(e);
						Collection<Node> outOfLoopnodes = new HashSet<Node>(
								prednodes);
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
	// apdate 1
	public Hashtable<String, Hashtable> getMatrixRelations() {
		return matrix;
	}

	public Hashtable<String, Hashtable> getMatrixRelations(
			BehaviouralProfile<NetSystem, Node> bp1, Set<Transition> transitions) {
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
				RelSetType rel = bp1.getRelationForEntities(node, node1);
				if (rel == RelSetType.Exclusive) {
					relation = "xor";
				} else if (rel == RelSetType.Interleaving) {
					relation = "and";
				} else if (rel == RelSetType.Order) {
					// check if activity is a direct 1parent to symbol or not
					Collection<Node> nodes = n.getDirectPredecessors(node1);
					for (Node p : nodes) {
						Collection<Node> pNodes = n.getDirectPredecessors(p);
						if (pNodes.contains(node)) {
							relation = "seq";

						} else {
							for (Node nt : pNodes) {
								if (nt.getLabel().contains("tau")) {
									Collection<Node> tNodes = getPredecessors(
											nt, new HashSet<Node>(), node,
											new HashSet<Node>());
									if (tNodes.size() > 0) {
										relation = "seq";
									}
								} else if (nt == node) {
									relation = "seq";
								}
							}
						}
					}

				} else if (rel == RelSetType.ReverseOrder) {
					relation = "tra";
				}
				// nodeRelationSubDic
				// .put(node1.getLabel().toLowerCase(), relation);
				nodeRelationSubDic.put(node1.getLabel(), relation);
			}
			// matrix.put(node.getLabel().toLowerCase(), nodeRelationSubDic);
			matrix.put(node.getLabel(), nodeRelationSubDic);
		}
		return matrix;
	}

	// apdate of loop
	public Hashtable<String, Hashtable> getMatrixRelations(
			BehaviouralProfile<NetSystem, Node> bp1,
			BehaviouralProfile<NetSystem, Node> bpLoopFree,
			Set<Transition> transitions, Collection<Loop> loops) {

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

				RelSetType rel = bp1.getRelationForEntities(node, node1);

				if (rel == RelSetType.Exclusive) {
					relation = "xor";
				} else if (rel == RelSetType.ReverseOrder) {
					relation = "tra";
				}

				else if (rel == RelSetType.Interleaving) {
					relation = "and";

					// Modify new part to handle false parallel of loop
					Loop loop = checkExistenceInLoop(node, node1, loops);
					if (loop != null) {
						// not on loop branch
						if (!loop.getLoopBranchElements().contains(node)
								&& !loop.getLoopBranchElements()
										.contains(node1)) {
							relation = getRelationName(bpLoopFree
									.getRelationForEntities(node, node1));

							if (loop.getEnds().contains(node)) {
								// relation = "tra";
								if (loop.getStarts().contains(node1)) {
									relation = "seq";
								}
							}
						}
						// Not finished yet
						else { // 1 activity within branch
								// node in loop branch && NODE1
							relation = "tra";
							if (loop.getEnds().contains(node)
									&& loop.getStarts().contains(node1)) {
								relation = "seq";
							}
							if (loop.getInnerEnds().contains(node)
									&& loop.getEnds().contains(node1)) {
								relation = "seq";
							}
						}
					}
				} else if (rel == RelSetType.Order) {
					// check if activity is a direct parent to symbol or not
					Collection<Node> nodes = n.getDirectPredecessors(node1);
					for (Node p : nodes) {
						Collection<Node> pNodes = n.getDirectPredecessors(p);
						if (pNodes.contains(node)) {
							relation = "seq";

						} else {
							for (Node nt : pNodes) {
								if (nt.getLabel().contains("tau")) {
									Collection<Node> tNodes = getPredecessors(
											nt, new HashSet<Node>(), node,
											new HashSet<Node>());
									if (tNodes.size() > 0) {
										relation = "seq";
									}
								} else if (nt == node) {
									relation = "seq";
								}
							}
						}
					}

				}
				// nodeRelationSubDic
				// .put(node1.getLabel().toLowerCase(), relation);
				nodeRelationSubDic.put(node1.getLabel(), relation);
			}
			// matrix.put(node.getLabel().toLowerCase(), nodeRelationSubDic);
			matrix.put(node.getLabel(), nodeRelationSubDic);
		}
		return matrix;
	}

	public String getRelationName(RelSetType rel) {
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

	public Loop checkExistenceInLoop(Node node, Node node1,
			Collection<Loop> loops) {

		for (Loop l : loops) {
			if (l.getElements().contains(node)
					&& l.getElements().contains(node1))
				return l;
		}

		return null;
	}

	public String getRelationForEntities(String node, String node1) {
		String relation = "none";
		try {
			relation = matrix.get(node).get(node1).toString();
		} catch (Exception ex) {
			relation = "none";
		}
		return relation;
	}

	public ArrayList<ArrayList<String>> getLoopselements() {
		ArrayList<ArrayList<String>> elements = new ArrayList<ArrayList<String>>();
		for (Loop l : pnLoops) {
			elements.add(l.getElementsNames());
		}
		return elements;
	}

	// public ArrayList<LoopConnection> getLoops() {
	// ArrayList<LoopConnection> elements = new ArrayList<LoopConnection>();
	// for (Loop l : pnLoops) {
	// elements.add(new LoopConnection(l));
	// }
	// return elements;
	// }
	public ArrayList<Loop> getLoops() {
		ArrayList<Loop> elements = new ArrayList<Loop>();
		for (Loop l : pnLoops) {
			elements.add(l);
		}
		return elements;
	}

	/** Predecessor part */
	public Collection<Node> getPredecessors(Node currentCheckNode,
			Collection<Node> PredTransNodes, Node nodeForSeq,
			Collection<Node> checkedTransNodes) {
		Collection<Node> Prednodes = n.getDirectPredecessors(n
				.getDirectPredecessors(currentCheckNode));
		checkedTransNodes.add(currentCheckNode);

		for (Node nt : Prednodes) {
			if (checkedTransNodes.contains(nt))
				continue;
			Boolean flag = false;
			if (nt == nodeForSeq) {
				PredTransNodes.add(nt);
				return PredTransNodes;
			} else if (nt.getLabel().contains("tau")) {
				Collection<Node> tNodes = n.getDirectPredecessors(n
						.getDirectPredecessors(nt));
				flag = tNodes.contains(nodeForSeq);
			} else {
				continue;
			}
			if (flag) {
				PredTransNodes.add(nt);
				return PredTransNodes;
			} else {
				PredTransNodes.addAll(getPredecessors(nt, PredTransNodes,
						nodeForSeq, checkedTransNodes));
			}
		}
		return PredTransNodes;
	}

	/**
	 * Its a recursion function to get all predecessors of a transition. if they
	 * include tau node, then predecessors of this node is added and tau node is
	 * removed from predecessors list
	 * 
	 * @param tranistion
	 *            : node that required its predecessors
	 * @param predecessors
	 *            : list initialized as empty , contains all direct predecessors
	 *            of node
	 * @return predecessors as above
	 */
	public Collection<Node> getPredecessors(Node transition,
			Collection<Node> Predecessors, Collection<Node> checkedTransNodes) {
		// get predecessors[output transitions] of input places of transition
		Collection<Node> Prednodes = n.getDirectPredecessors(n
				.getDirectPredecessors(transition));
		Predecessors.addAll(Prednodes);
		checkedTransNodes.add(transition);

		for (Node nt : Prednodes) {
			if (checkedTransNodes.contains(nt))
				continue;
			Boolean flag = false;
			if (nt.getLabel().contains("tau")) {
				Collection<Node> tNodes = n.getDirectPredecessors(n
						.getDirectPredecessors(nt));
				flag = true;
				Predecessors.remove(nt);
				Predecessors.addAll(tNodes);
			}
			if (flag) {
				Predecessors.addAll(getPredecessors(nt, Predecessors,
						checkedTransNodes));
			}
		}
		return Predecessors;
	}

	public Collection<String> getTransitionNames(Collection<Node> nodes) {
		Collection<String> names = new HashSet<String>();
		for (Node node : nodes) {
			names.add(node.getLabel().toLowerCase());
		}
		return names;
	}

	public Dictionary<String, ArrayList<ArrayList<String>>> getAllPredecessors() {
		return getPredecessors(getPredecessors());
	}

	public Dictionary<String, ArrayList<ArrayList<String>>> getPredecessors(
			Hashtable<String, HashSet<HashSet<String>>> predecessors) {
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

	public Hashtable<String, HashSet<HashSet<String>>> getPredecessors() {
		Hashtable<String, HashSet<HashSet<String>>> parentsMatrix = new Hashtable<String, HashSet<HashSet<String>>>();

		// ArrayList<Transition> _transitions = new ArrayList<Transition>(
		// transitions);
		// _transitions.sort((t1, t2) ->
		// t1.getLabel().compareTo(t2.getLabel()));
		for (Node transition : transitions) {
			// System.out.println("transition:" + transition.getLabel());
			if (transition.getLabel().startsWith("tau"))
				continue;
			Collection<Node> cNodes = getPredecessors(transition,
					new HashSet<Node>(), new HashSet<Node>());
			// Collection<String> nodesNames = getTransitionNames(cNodes);
			ArrayList<Node> nodes = new ArrayList<Node>(cNodes);
			// nodes.sort((n1, n2) -> n1.getLabel().compareTo(n2.getLabel()));
			// show loop on nodes to get the relation between these parents
			HashSet<HashSet<String>> transitionPredecessors = new HashSet<HashSet<String>>();
			HashSet<String> transitionPredecessorsTillNow = new HashSet<String>();
			for (int i = 0; i < nodes.size(); i++) {

				Node node = nodes.get(i);
				// System.out.println("node:" + node.getLabel());
				boolean AddAlone = true;

				if (i + 1 == nodes.size() && nodes.size() > 1) {
					if (transitionPredecessorsTillNow.contains(node.getLabel())) {
						AddAlone = false;
					}
				}
				for (int j = i + 1; j < nodes.size(); j++) {
					Node node1 = nodes.get(j);
					// System.out.println("node1:" + node1.getLabel());
					// RelSetType rel = bp1.getRelationForEntities(node, node1);
					String rel = getRelationForEntities(node.getLabel(),
							node1.getLabel());
					// if (rel == RelSetType.Exclusive) {
					if (rel.contains("xor")) {
						HashSet<String> sNode = new HashSet<String>();
						// sNode.add(node1.getLabel().toLowerCase());
						sNode.add(node1.getLabel());
						transitionPredecessorsTillNow.addAll(sNode);
						transitionPredecessors.add(sNode);
						// } else if (rel == RelSetType.Interleaving) {
					} else if (rel.contains("and")) {
						// some changes must happen here !!! to be based on
						// apdated bp
						AddAlone = false;
						// System.out.println("Before deleting : "
						// + transitionPredecessors.size());
						// to remove nodes from set first
						HashSet<String> sRNode = new HashSet<String>();
						// sRNode.add(node.getLabel().toLowerCase());
						sRNode.add(node.getLabel());
						HashSet<String> sRNode1 = new HashSet<String>();
						// sRNode1.add(node1.getLabel().toLowerCase());
						sRNode1.add(node1.getLabel());
						transitionPredecessors.remove(sRNode);
						transitionPredecessors.remove(sRNode1);
						// System.out.println("after deleting : "
						// + transitionPredecessors.size());

						HashSet<String> sNode = new HashSet<String>();
						// sNode.add(node.getLabel().toLowerCase());
						// sNode.add(node1.getLabel().toLowerCase());
						sNode.add(node.getLabel());
						sNode.add(node1.getLabel());
						transitionPredecessorsTillNow.addAll(sNode);
						transitionPredecessors.add(sNode);

					}
				}
				if (AddAlone) {
					HashSet<String> sNode = new HashSet<String>();
					// sNode.add(node.getLabel().toLowerCase());
					sNode.add(node.getLabel());
					transitionPredecessorsTillNow.addAll(sNode);
					transitionPredecessors.add(sNode);
				}
			}
			HashSet<HashSet<String>> delete = new HashSet<HashSet<String>>();
			HashSet<HashSet<String>> MergedSets = new HashSet<HashSet<String>>();
			for (HashSet<String> s1 : transitionPredecessors) {

				for (HashSet<String> s2 : transitionPredecessors) {
					if (s1 == s2)
						continue;

					if (!Collections.disjoint(s1, s2)) {

						HashSet<String> s1s2 = new HashSet<String>(s1);
						s1s2.addAll(s2);
						MergedSets.add(s1s2);
						delete.add(s1);
						delete.add(s2);
					}
				}
			}
			transitionPredecessors.removeAll(delete);
			transitionPredecessors.addAll(MergedSets);
			// parentsMatrix.put(transition.getLabel().toLowerCase(),
			// transitionPredecessors);
			parentsMatrix.put(transition.getLabel(), transitionPredecessors);
		}

		return parentsMatrix;
	}

	public static void main(String[] args) {
		BehaviouralProfileEntryPoint bpep = new BehaviouralProfileEntryPoint();
		Hashtable<String, Hashtable> matrixLoopFree = bpep.getMatrixRelations();
		// Hashtable<String, Hashtable> matrix =
		// bpep.getMatrixRelations(bpep.bp1,
		// bpep.transitions);

		// if(bpep.nloopFree!=null){
		// Hashtable<String, Hashtable> matrixLoopFree =
		// bpep.getMatrixRelations(
		// bpep.bp1, bpep.bpLoopFree, bpep.transitions, bpep.pnLoops);

		// // }
		//
		 System.out.println("BP for pn with loop");
		 // System.out.print("	");
		 Set<Transition> stransitions = bpep.bp1.getModel().getTransitions();
		 // bpep.getPredecessors();
		 // Print orginal BP
		 ArrayList<Transition> transitions = new ArrayList<Transition>(
		 stransitions);
		 transitions.sort((t1, t2) -> t1.getLabel().compareTo(t2.getLabel()));
		 System.out.print("	");
		 for (Transition transition : transitions) {
		 if (transition.getLabel().contains("tau"))
		 continue;
		 System.out.print(transition.getLabel() + "	");
		 }
		 for (Transition transition : transitions) {
		 if (transition.getLabel().contains("tau"))
		 continue;
		 System.out.println();
		 System.out.print(transition.getLabel() + "	");
		 for (Transition transition1 : transitions) {
		 if (transition1.getLabel().contains("tau"))
		 continue;
		 System.out.print(bpep.bp1.getRelationForEntities(transition,
		 transition1) + "	 ");
		 }
		 }
		 System.out.println();
		 System.out.println();
		 System.out.println();
		 System.out.print("	");
		
		// // Print orginal BP
		// stransitions = bpep.bpLoopFree.getModel().getTransitions();
		// transitions = new ArrayList<Transition>(stransitions);
		// transitions.sort((t1, t2) -> t1.getLabel().compareTo(t2.getLabel()));
		//
		// System.out.println("BP for pn without loop");
		// System.out.print("	");
		// for (Transition transition : transitions) {
		// if (transition.getLabel().contains("tau"))
		// continue;
		// System.out.print(transition.getLabel() + "	");
		// }
		// for (Transition transition : transitions) {
		// if (transition.getLabel().contains("tau"))
		// continue;
		// System.out.println();
		// System.out.print(transition.getLabel() + "	");
		// for (Transition transition1 : transitions) {
		// if (transition1.getLabel().contains("tau"))
		// continue;
		// System.out.print(bpep.bpLoopFree.getRelationForEntities(
		// transition, transition1) + "	 ");
		// }
		// }
		// System.out.println();
		// System.out.println();
		// System.out.println();
		// System.out.print("	");
		//
		// // Adapted BP
		// System.out.println("Adapted v1 for pn with loop");
		// System.out.print("	");
		// for (String e : matrix.keySet()) {
		// System.out.print(e + "        ");
		// }
		// for (String e : matrix.keySet()) {
		// System.out.println();
		// System.out.print(e + "       ");
		// Hashtable<String, String> rel = matrix.get(e);
		// for (String km : rel.keySet()) {
		// System.out.print(rel.get(km) + "      ");
		// }
		// }
		//
		// System.out.println();
		// System.out.println();
		// System.out.println();
		//
		// Adapted BPLoop
		System.out.println("Adapted v2 for pn with loop");
		System.out.print("	");

		for (String e : matrixLoopFree.keySet()) {
			System.out.print(e + "        ");
		}
		for (String e : matrixLoopFree.keySet()) {
			System.out.println();
			System.out.print(e + "       ");
			Hashtable<String, String> rel = matrixLoopFree.get(e);
			for (String km : rel.keySet()) {
				System.out.print(rel.get(km) + " 	     ");
			}
		}

		// printing predecessors
		System.out.println();
		System.out.println();
		System.out.println("Predecesssors");
		Hashtable<String, HashSet<HashSet<String>>> predecessors = bpep
				.getPredecessors();
		Set<String> keys = predecessors.keySet();
		for (String key : keys) {
			System.out.print(key + ": [");
			HashSet<HashSet<String>> pre = predecessors.get(key);
			for (HashSet<String> p : pre) {
				System.out.print("[");
				for (String s : p) {
					System.out.print(s + ",");
				}
				System.out.print("]");
			}
			System.out.print("]");
		}
		System.out.println();
		System.out.println();

		for (Loop l : bpep.pnLoops) {
			l.print();
			System.out.println("---------------------------");

		}
		// open gateway
		GatewayServer gatewayServer = new GatewayServer(bpep);
		// new BehaviouralProfileEntryPoint());
		try {
			gatewayServer.start();
			System.out.println("open gate");
		} catch (Exception ex) {
			System.out.println("This channel  must be closed from python");
			// gatewayServer.shutdown();
			// gatewayServer.start();
		}

	}

}
