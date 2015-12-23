package Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.jbpt.petri.*;

import py4j.GatewayServer;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PetriNet tauPetriLoop = new PetriNet();

		Transition tauPetriLoopt1 = new Transition("a");
		Transition tauPetriLoopt2 = new Transition("b");
		Transition tauPetriLoopt3 = new Transition("c");
		Transition tauPetriLoopt4 = new Transition("d");
		Transition tauPetriLooptt5 = new Transition("tau from tree");
		Transition tauPetriLooptt6 = new Transition("u");
		Place tauPetriLoopp1 = new Place("1");
		Place tauPetriLoopp2 = new Place("2");
		Place tauPetriLoopp3 = new Place("3");
		Place tauPetriLoopp4 = new Place("4");
		Place tauPetriLoopp5 = new Place("5");

		tauPetriLoop.addNode(tauPetriLoopt1);
		tauPetriLoop.addNode(tauPetriLoopt2);
		tauPetriLoop.addNode(tauPetriLoopt3);
		tauPetriLoop.addNode(tauPetriLoopt4);
		tauPetriLoop.addNode(tauPetriLooptt5);
		tauPetriLoop.addNode(tauPetriLooptt6);

		tauPetriLoop.addNode(tauPetriLoopp1);
		tauPetriLoop.addNode(tauPetriLoopp2);
		tauPetriLoop.addNode(tauPetriLoopp3);
		tauPetriLoop.addNode(tauPetriLoopp4);
		tauPetriLoop.addNode(tauPetriLoopp5);

		tauPetriLoop.addFlow(tauPetriLoopp1, tauPetriLoopt1);
		tauPetriLoop.addFlow(tauPetriLoopt1, tauPetriLoopp2);
		tauPetriLoop.addFlow(tauPetriLoopp2, tauPetriLoopt2);
		tauPetriLoop.addFlow(tauPetriLoopt2, tauPetriLoopp3);
		tauPetriLoop.addFlow(tauPetriLoopp3, tauPetriLoopt3);
		tauPetriLoop.addFlow(tauPetriLoopp2, tauPetriLooptt5);
		tauPetriLoop.addFlow(tauPetriLooptt5, tauPetriLoopp4);
		tauPetriLoop.addFlow(tauPetriLoopt3, tauPetriLoopp4);
		tauPetriLoop.addFlow(tauPetriLoopp4, tauPetriLoopt4);
		tauPetriLoop.addFlow(tauPetriLoopp4, tauPetriLooptt6);
		tauPetriLoop.addFlow(tauPetriLooptt6, tauPetriLoopp2);
		tauPetriLoop.addFlow(tauPetriLoopt4, tauPetriLoopp5);
		// *******************************************************************\\
		PetriNet nestedPetriLoop = new PetriNet();

		Transition nestedPetriLoopt1 = new Transition("a");
		Transition nestedPetriLoopt2 = new Transition("b");
		Transition nestedPetriLoopt3 = new Transition("c");
		Transition nestedPetriLoopt4 = new Transition("d");
		Transition nestedPetriLoopt5 = new Transition("e");
		Transition nestedPetriLoopt6 = new Transition("f");
		Transition nestedPetriLoopt7 = new Transition("g");
		Transition nestedPetriLoopt8 = new Transition("h");
		Transition nestedPetriLooptt2 = new Transition("tau Inner");
		Transition nestedPetriLooptt1 = new Transition("tau outter ");
		Transition nestedPetriLooptt3 = new Transition("tau 3 ");
		Place nestedPetriLoopp1 = new Place("1");
		Place nestedPetriLoopp2 = new Place("2");
		Place nestedPetriLoopp3 = new Place("3");
		Place nestedPetriLoopp4 = new Place("4");
		Place nestedPetriLoopp5 = new Place("5");
		Place nestedPetriLoopp6 = new Place("6");
		Place nestedPetriLoopp7 = new Place("7");
		Place nestedPetriLoopp8 = new Place("8");

		nestedPetriLoop.addNode(nestedPetriLoopt1);
		nestedPetriLoop.addNode(nestedPetriLoopt2);
		nestedPetriLoop.addNode(nestedPetriLoopt3);
		nestedPetriLoop.addNode(nestedPetriLoopt4);
		nestedPetriLoop.addNode(nestedPetriLoopt5);
		nestedPetriLoop.addNode(nestedPetriLoopt6);
		nestedPetriLoop.addNode(nestedPetriLoopt7);
		nestedPetriLoop.addNode(nestedPetriLoopt8);
		nestedPetriLoop.addNode(nestedPetriLooptt1);
		nestedPetriLoop.addNode(nestedPetriLooptt2);

		nestedPetriLoop.addNode(nestedPetriLoopp1);
		nestedPetriLoop.addNode(nestedPetriLoopp2);
		nestedPetriLoop.addNode(nestedPetriLoopp3);
		nestedPetriLoop.addNode(nestedPetriLoopp4);
		nestedPetriLoop.addNode(nestedPetriLoopp5);
		nestedPetriLoop.addNode(nestedPetriLoopp6);
		nestedPetriLoop.addNode(nestedPetriLoopp7);
		nestedPetriLoop.addNode(nestedPetriLoopp8);

		nestedPetriLoop.addFlow(nestedPetriLoopp1, nestedPetriLoopt1);// 1-A
		nestedPetriLoop.addFlow(nestedPetriLoopt1, nestedPetriLoopp2);// A-2
		nestedPetriLoop.addFlow(nestedPetriLoopp2, nestedPetriLoopt2);// 2-B
		nestedPetriLoop.addFlow(nestedPetriLoopt2, nestedPetriLoopp3);// B-3
		nestedPetriLoop.addFlow(nestedPetriLoopp3, nestedPetriLoopt3);// 3-c
		nestedPetriLoop.addFlow(nestedPetriLoopt3, nestedPetriLoopp4);// c-4
		nestedPetriLoop.addFlow(nestedPetriLoopp4, nestedPetriLoopt4);// 4-d
		nestedPetriLoop.addFlow(nestedPetriLoopt4, nestedPetriLoopp5);// d-5
		nestedPetriLoop.addFlow(nestedPetriLoopp3, nestedPetriLoopt5);// 3-e
		nestedPetriLoop.addFlow(nestedPetriLoopt5, nestedPetriLoopp5);// e-5
		nestedPetriLoop.addFlow(nestedPetriLoopp5, nestedPetriLooptt3);// 5-tau3
		nestedPetriLoop.addFlow(nestedPetriLooptt3, nestedPetriLoopp7);// tau3 -
																		// 7

		nestedPetriLoop.addFlow(nestedPetriLoopp5, nestedPetriLooptt2);// 5-tau2
		nestedPetriLoop.addFlow(nestedPetriLooptt2, nestedPetriLoopp3);// tau2-3

		nestedPetriLoop.addFlow(nestedPetriLoopp2, nestedPetriLoopt6);// 2-f
		nestedPetriLoop.addFlow(nestedPetriLoopt6, nestedPetriLoopp6);// f-6
		nestedPetriLoop.addFlow(nestedPetriLoopp6, nestedPetriLoopt7);// 6-g
		nestedPetriLoop.addFlow(nestedPetriLoopt7, nestedPetriLoopp7);// g-7

		nestedPetriLoop.addFlow(nestedPetriLoopp7, nestedPetriLooptt1);// 7-tau1
		nestedPetriLoop.addFlow(nestedPetriLooptt1, nestedPetriLoopp2);// tau1-2

		nestedPetriLoop.addFlow(nestedPetriLoopp7, nestedPetriLoopt8);// 7-h
		nestedPetriLoop.addFlow(nestedPetriLoopt8, nestedPetriLoopp8);// h-8

		// *******************************************************************\\
		// petri net with set of predecessors
		PetriNet prePetri = new PetriNet();

		Transition prePetrit1 = new Transition("a");
		Transition prePetrit2 = new Transition("b");
		Transition prePetrit3 = new Transition("c");
		Transition prePetrit4 = new Transition("d");
		Transition prePetrit5 = new Transition("e");
		Transition prePetrit6 = new Transition("f");
		Transition prePetrit7 = new Transition("g");
		Transition prePetrit8 = new Transition("h");
		Transition prePetrit9 = new Transition("i");
		Transition prePetritt1 = new Transition("tau 1");
		Transition prePetritt2 = new Transition("tau 2");
		Transition prePetritt3 = new Transition("tau 3");
		Transition prePetritt4 = new Transition("tau 4");
		Transition prePetritt5 = new Transition("tau 5");

		Place prePetritp1 = new Place("1");
		Place prePetritp2 = new Place("2");
		Place prePetritp3 = new Place("3");
		Place prePetritp4 = new Place("4");
		Place prePetritp5 = new Place("5");
		Place prePetritp6 = new Place("6");
		Place prePetritp7 = new Place("7");
		Place prePetritp8 = new Place("8");
		Place prePetritp9 = new Place("9");
		Place prePetritp10 = new Place("10");
		Place prePetritp11 = new Place("11");
		Place prePetritp12 = new Place("12");
		Place prePetritp13 = new Place("13");
		Place prePetritp14 = new Place("14");
		Place prePetritp15 = new Place("15s");

		prePetri.addNode(prePetrit1);
		prePetri.addNode(prePetrit2);
		prePetri.addNode(prePetrit3);
		prePetri.addNode(prePetrit4);
		prePetri.addNode(prePetrit5);
		prePetri.addNode(prePetrit6);
		prePetri.addNode(prePetrit7);
		prePetri.addNode(prePetrit8);
		prePetri.addNode(prePetrit9);

		prePetri.addNode(prePetritt1);
		prePetri.addNode(prePetritt2);
		prePetri.addNode(prePetritt3);
		prePetri.addNode(prePetritt4);
		prePetri.addNode(prePetritt5);
		prePetri.addNode(prePetritp1);
		prePetri.addNode(prePetritp2);
		prePetri.addNode(prePetritp3);
		prePetri.addNode(prePetritp4);
		prePetri.addNode(prePetritp5);
		prePetri.addNode(prePetritp6);
		prePetri.addNode(prePetritp7);
		prePetri.addNode(prePetritp8);
		prePetri.addNode(prePetritp9);
		prePetri.addNode(prePetritp10);
		prePetri.addNode(prePetritp11);
		prePetri.addNode(prePetritp12);
		prePetri.addNode(prePetritp13);
		prePetri.addNode(prePetritp14);
		prePetri.addNode(prePetritp15);

		prePetri.addFlow(prePetritp1, prePetrit1);// 1-a
		prePetri.addFlow(prePetrit1, prePetritp2);// a-2
		prePetri.addFlow(prePetritp2, prePetritt1);// 2-tau1
		prePetri.addFlow(prePetritt1, prePetritp3);// tau1-3
		prePetri.addFlow(prePetritp3, prePetrit2);// 3-b
		prePetri.addFlow(prePetritp3, prePetrit3);// 3-c
		prePetri.addFlow(prePetrit2, prePetritp4);// b-4
		prePetri.addFlow(prePetrit3, prePetritp4);// c-4
		prePetri.addFlow(prePetritp4, prePetritt2);// 4-tau2
		prePetri.addFlow(prePetritt1, prePetritp5);// tau1-5
		prePetri.addFlow(prePetritp5, prePetrit4);// 5-d
		prePetri.addFlow(prePetritp5, prePetrit5);// 5-e
		prePetri.addFlow(prePetrit4, prePetritp6);// d-6
		prePetri.addFlow(prePetrit5, prePetritp6);// e-6
		prePetri.addFlow(prePetritp6, prePetritt2);// 6-tau2
		prePetri.addFlow(prePetritt2, prePetritp7);// tau2-7
		prePetri.addFlow(prePetritp7, prePetritt5);// 7-tau5

		prePetri.addFlow(prePetrit1, prePetritp8);// a-8
		prePetri.addFlow(prePetritp8, prePetrit6);// 8-f
		prePetri.addFlow(prePetrit6, prePetritp13);// f-13
		prePetri.addFlow(prePetritp8, prePetritt3);// 8-tau3
		prePetri.addFlow(prePetritt3, prePetritp9);// tau3-9
		prePetri.addFlow(prePetritp9, prePetrit7);// 9-g
		prePetri.addFlow(prePetrit7, prePetritp10);// g-10
		prePetri.addFlow(prePetritp10, prePetritt4);// 10-tau4
		prePetri.addFlow(prePetritt3, prePetritp11);// tau3-11
		prePetri.addFlow(prePetritp11, prePetrit8);// 11-h
		prePetri.addFlow(prePetrit8, prePetritp12);// h-12
		prePetri.addFlow(prePetritp12, prePetritt4);// 12-tau4
		prePetri.addFlow(prePetritt4, prePetritp13);// tau4-13
		prePetri.addFlow(prePetritp13, prePetritt5);// 13-tau5
		prePetri.addFlow(prePetritt5, prePetritp14);// tau5-14

		prePetri.addFlow(prePetritp14, prePetrit9);// 14-i
		prePetri.addFlow(prePetrit9, prePetritp15);// i-14

		// *******************************************************************\\
		PetriNet preLoopNestedAndPetriNet = new PetriNet();

		Transition preLoopNestedAndPetriNett1 = new Transition("A");
		Transition preLoopNestedAndPetriNett2 = new Transition("B");
		Transition preLoopNestedAndPetriNett3 = new Transition("C");
		Transition preLoopNestedAndPetriNett4 = new Transition("D");
		Transition preLoopNestedAndPetriNett5 = new Transition("E");
		Transition preLoopNestedAndPetriNett6 = new Transition("F");
		Transition preLoopNestedAndPetriNett7 = new Transition("H");
		Transition preLoopNestedAndPetriNett8 = new Transition("I");
		Transition preLoopNestedAndPetriNett9 = new Transition("J");
		Transition preLoopNestedAndPetriNett10 = new Transition("K");
		Transition preLoopNestedAndPetriNett11 = new Transition("L");
		Transition preLoopNestedAndPetriNett12 = new Transition("M");
		Transition preLoopNestedAndPetriNettau1 = new Transition("tau1");
		Transition preLoopNestedAndPetriNettau2 = new Transition("tau2");
		Transition preLoopNestedAndPetriNettau3 = new Transition("tau3");
		Transition preLoopNestedAndPetriNettau4 = new Transition("tau4");
		Transition preLoopNestedAndPetriNettau5 = new Transition("tau5");
		Transition preLoopNestedAndPetriNettau6 = new Transition("tau6");
		Transition preLoopNestedAndPetriNettau7 = new Transition("tau7");
		Transition preLoopNestedAndPetriNettau8 = new Transition("tau 8");

		Place preLoopNestedAndPetriNetp1 = new Place("1");
		Place preLoopNestedAndPetriNetp2 = new Place("2");
		Place preLoopNestedAndPetriNetp3 = new Place("3");
		Place preLoopNestedAndPetriNetp4 = new Place("4");
		Place preLoopNestedAndPetriNetp5 = new Place("5");
		Place preLoopNestedAndPetriNetp6 = new Place("6");
		Place preLoopNestedAndPetriNetp7 = new Place("7");
		Place preLoopNestedAndPetriNetp8 = new Place("8");
		Place preLoopNestedAndPetriNetp9 = new Place("9");
		Place preLoopNestedAndPetriNetp10 = new Place("10");
		Place preLoopNestedAndPetriNetp11 = new Place("11");
		Place preLoopNestedAndPetriNetp12 = new Place("12");
		Place preLoopNestedAndPetriNetp13 = new Place("13");
		Place preLoopNestedAndPetriNetp14 = new Place("14");
		Place preLoopNestedAndPetriNetp15 = new Place("15");
		Place preLoopNestedAndPetriNetp16 = new Place("16");
		Place preLoopNestedAndPetriNetp17 = new Place("17");
		Place preLoopNestedAndPetriNetp18 = new Place("18");
		Place preLoopNestedAndPetriNetp19 = new Place("19");
		Place preLoopNestedAndPetriNetp20 = new Place("20");

		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp1, preLoopNestedAndPetriNett1);// 1-a
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett1, preLoopNestedAndPetriNetp2);// a-2
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp2, preLoopNestedAndPetriNettau1);// 2-tau1

		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau1, preLoopNestedAndPetriNetp3);// tau1-3
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp3, preLoopNestedAndPetriNett2);// 3-b
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett2, preLoopNestedAndPetriNetp4);// b-4
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp4, preLoopNestedAndPetriNett4);// 4-d
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett4, preLoopNestedAndPetriNetp5);// d-5
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp5, preLoopNestedAndPetriNett5);// 5-e
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett5, preLoopNestedAndPetriNetp7);// e-7
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp4, preLoopNestedAndPetriNett6);// 4-f
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett6, preLoopNestedAndPetriNetp6);// f-6
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp6, preLoopNestedAndPetriNett7);// 6-h
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett7, preLoopNestedAndPetriNetp7);// h-7
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp7, preLoopNestedAndPetriNettau2);// 7-
																									// tau2
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau2, preLoopNestedAndPetriNetp4);// tau2-4
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp7, preLoopNestedAndPetriNettau3);// 7-tau3

		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau1, preLoopNestedAndPetriNetp8);// tau1-8
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp8, preLoopNestedAndPetriNett3);// 8-c
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett3, preLoopNestedAndPetriNetp9);// c-9
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp9, preLoopNestedAndPetriNettau3);// 9-
																									// tau3
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau3, preLoopNestedAndPetriNetp10);// tau3-10
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp10, preLoopNestedAndPetriNett12);// 10-m

		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett1, preLoopNestedAndPetriNetp11);// a-11
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp11, preLoopNestedAndPetriNettau4);// 11-tau4
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau4, preLoopNestedAndPetriNetp12);// tau4-12
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp12, preLoopNestedAndPetriNettau5);// 12-tau5
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau5, preLoopNestedAndPetriNetp13);// tau5-13
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp13, preLoopNestedAndPetriNett8);// 13-i
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett8, preLoopNestedAndPetriNetp14);// i-14
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp14, preLoopNestedAndPetriNett10);// 14-k
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett10, preLoopNestedAndPetriNetp15);// k-15
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp15, preLoopNestedAndPetriNettau6);// 15-tau6
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau5, preLoopNestedAndPetriNetp16);// tau5-16
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp16, preLoopNestedAndPetriNett9);// 16-j
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett9, preLoopNestedAndPetriNetp17);// j-17
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp17, preLoopNestedAndPetriNettau6);// 17-tau6
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau6, preLoopNestedAndPetriNetp18);// tau6-18
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp18, preLoopNestedAndPetriNettau7);// 18-tau7
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau7, preLoopNestedAndPetriNetp12);// tau7-12
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp18, preLoopNestedAndPetriNett11);// 18-l
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett11, preLoopNestedAndPetriNetp19);// l-19
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp19, preLoopNestedAndPetriNettau8);// 19-tau8
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNettau8, preLoopNestedAndPetriNetp11);// 18-tau7
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNetp19, preLoopNestedAndPetriNett12);// 18-tau7
		preLoopNestedAndPetriNet.addFlow(preLoopNestedAndPetriNett12, preLoopNestedAndPetriNetp20);// 18-tau7

		// *******************************************************************\\
		// bpi coselog with 0 induct
		PetriNet bpiCoseLog = new PetriNet();
		Transition bpiCoseLogt1 = new Transition("Confirmation of receipt");
		Transition bpiCoseLogt2 = new Transition("T06 Determine necessity of stop advice");
		Transition bpiCoseLogt3 = new Transition("T02 Check confirmation of receipt");
		Transition bpiCoseLogt4 = new Transition("T07-4 Draft internal advice to hold for type 4");
		Transition bpiCoseLogt5 = new Transition("T17 Check report Y to stop indication");
		Transition bpiCoseLogt6 = new Transition("T12 Check document X request unlicensed");
		Transition bpiCoseLogt7 = new Transition("T14 Determine document X request unlicensed");
		Transition bpiCoseLogt8 = new Transition("T04 Determine confirmation of receipt");
		Transition bpiCoseLogt9 = new Transition("T18 Adjust report Y to stop indicition");
		Transition bpiCoseLogt10 = new Transition("T10 Determine necessity to stop indication");
		Transition bpiCoseLogt11 = new Transition("T20 Print report Y to stop indication");
		Transition bpiCoseLogt12 = new Transition("T11 Create document X request unlicensed");
		Transition bpiCoseLogt13 = new Transition("T16 Report reasons to hold request");
		Transition bpiCoseLogt14 = new Transition("T13 Adjust document X request unlicensed");
		Transition bpiCoseLogt15 = new Transition("T19 Determine report Y to stop indication");
		Transition bpiCoseLogt16 = new Transition("T05 Print and send confirmation of receipt");
		Transition bpiCoseLogt17 = new Transition("T07-1 Draft intern advice aspect 1");
		Transition bpiCoseLogt18 = new Transition("T07-3 Draft intern advice hold for aspect 3");
		Transition bpiCoseLogt19 = new Transition("T09-1 Process or receive external advice from party 1");
		Transition bpiCoseLogt20 = new Transition("T09-3 Process or receive external advice from party 3");
		Transition bpiCoseLogt21 = new Transition("T09-2 Process or receive external advice from party 2");
		Transition bpiCoseLogt22 = new Transition("T09-4 Process or receive external advice from party 4");
		Transition bpiCoseLogt23 = new Transition("T08 Draft and send request for advice");
		Transition bpiCoseLogt24 = new Transition("T07-5 Draft intern advice aspect 5");
		Transition bpiCoseLogt25 = new Transition("T07-2 Draft intern advice aspect 2");
		Transition bpiCoseLogt26 = new Transition("T03 Adjust confirmation of receipt");
		Transition bpiCoseLogt27 = new Transition("T15 Print document X request unlicensed");

		Transition bpiCoseLogtau1 = new Transition("tau1");
		Transition bpiCoseLogtau2 = new Transition("tau2");
		Transition bpiCoseLogtau3 = new Transition("tau3");
		Transition bpiCoseLogtau4 = new Transition("tau4");
		Transition bpiCoseLogtau5 = new Transition("tau5");
		Transition bpiCoseLogtau6 = new Transition("tau6");
		Transition bpiCoseLogtau7 = new Transition("tau7");
		Transition bpiCoseLogtau8 = new Transition("tau8");
		Transition bpiCoseLogtau9 = new Transition("tau9");
		Transition bpiCoseLogtau10 = new Transition("tau10");
		Transition bpiCoseLogtau11 = new Transition("tau11");
		Transition bpiCoseLogtau12 = new Transition("tau12");
		Transition bpiCoseLogtau13 = new Transition("tau13");

		Place bpiCoseLogp1 = new Place("p1");
		Place bpiCoseLogp2 = new Place("p2");
		Place bpiCoseLogp3 = new Place("p3");
		Place bpiCoseLogp4 = new Place("p4");
		Place bpiCoseLogp5 = new Place("p5");
		Place bpiCoseLogp6 = new Place("p6");
		Place bpiCoseLogp7 = new Place("p7");
		Place bpiCoseLogp8 = new Place("p8");
		Place bpiCoseLogp9 = new Place("p9");
		Place bpiCoseLogp10 = new Place("p10");
		Place bpiCoseLogp11 = new Place("p11");
		Place bpiCoseLogp12 = new Place("p12");

		bpiCoseLog.addFlow(bpiCoseLogp1, bpiCoseLogt1);// 1- t1
		bpiCoseLog.addFlow(bpiCoseLogt1, bpiCoseLogp2);// t1-2
		bpiCoseLog.addFlow(bpiCoseLogp2, bpiCoseLogtau1);// 2-tau1
		bpiCoseLog.addFlow(bpiCoseLogtau1, bpiCoseLogp12);// tau1-2
		bpiCoseLog.addFlow(bpiCoseLogp2, bpiCoseLogt2);// 2-t2
		bpiCoseLog.addFlow(bpiCoseLogt2, bpiCoseLogp3);// t2-3
		bpiCoseLog.addFlow(bpiCoseLogp2, bpiCoseLogt3);// 2-t3
		bpiCoseLog.addFlow(bpiCoseLogt3, bpiCoseLogp3);// t3-p3
		bpiCoseLog.addFlow(bpiCoseLogp3, bpiCoseLogt4);// p3 -t4
		bpiCoseLog.addFlow(bpiCoseLogt4, bpiCoseLogp8);// t4-8
		bpiCoseLog.addFlow(bpiCoseLogp3, bpiCoseLogtau2);// 3-tau2
		bpiCoseLog.addFlow(bpiCoseLogtau2, bpiCoseLogp4);// tau2-4
		bpiCoseLog.addFlow(bpiCoseLogp4, bpiCoseLogtau3);// 4-tau3
		bpiCoseLog.addFlow(bpiCoseLogtau3, bpiCoseLogp5);// tau3-5
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogtau13);// 5-tau13
		bpiCoseLog.addFlow(bpiCoseLogtau13, bpiCoseLogp8);// tau13-8
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt5);// 5-t5
		bpiCoseLog.addFlow(bpiCoseLogt5, bpiCoseLogp4);// t5-4 [inner loop1]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt6);// 5-t6
		bpiCoseLog.addFlow(bpiCoseLogt6, bpiCoseLogp4);// t6-4 [inner loop2]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt7);// 5-t7
		bpiCoseLog.addFlow(bpiCoseLogt7, bpiCoseLogp4);// t7-4 [inner loop3]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt8);// 5-t8
		bpiCoseLog.addFlow(bpiCoseLogt8, bpiCoseLogp4);// t8-4 [inner loop4]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt9);// 5-t9
		bpiCoseLog.addFlow(bpiCoseLogt9, bpiCoseLogp4);// t9-4 [inner loop5]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt10);// 5-t10
		bpiCoseLog.addFlow(bpiCoseLogt10, bpiCoseLogp4);// t10-4 [inner loop6]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt11);// 5-t11
		bpiCoseLog.addFlow(bpiCoseLogt11, bpiCoseLogp4);// t11-4 [inner loop7]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt12);// 5-t12
		bpiCoseLog.addFlow(bpiCoseLogt12, bpiCoseLogp4);// t12-4 [inner loop8]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt13);// 5-t13
		bpiCoseLog.addFlow(bpiCoseLogt13, bpiCoseLogp4);// t13-4 [inner loop9]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt14);// 5-t14
		bpiCoseLog.addFlow(bpiCoseLogt14, bpiCoseLogp4);// t14-4 [inner loop10]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt15);// 5-t15
		bpiCoseLog.addFlow(bpiCoseLogt15, bpiCoseLogp4);// t15-4 [inner loop11]
		bpiCoseLog.addFlow(bpiCoseLogp5, bpiCoseLogt16);// 5-t16
		bpiCoseLog.addFlow(bpiCoseLogt16, bpiCoseLogp4);// t16-4 [inner loop12]
		bpiCoseLog.addFlow(bpiCoseLogp3, bpiCoseLogt17);// 3-t17
		bpiCoseLog.addFlow(bpiCoseLogt17, bpiCoseLogp8);// t17-8
		bpiCoseLog.addFlow(bpiCoseLogp3, bpiCoseLogt18);// 3-t18
		bpiCoseLog.addFlow(bpiCoseLogt18, bpiCoseLogp8);// t18-8
		bpiCoseLog.addFlow(bpiCoseLogp3, bpiCoseLogtau4);// 3-tau4
		bpiCoseLog.addFlow(bpiCoseLogtau4, bpiCoseLogp6);// tau4-6
		bpiCoseLog.addFlow(bpiCoseLogp6, bpiCoseLogtau5);// 6-tau5
		bpiCoseLog.addFlow(bpiCoseLogtau5, bpiCoseLogp7);// tau5-7
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt19);// 7-t19
		bpiCoseLog.addFlow(bpiCoseLogt19, bpiCoseLogp6);// t19-7 [inner loop13]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt20);// 7-t20
		bpiCoseLog.addFlow(bpiCoseLogt20, bpiCoseLogp6);// t20-7 [inner loop14]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt21);// 7-t21
		bpiCoseLog.addFlow(bpiCoseLogt21, bpiCoseLogp6);// t21-7 [inner loop14]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt22);// 7-t22
		bpiCoseLog.addFlow(bpiCoseLogt22, bpiCoseLogp6);// t22-7 [inner loop15]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt23);// 7-t23
		bpiCoseLog.addFlow(bpiCoseLogt23, bpiCoseLogp6);// t23-7 [inner loop16]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt24);// 7-t24
		bpiCoseLog.addFlow(bpiCoseLogt24, bpiCoseLogp6);// t24-7 [inner loop17]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogt25);// 7-t24
		bpiCoseLog.addFlow(bpiCoseLogt25, bpiCoseLogp6);// t24-7 [inner loop18]
		bpiCoseLog.addFlow(bpiCoseLogp7, bpiCoseLogtau6);// 7-tau6
		bpiCoseLog.addFlow(bpiCoseLogtau6, bpiCoseLogp8);// tau6-8

		bpiCoseLog.addFlow(bpiCoseLogp8, bpiCoseLogtau12);// 8-tau12
		bpiCoseLog.addFlow(bpiCoseLogtau12, bpiCoseLogp3);// tau12 - 3[outer -
															// inner 1 ]
		bpiCoseLog.addFlow(bpiCoseLogp8, bpiCoseLogtau7);// 8-tau7
		bpiCoseLog.addFlow(bpiCoseLogtau7, bpiCoseLogp9);// tau7-9
		bpiCoseLog.addFlow(bpiCoseLogp9, bpiCoseLogtau8);// 9-tau8
		bpiCoseLog.addFlow(bpiCoseLogtau8, bpiCoseLogp10);// tau8-10
		bpiCoseLog.addFlow(bpiCoseLogp9, bpiCoseLogt26);// 9-t26
		bpiCoseLog.addFlow(bpiCoseLogt26, bpiCoseLogp10);// t26-10
		bpiCoseLog.addFlow(bpiCoseLogp10, bpiCoseLogtau9);// 10-tau9
		bpiCoseLog.addFlow(bpiCoseLogtau9, bpiCoseLogp11);// tau9-11
		bpiCoseLog.addFlow(bpiCoseLogp11, bpiCoseLogtau10);// 11-tau10
		bpiCoseLog.addFlow(bpiCoseLogtau10, bpiCoseLogp12);// tau10-12
		bpiCoseLog.addFlow(bpiCoseLogp11, bpiCoseLogt27);// 11-t27
		bpiCoseLog.addFlow(bpiCoseLogt27, bpiCoseLogp12);// t27-12
		bpiCoseLog.addFlow(bpiCoseLogp10, bpiCoseLogtau11);// 10-tau11
		bpiCoseLog.addFlow(bpiCoseLogtau11, bpiCoseLogp2);// tau11-2 [outer 1]

		// *******************************************************************\\
		// coselog with 20 %
		PetriNet bpiCoseLog20 = new PetriNet();
		Transition bpiCoseLog20t1 = new Transition("Confirmation of receipt");
		Transition bpiCoseLog20t2 = new Transition("T07-4 Draft internal advice to hold for type 4");
		Transition bpiCoseLog20t3 = new Transition("T07-5 Draft intern advice aspect 5");
		Transition bpiCoseLog20t4 = new Transition("T07-2 Draft intern advice aspect 2");
		Transition bpiCoseLog20t5 = new Transition("T07-1 Draft intern advice aspect 1");
		Transition bpiCoseLog20t6 = new Transition("T03 Adjust confirmation of receipt");
		Transition bpiCoseLog20t7 = new Transition("T07-3 Draft intern advice hold for aspect 3");
		Transition bpiCoseLog20t8 = new Transition("T09-1 Process or receive external advice from party 1");
		Transition bpiCoseLog20t9 = new Transition("T08 Draft and send request for advice");
		Transition bpiCoseLog20t10 = new Transition("T09-4 Process or receive external advice from party 4");
		Transition bpiCoseLog20t11 = new Transition("T09-3 Process or receive external advice from party 3");
		Transition bpiCoseLog20t12 = new Transition("T09-2 Process or receive external advice from party 2");
		Transition bpiCoseLog20t13 = new Transition("T11 Create document X request unlicensed");
		Transition bpiCoseLog20t14 = new Transition("T12 Check document X request unlicensed");
		Transition bpiCoseLog20t15 = new Transition("T16 Report reasons to hold request");
		Transition bpiCoseLog20t16 = new Transition("T17 Check report Y to stop indication");
		Transition bpiCoseLog20t17 = new Transition("T18 Adjust report Y to stop indicition");
		Transition bpiCoseLog20t18 = new Transition("T19 Determine report Y to stop indication");
		Transition bpiCoseLog20t19 = new Transition("T20 Print report Y to stop indication");
		Transition bpiCoseLog20t20 = new Transition("T04 Determine confirmation of receipt");
		Transition bpiCoseLog20t21 = new Transition("T02 Check confirmation of receipt");
		Transition bpiCoseLog20t22 = new Transition("T14 Determine document X request unlicensed");
		Transition bpiCoseLog20t23 = new Transition("T05 Print and send confirmation of receipt");
		Transition bpiCoseLog20t24 = new Transition("T06 Determine necessity of stop advice");
		Transition bpiCoseLog20t25 = new Transition("T10 Determine necessity to stop indication");
		Transition bpiCoseLog20t26 = new Transition("T15 Print document X request unlicensed");

		Transition bpiCoseLog20tau1 = new Transition("tau1");
		Transition bpiCoseLog20tau2 = new Transition("tau2");
		Transition bpiCoseLog20tau3 = new Transition("tau3");
		Transition bpiCoseLog20tau4 = new Transition("tau4");
		Transition bpiCoseLog20tau5 = new Transition("tau5");
		Transition bpiCoseLog20tau6 = new Transition("tau6");
		Transition bpiCoseLog20tau7 = new Transition("tau7");
		Transition bpiCoseLog20tau8 = new Transition("tau8");
		Transition bpiCoseLog20tau9 = new Transition("tau9");
		Transition bpiCoseLog20tau10 = new Transition("tau10");
		Transition bpiCoseLog20tau11 = new Transition("tau11");
		Transition bpiCoseLog20tau12 = new Transition("tau12");

		Place bpiCoseLog20p1 = new Place("p1");
		Place bpiCoseLog20p2 = new Place("p2");
		Place bpiCoseLog20p3 = new Place("p3");
		Place bpiCoseLog20p4 = new Place("p4");
		Place bpiCoseLog20p5 = new Place("p5");
		Place bpiCoseLog20p6 = new Place("p6");
		Place bpiCoseLog20p7 = new Place("p7");
		Place bpiCoseLog20p8 = new Place("p8");
		Place bpiCoseLog20p9 = new Place("p9");
		Place bpiCoseLog20p10 = new Place("p10");
		Place bpiCoseLog20p11 = new Place("p11");
		Place bpiCoseLog20p12 = new Place("p12");
		Place bpiCoseLog20p13 = new Place("p13");
		Place bpiCoseLog20p14 = new Place("p14");
		Place bpiCoseLog20p15 = new Place("p15");
		Place bpiCoseLog20p16 = new Place("p16");
		Place bpiCoseLog20p17 = new Place("p17");
		Place bpiCoseLog20p18 = new Place("p18");
		Place bpiCoseLog20p19 = new Place("p19");
		Place bpiCoseLog20p20 = new Place("p20");
		Place bpiCoseLog20p21 = new Place("p21");
		Place bpiCoseLog20p22 = new Place("p22");

		bpiCoseLog20.addFlow(bpiCoseLog20p1, bpiCoseLog20t1);// 1-t1
		bpiCoseLog20.addFlow(bpiCoseLog20t1, bpiCoseLog20p2);// t1-2
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20t2);// 2-t2
		bpiCoseLog20.addFlow(bpiCoseLog20t2, bpiCoseLog20p5);// t2-5
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20t3);// 2-t3
		bpiCoseLog20.addFlow(bpiCoseLog20t3, bpiCoseLog20p5);// t3-5
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20t4);// 2-t4
		bpiCoseLog20.addFlow(bpiCoseLog20t4, bpiCoseLog20p5);// t4-5
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20t5);// 2-t5
		bpiCoseLog20.addFlow(bpiCoseLog20t5, bpiCoseLog20p5);// t5-5
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20t6);// 2-t6
		bpiCoseLog20.addFlow(bpiCoseLog20t6, bpiCoseLog20p5);// t6-5
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20t7);// 2-t7
		bpiCoseLog20.addFlow(bpiCoseLog20t7, bpiCoseLog20p5);// t7-5
		bpiCoseLog20.addFlow(bpiCoseLog20p2, bpiCoseLog20tau1);// 2-tau1
		bpiCoseLog20.addFlow(bpiCoseLog20tau1, bpiCoseLog20p3);// tau1-3
		bpiCoseLog20.addFlow(bpiCoseLog20p3, bpiCoseLog20tau2);// 3-tau2
		bpiCoseLog20.addFlow(bpiCoseLog20tau2, bpiCoseLog20p4);// tau2-4
		bpiCoseLog20.addFlow(bpiCoseLog20p4, bpiCoseLog20tau3);// 4-tau3
		bpiCoseLog20.addFlow(bpiCoseLog20tau3, bpiCoseLog20p5);// 2-t7
		// inner loops
		bpiCoseLog20.addFlow(bpiCoseLog20p4, bpiCoseLog20t8);// 4-t8
		bpiCoseLog20.addFlow(bpiCoseLog20t8, bpiCoseLog20p3);// t8-3
		bpiCoseLog20.addFlow(bpiCoseLog20p4, bpiCoseLog20t9);// 4-t9
		bpiCoseLog20.addFlow(bpiCoseLog20t9, bpiCoseLog20p3);// t9-3
		bpiCoseLog20.addFlow(bpiCoseLog20p4, bpiCoseLog20t10);// 4-t10
		bpiCoseLog20.addFlow(bpiCoseLog20t10, bpiCoseLog20p3);// t10-3
		bpiCoseLog20.addFlow(bpiCoseLog20p4, bpiCoseLog20t11);// 4-t11
		bpiCoseLog20.addFlow(bpiCoseLog20t11, bpiCoseLog20p3);// t11-3
		bpiCoseLog20.addFlow(bpiCoseLog20p4, bpiCoseLog20t12);// 4-t12
		bpiCoseLog20.addFlow(bpiCoseLog20t12, bpiCoseLog20p3);// t12-3
		// outer loop
		bpiCoseLog20.addFlow(bpiCoseLog20p5, bpiCoseLog20tau4);// 5-tau4
		bpiCoseLog20.addFlow(bpiCoseLog20tau4, bpiCoseLog20p2);// tau4-2
		/////
		bpiCoseLog20.addFlow(bpiCoseLog20p5, bpiCoseLog20tau5);// 5-tau5
		bpiCoseLog20.addFlow(bpiCoseLog20tau5, bpiCoseLog20p6);// tau5-6
		bpiCoseLog20.addFlow(bpiCoseLog20p6, bpiCoseLog20tau6);// 6-tau6
		bpiCoseLog20.addFlow(bpiCoseLog20tau6, bpiCoseLog20p12);// tau6-12
		bpiCoseLog20.addFlow(bpiCoseLog20p6, bpiCoseLog20t13);// 6-t13
		bpiCoseLog20.addFlow(bpiCoseLog20t13, bpiCoseLog20p7);// t13-7
		bpiCoseLog20.addFlow(bpiCoseLog20p7, bpiCoseLog20t14);// 7-t14
		bpiCoseLog20.addFlow(bpiCoseLog20t14, bpiCoseLog20p12);// t14-12
		bpiCoseLog20.addFlow(bpiCoseLog20p6, bpiCoseLog20t15);// 6-t15
		bpiCoseLog20.addFlow(bpiCoseLog20t15, bpiCoseLog20p8);// t15-8
		// outer loop
		bpiCoseLog20.addFlow(bpiCoseLog20p8, bpiCoseLog20t16);// 8-t16
		bpiCoseLog20.addFlow(bpiCoseLog20t16, bpiCoseLog20p9);// t16-9
		bpiCoseLog20.addFlow(bpiCoseLog20p9, bpiCoseLog20t17);// 9-t17
		bpiCoseLog20.addFlow(bpiCoseLog20t17, bpiCoseLog20p8);// t17-8
		//////
		bpiCoseLog20.addFlow(bpiCoseLog20p9, bpiCoseLog20tau7);// 9-tau7
		bpiCoseLog20.addFlow(bpiCoseLog20tau7, bpiCoseLog20p10);// tau7-10
		bpiCoseLog20.addFlow(bpiCoseLog20p10, bpiCoseLog20t18);// 10-t18
		bpiCoseLog20.addFlow(bpiCoseLog20t18, bpiCoseLog20p11);// t18-11
		bpiCoseLog20.addFlow(bpiCoseLog20p11, bpiCoseLog20t19);// 11-t19
		bpiCoseLog20.addFlow(bpiCoseLog20t19, bpiCoseLog20p12);// t19-12
		bpiCoseLog20.addFlow(bpiCoseLog20p12, bpiCoseLog20tau8);// 12-tau8
		bpiCoseLog20.addFlow(bpiCoseLog20tau8, bpiCoseLog20p13);// tau8-13
		bpiCoseLog20.addFlow(bpiCoseLog20p13, bpiCoseLog20t20);// 13-t20
		bpiCoseLog20.addFlow(bpiCoseLog20t20, bpiCoseLog20p14);// t20-14
		bpiCoseLog20.addFlow(bpiCoseLog20p14, bpiCoseLog20tau9);// 14-tau9
		bpiCoseLog20.addFlow(bpiCoseLog20tau8, bpiCoseLog20p15);// tau8-15
		bpiCoseLog20.addFlow(bpiCoseLog20p15, bpiCoseLog20t21);// 15-t21
		bpiCoseLog20.addFlow(bpiCoseLog20t21, bpiCoseLog20p22);// t21-22
		bpiCoseLog20.addFlow(bpiCoseLog20p22, bpiCoseLog20tau9);// 22-tau9
		bpiCoseLog20.addFlow(bpiCoseLog20tau9, bpiCoseLog20p16);// tau9-16
		bpiCoseLog20.addFlow(bpiCoseLog20p16, bpiCoseLog20t22);// 16-t22
		bpiCoseLog20.addFlow(bpiCoseLog20t22, bpiCoseLog20p17);// t22-17
		bpiCoseLog20.addFlow(bpiCoseLog20p16, bpiCoseLog20tau10);// 16-tau10
		bpiCoseLog20.addFlow(bpiCoseLog20tau10, bpiCoseLog20p17);// tau10-17
		bpiCoseLog20.addFlow(bpiCoseLog20p17, bpiCoseLog20t23);// 17-t23
		bpiCoseLog20.addFlow(bpiCoseLog20t23, bpiCoseLog20p18);// t23-18
		bpiCoseLog20.addFlow(bpiCoseLog20p17, bpiCoseLog20tau11);// 17-tau11
		bpiCoseLog20.addFlow(bpiCoseLog20tau11, bpiCoseLog20p18);// tau11-18
		bpiCoseLog20.addFlow(bpiCoseLog20p18, bpiCoseLog20t24);// 18-t24
		bpiCoseLog20.addFlow(bpiCoseLog20t24, bpiCoseLog20p19);// t24-19
		bpiCoseLog20.addFlow(bpiCoseLog20p19, bpiCoseLog20t25);// 19-t25
		bpiCoseLog20.addFlow(bpiCoseLog20t25, bpiCoseLog20p20);// t25-20
		bpiCoseLog20.addFlow(bpiCoseLog20p20, bpiCoseLog20tau12);// 20-tau12
		bpiCoseLog20.addFlow(bpiCoseLog20p20, bpiCoseLog20t26);// 20-t26
		bpiCoseLog20.addFlow(bpiCoseLog20t26, bpiCoseLog20p21);// t26-21
		bpiCoseLog20.addFlow(bpiCoseLog20tau12, bpiCoseLog20p21);// 21-tau12

		// ******************************************************\\

		PetriNet bpi2012 = new PetriNet();
		Transition bpi2012t1 = new Transition("A_SUBMITTED");
		Transition bpi2012t2 = new Transition("A_PARTLYSUBMITTED");
		Transition bpi2012t3 = new Transition("A_DECLINED");
		Transition bpi2012t4 = new Transition("W_Beoordelen fraude");
		Transition bpi2012t5 = new Transition("A_PREACCEPTED");
		Transition bpi2012t6 = new Transition("W_Afhandelen leads");
		Transition bpi2012t7 = new Transition("O_DECLINED");
		Transition bpi2012t8 = new Transition("W_Completeren aanvraag");
		Transition bpi2012t9 = new Transition("W_Wijzigen contractgegevens");
		Transition bpi2012t10 = new Transition("W_Nabellen offertes");
		Transition bpi2012t11 = new Transition("W_Valideren aanvraag");
		Transition bpi2012t12 = new Transition("A_CANCELLED");
		Transition bpi2012t13 = new Transition("A_ACCEPTED");
		Transition bpi2012t14 = new Transition("W_Nabellen incomplete dossiers");
		Transition bpi2012t15 = new Transition("O_SENT_BACK");
		Transition bpi2012t16 = new Transition("A_ACTIVATED");
		Transition bpi2012t17 = new Transition("A_APPROVED");
		Transition bpi2012t18 = new Transition("O_ACCEPTED");
		Transition bpi2012t19 = new Transition("A_REGISTERED");
		Transition bpi2012t20 = new Transition("O_SELECTED");
		Transition bpi2012t21 = new Transition("O_CANCELLED");
		Transition bpi2012t22 = new Transition("A_FINALIZED");
		Transition bpi2012t23 = new Transition("O_CREATED");
		Transition bpi2012t24 = new Transition("O_SENT");
		Transition bpi2012tau1 = new Transition("tau1");
		Transition bpi2012tau2 = new Transition("tau2");
		Transition bpi2012tau3 = new Transition("tau3");
		Transition bpi2012tau4 = new Transition("tau4");
		Transition bpi2012tau5 = new Transition("tau5");
		Transition bpi2012tau6 = new Transition("tau6");
		Transition bpi2012tau7 = new Transition("tau7");
		Transition bpi2012tau8 = new Transition("tau8");
		Transition bpi2012tau9 = new Transition("tau9");

		Place bpi2012p1 = new Place("1");
		Place bpi2012p2 = new Place("2");
		Place bpi2012p3 = new Place("3");
		Place bpi2012p4 = new Place("4");
		Place bpi2012p5 = new Place("5");
		Place bpi2012p6 = new Place("6");
		Place bpi2012p7 = new Place("7");
		Place bpi2012p8 = new Place("8");
		Place bpi2012p9 = new Place("9");
		Place bpi2012p10 = new Place("10");
		Place bpi2012p11 = new Place("11");
		Place bpi2012p12 = new Place("12");
		Place bpi2012p13 = new Place("13");
		Place bpi2012p14 = new Place("14");
		Place bpi2012p15 = new Place("15");
		Place bpi2012p16 = new Place("16");
		Place bpi2012p17 = new Place("17");
		Place bpi2012p18 = new Place("18");
		Place bpi2012p19 = new Place("19");
		Place bpi2012p20 = new Place("20");
		Place bpi2012p21 = new Place("21");
		Place bpi2012p22 = new Place("22");

		bpi2012.addFlow(bpi2012p1, bpi2012t1);// 1-t1
		bpi2012.addFlow(bpi2012t1, bpi2012p2);// t1-2
		bpi2012.addFlow(bpi2012p2, bpi2012t2);// 2-t2
		bpi2012.addFlow(bpi2012t2, bpi2012p3);// t2-3
		bpi2012.addFlow(bpi2012p3, bpi2012t3);// 3-t3
		bpi2012.addFlow(bpi2012t3, bpi2012p4);// t3-4
		bpi2012.addFlow(bpi2012p3, bpi2012t4);// 3-t4
		bpi2012.addFlow(bpi2012t4, bpi2012p4);// t4-4
		bpi2012.addFlow(bpi2012p3, bpi2012t5);// 3-t5
		bpi2012.addFlow(bpi2012t5, bpi2012p4);// t5-4
		bpi2012.addFlow(bpi2012p3, bpi2012t6);// 3-t6
		bpi2012.addFlow(bpi2012t6, bpi2012p4);// t6-4
		bpi2012.addFlow(bpi2012p4, bpi2012t7);// 4-t7
		bpi2012.addFlow(bpi2012t7, bpi2012p20);// t7-20
		bpi2012.addFlow(bpi2012p4, bpi2012t8);// 4-t8
		bpi2012.addFlow(bpi2012t8, bpi2012p20);// t8-20
		bpi2012.addFlow(bpi2012p4, bpi2012t9);// 4-t9
		bpi2012.addFlow(bpi2012t9, bpi2012p20);// t9-20
		bpi2012.addFlow(bpi2012p4, bpi2012t10);// 4-t10
		bpi2012.addFlow(bpi2012t10, bpi2012p20);// t10-20
		bpi2012.addFlow(bpi2012p4, bpi2012t11);// 4-t11
		bpi2012.addFlow(bpi2012t11, bpi2012p20);// t11-20
		bpi2012.addFlow(bpi2012p4, bpi2012t14);// 4-t14
		bpi2012.addFlow(bpi2012t14, bpi2012p20);// t14-20
		bpi2012.addFlow(bpi2012p4, bpi2012t15);// 4-t15
		bpi2012.addFlow(bpi2012t15, bpi2012p20);// t15-20
		// 1st And
		bpi2012.addFlow(bpi2012p4, bpi2012tau1);// 4-tau1
		bpi2012.addFlow(bpi2012tau1, bpi2012p5);// tau1-5
		bpi2012.addFlow(bpi2012p5, bpi2012t16);// 5-t16
		bpi2012.addFlow(bpi2012t16, bpi2012p9);// t16-9
		bpi2012.addFlow(bpi2012p9, bpi2012tau3);// 9-tau3
		bpi2012.addFlow(bpi2012tau1, bpi2012p6);// tau1-6
		bpi2012.addFlow(bpi2012p6, bpi2012t17);// 6-t17
		bpi2012.addFlow(bpi2012t17, bpi2012p10);// t17-10
		bpi2012.addFlow(bpi2012p10, bpi2012tau3);// 10-tau3
		bpi2012.addFlow(bpi2012tau1, bpi2012p7);// tau1-7
		bpi2012.addFlow(bpi2012p7, bpi2012t18);// 7-t18
		bpi2012.addFlow(bpi2012t18, bpi2012p11);// t18-11
		bpi2012.addFlow(bpi2012p11, bpi2012tau3);// 11-tau3
		bpi2012.addFlow(bpi2012tau1, bpi2012p8);// tau1-8
		bpi2012.addFlow(bpi2012p8, bpi2012t19);// 8-t19
		bpi2012.addFlow(bpi2012t19, bpi2012p12);// t19-12
		bpi2012.addFlow(bpi2012p12, bpi2012tau3);// 12-tau3
		bpi2012.addFlow(bpi2012tau3, bpi2012p20);// tau3-20

		bpi2012.addFlow(bpi2012p4, bpi2012t12);// 4-t12
		bpi2012.addFlow(bpi2012t12, bpi2012p13);// t12-13
		bpi2012.addFlow(bpi2012p4, bpi2012t13);// 4-t13
		bpi2012.addFlow(bpi2012t13, bpi2012p13);// t13-13
		bpi2012.addFlow(bpi2012p4, bpi2012tau2);// 4-tau2
		bpi2012.addFlow(bpi2012tau2, bpi2012p13);// tau2-13

		bpi2012.addFlow(bpi2012p13, bpi2012tau4);// 13-tau4
		bpi2012.addFlow(bpi2012tau4, bpi2012p14);// tau4-14
		bpi2012.addFlow(bpi2012p14, bpi2012t20);// 14-t20
		bpi2012.addFlow(bpi2012t20, bpi2012p15);// t20-15
		bpi2012.addFlow(bpi2012p15, bpi2012tau5);// 15-tau5
		bpi2012.addFlow(bpi2012tau4, bpi2012p16);// tau4-16
		bpi2012.addFlow(bpi2012p16, bpi2012t21);// 16-t21
		bpi2012.addFlow(bpi2012t21, bpi2012p17);// t21-17
		bpi2012.addFlow(bpi2012p17, bpi2012tau5);// 17-tau5
		bpi2012.addFlow(bpi2012p16, bpi2012t22);// 16-t22
		bpi2012.addFlow(bpi2012t22, bpi2012p17);// t22-17
		bpi2012.addFlow(bpi2012tau5, bpi2012p18);// tau5-18
		bpi2012.addFlow(bpi2012p18, bpi2012t23);// 18-t23
		bpi2012.addFlow(bpi2012t23, bpi2012p19);// t23-19
		bpi2012.addFlow(bpi2012p19, bpi2012t24);// 19-t24
		bpi2012.addFlow(bpi2012t24, bpi2012p20);// t24-20
		// inner loop
		bpi2012.addFlow(bpi2012p20, bpi2012tau6);// 20-tau6
		bpi2012.addFlow(bpi2012tau6, bpi2012p4);// tau6-4

		bpi2012.addFlow(bpi2012p20, bpi2012tau7);// 20-tau7
		bpi2012.addFlow(bpi2012tau7, bpi2012p21);// tau7-21
		bpi2012.addFlow(bpi2012p21, bpi2012tau8);// 21-tau8
		// outer loop
		bpi2012.addFlow(bpi2012tau8, bpi2012p3);// tau8-3

		bpi2012.addFlow(bpi2012p21, bpi2012tau9);// 21-tau9
		bpi2012.addFlow(bpi2012tau9, bpi2012p22);// tau9-22

		// **************************************************************\\
		// BPI2013
		PetriNet bpi2013C = new PetriNet();
		Transition bpi2013Ct1 = new Transition("Unmatched");
		Transition bpi2013Ct2 = new Transition("Accepted");
		Transition bpi2013Ct3 = new Transition("Queued");
		Transition bpi2013Ct4 = new Transition("Completed");
		Transition bpi2013Ctau1 = new Transition("tau1");
		Transition bpi2013Ctau2 = new Transition("tau2");

		Place bpi2013Cp1 = new Place("p1");
		Place bpi2013Cp2 = new Place("p2");
		Place bpi2013Cp3 = new Place("p3");
		Place bpi2013Cp4 = new Place("p4");
		Place bpi2013Cp5 = new Place("p5");

		bpi2013C.addFlow(bpi2013Cp1, bpi2013Ct1);// 1-t1
		bpi2013C.addFlow(bpi2013Cp1, bpi2013Ctau1);// 1-tau1
		bpi2013C.addFlow(bpi2013Ct1, bpi2013Cp2);// t1-2
		bpi2013C.addFlow(bpi2013Ctau1, bpi2013Cp2);// t1-2
		bpi2013C.addFlow(bpi2013Cp2, bpi2013Ct2);// 2-t2
		bpi2013C.addFlow(bpi2013Ct2, bpi2013Cp3);// t2-3
		bpi2013C.addFlow(bpi2013Cp3, bpi2013Ct3);// 3-t3
		bpi2013C.addFlow(bpi2013Ct3, bpi2013Cp2);// t3-2
		bpi2013C.addFlow(bpi2013Cp3, bpi2013Ctau2);// 3-tau2
		bpi2013C.addFlow(bpi2013Ctau2, bpi2013Cp4);// tau2-4
		bpi2013C.addFlow(bpi2013Cp4, bpi2013Ct4);// 4-t4
		bpi2013C.addFlow(bpi2013Ct4, bpi2013Cp5);// t4-5

		PetriNet paperEx = new PetriNet();
		Transition paperExT1 = new Transition("A");
		Transition paperExT2 = new Transition("B");
		Transition paperExT3 = new Transition("C");
		Transition paperExT4 = new Transition("D");
		Transition paperExT5 = new Transition("E");
		Transition paperExT6 = new Transition("F");
		Transition paperExT7 = new Transition("G");
		Transition paperExT8 = new Transition("H");
		Transition paperExTau1 = new Transition("tau1");
		Transition paperExTau2 = new Transition("tau2");
		Transition paperExTau3 = new Transition("tau3");
		Place paperExP1 = new Place("1");
		Place paperExP2 = new Place("2");
		Place paperExP3 = new Place("3");
		Place paperExP4 = new Place("4");
		Place paperExP5 = new Place("5");
		Place paperExP6 = new Place("6");
		Place paperExP7 = new Place("7");
		Place paperExP8 = new Place("8");
		Place paperExP9 = new Place("9");
		Place paperExP10 = new Place("10");
		Place paperExP11 = new Place("11");

		paperEx.addFlow(paperExP1, paperExT1);// 1-A
		paperEx.addFlow(paperExT1, paperExP2);// A-2
		paperEx.addFlow(paperExP2, paperExT2);// 2-B
		paperEx.addFlow(paperExT2, paperExP3);// B-3
		//XOR
		paperEx.addFlow(paperExP3, paperExT3);//3-C
		paperEx.addFlow(paperExT3, paperExP4);//C-4
		paperEx.addFlow(paperExP4, paperExT4);//4-D
		paperEx.addFlow(paperExT4, paperExP5);//D-5
		paperEx.addFlow(paperExP3, paperExT5);//3-E
		paperEx.addFlow(paperExT5, paperExP5);//E-5
		
		paperEx.addFlow(paperExP5, paperExTau1);//5-tau1
		paperEx.addFlow(paperExTau1, paperExP3);//tau3-3
		
		paperEx.addFlow(paperExP5, paperExT6);//5-F
		paperEx.addFlow(paperExT6, paperExP6);//F-6
		paperEx.addFlow(paperExP6, paperExT8);//6-H
		paperEx.addFlow(paperExT8, paperExP2);//H-2
		paperEx.addFlow(paperExP6, paperExT7);//10-G
		paperEx.addFlow(paperExT7, paperExP7);//G-11
		
		
		// ADD
//		paperEx.addFlow(paperExP3, paperExTau1);// 3-tau1
//		paperEx.addFlow(paperExTau1, paperExP4);//tau1-4
//		paperEx.addFlow(paperExP4, paperExT3);//4-C
//		paperEx.addFlow(paperExT3, paperExP5);//C-5
//		paperEx.addFlow(paperExP5, paperExT4);//5-D
//		paperEx.addFlow(paperExT4, paperExP6);//D-6
//		paperEx.addFlow(paperExP6, paperExTau2);//6-tau2
//		paperEx.addFlow(paperExTau1, paperExP7);//tau1-7
//		paperEx.addFlow(paperExP7, paperExT5);//7-E
//		paperEx.addFlow(paperExT5, paperExP8);//E-8
//		paperEx.addFlow(paperExP8, paperExTau2);//8-tau2
//		paperEx.addFlow(paperExTau2, paperExP9);//tau2-9
//		paperEx.addFlow(paperExP9, paperExTau3);//9-tau3
//		paperEx.addFlow(paperExTau3, paperExP3);//tau3-3
//		paperEx.addFlow(paperExP9, paperExT6);//9-F
//		paperEx.addFlow(paperExT6, paperExP10);//F-10
//		paperEx.addFlow(paperExP10, paperExT8);//10-H
//		paperEx.addFlow(paperExT8, paperExP2);//H-2
//		paperEx.addFlow(paperExP10, paperExT7);//10-G
//		paperEx.addFlow(paperExT7, paperExP11);//G-11

		RelationMatrix rm = new RelationMatrix(bpiCoseLog20);
		// HashSet<HashSet<String>> s = rm.predcessors(bpi2012t24);
		//
		// System.out.println("Predecesssors");
		// System.out.print(s);
		// System.out.println();
		// System.out.println();
		// Collection<Node> pre = rm.getModelPredecessors(bpiCoseLog20t26, new
		// HashSet<>(), new HashSet<>(),
		// new HashSet<>(), true);
		// for (Node p : pre) {
		// System.out.println(p.getLabel());
		// }
//		System.out.println(rm.getEndActivitiesName());
//		System.out.println();
//		System.out.println("------------------------------------------------");
//		System.out.println("RM");
//		System.out.println("------------------------------------------------");
//		rm.printRM();
//		System.out.println();
//		System.out.println("------------------------------------------------");
//		System.out.println("cyclic BP");
//		System.out.println("------------------------------------------------");
//		rm.printBP(rm.getBpOrginial());
//		System.out.println();
//		System.out.println("------------------------------------------------");
//		System.out.println("Acyclic BP");
//		System.out.println("------------------------------------------------");
//		rm.printBP(rm.getBpLoopFree());
		// System.out.println();
		// System.out.println("------------------------------------------------");
		// rm.printBP(rm.getBpOrginial());
		// System.out.println();
		// System.out.println("------------------------------------------------");
		// rm.printBP(rm.getBpLoopFree());
//		System.out.println();
//		System.out.println("------------------------------------------------");
//		ArrayList<Loop> loops = rm.getLoops();
//		for (Loop l : loops) {
//			l.print();
//			// System.out.println();
//			// System.out.println("------------------------------------------------");
//			System.out.println();
//			System.out.println("------------------------------------------------");
//		}
//
		GatewayServer gatewayServer = new GatewayServer(rm);
		// // new BehaviouralProfileEntryPoint());
		try {
			gatewayServer.start();
			System.out.println("open gate");
		} catch (Exception ex) {
			System.out.println("This channel  must be closed from python");
			gatewayServer.shutdown();
			// gatewayServer.start();
		}
	}

}
