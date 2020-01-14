package Enigma;

import java.util.Scanner;
import java.util.Arrays;

/**
 * 
 * Enigma.java
 * 
 * Main class of the simulation
 * 
 * @author Adam Jones
 *
 */

public class Enigma {

	public static void main(String[] args) {
		//inits
		char e;
		Scanner keyboard = new Scanner(System.in);
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String[] rotorOptions = {"I","II","III","IV","V"};
		String lChoice = "";
		String cChoice = "";
		String rChoice = "";
		char lStart = '-';
		char cStart = '-';
		char rStart = '-';
		
		
		//Welcome
		System.out.println("Welcome to a Simulation of the Enigma Machine\n");
		wait(1000);

		//Choose Rotors
		while (true) {
			System.out.println("Please enter the rotor you would like to use for the left-most rotor: (I-V)");
			lChoice = keyboard.nextLine().toUpperCase();
			if (correctStrInput(rotorOptions,lChoice)) {
				rotorOptions = updateRotorChoice(rotorOptions,lChoice);
				break;
			} else {
				System.out.println("Invalid input, must be a valid free rotor (I,II,III,IV,V)");
			}
		}
		while (true) {
			System.out.println("Please enter the rotor you would like to use for the central rotor: (I-V)");
			cChoice = keyboard.nextLine().toUpperCase();
			if (correctStrInput(rotorOptions,cChoice)) {
				rotorOptions = updateRotorChoice(rotorOptions,cChoice);
				break;
			} else {
				System.out.println("Invalid input, must be a valid free rotor " + rOptToString(rotorOptions));
			}
		}
		while (true) {
			System.out.println("Please enter the rotor you would like to use for the right-most rotor: (I-V)");
			rChoice = keyboard.nextLine().toUpperCase();
			if (correctStrInput(rotorOptions,rChoice)) {
				break;
			} else {
				System.out.println("Invalid input, must be a valid free rotor " + rOptToString(rotorOptions));
			}
		}
		
		int ringSettingL = numRangeInput("Please enter the inner-ring setting for the left-most rotor: (1-26)", keyboard, 1, 26);
		int ringSettingC = numRangeInput("Please enter the inner-ring setting for the central rotor: (1-26)", keyboard, 1, 26);
		int ringSettingR = numRangeInput("Please enter the inner-ring setting for the right-most rotor: (1-26)", keyboard, 1, 26);
		
		//Choose starting position for rotors
		//maybe edit to only accept single character strings on input
		//sort if nothing entered
		while (true) {
			System.out.println("Please enter the starting position of the left-most rotor: (A-Z)");
			String s = keyboard.nextLine();
			if (s.length() == 1) {
				lStart = s.toUpperCase().charAt(0);
				if (correctCharInput(alphabet,lStart)) {
					break;
				} else {
					System.out.println("Invalid input, must be a letter of the alphabet");
				}
			} else {
				System.out.println("Invalid input, must be a letter of the alphabet");
			}
		}
		while (true) {
			System.out.println("Please enter the starting position of the central rotor: (A-Z)");
			String s = keyboard.nextLine();
			if (s.length() == 1) {
				cStart = s.toUpperCase().charAt(0);
				if (correctCharInput(alphabet,cStart)) {
					break;
				} else {
					System.out.println("Invalid input, must be a letter of the alphabet");
				}
			} else {
				System.out.println("Invalid input, must be a letter of the alphabet");
			}
		}
		while (true) {
			System.out.println("Please enter the starting position of the right-most rotor: (A-Z)");
			String s = keyboard.nextLine();
			if (s.length() == 1) {
				rStart = s.toUpperCase().charAt(0);
				if (correctCharInput(alphabet,rStart)) {
					break;
				} else {
					System.out.println("Invalid input, must be a letter of the alphabet");
				}
			} else {
				System.out.println("Invalid input, must be a letter of the alphabet");
			}
		}
		
		//Choose the number of connections
		int numWires = numRangeInput("Please enter the number of connections you would like on the plugboard", keyboard, 0, 13);
		
		//Initiate new objects
		Reflector refl = new Reflector();
		Rotor rRotor = new Rotor(rChoice, ringSettingR, rStart);
		Rotor cRotor = new Rotor(cChoice, ringSettingC, cStart);
		Rotor lRotor = new Rotor(lChoice, ringSettingL, lStart);
		Plugboard pb = new Plugboard(numWires);
		
		//Enter Connections
		String s = "";
		char first = '-';
		char second = '-';
		for (int con = 0; con < numWires; con++) {
			while (true) {
				String free = pb.getFreePlugs();
				System.out.println(free);
				System.out.println("Please create a connection using the available plugs shown above");
				while (true) {
					System.out.println("Enter the first plug: ");
					s = keyboard.nextLine().toUpperCase();
					first = s.charAt(0);
					if (s.length() == 1 && correctCharInput(free,first)) {
						break;
					}
					System.out.println("Please enter a single letter that is currently free");
				}
				while (true) {
					System.out.println("Enter the second plug: ");
					s = keyboard.nextLine().toUpperCase();
					second = s.charAt(0);
					if (s.length() == 1 && first!=second && correctCharInput(free,second)) {
						break;
					}
					System.out.println("Please enter a single letter that is currently free and not the same as the first letter entered for the connection");
				}
				Wire w = new Wire(first, second);
				pb.addConnection(w);
				System.out.println("Connection " + (con+1) + " added");
				break;
			}
		}		
		
		//Return Set-Up
		System.out.println("Rotor Selections (left to right):");
		System.out.println(lChoice + " " + cChoice + " " + rChoice);
		System.out.println("Inner-Ring settings (left to right):");
		System.out.println(ringSettingL + " " + ringSettingC + " " + ringSettingR);
		System.out.println("Starting position of Rotors (left to right)");
		System.out.println(lStart + " " + cStart + " " + rStart);
		//return connections
		System.out.println("Plugboard Connections: ");
		String[] conns = pb.getConnections();
		for (String cs : conns) {
			System.out.print(cs);
		}
		lRotor.printRotor();
		//Enter the message to encrypt
		System.out.println("Please enter the message you would like to encrypt:");
		String msg = keyboard.nextLine().toUpperCase();
		
		wait(1000);
		msg = msg.replaceAll("[^a-zA-Z]", "");
		for (int i = 0; i < msg.length(); i++) {
			if (i % 5 == 0 && i != 0) {
				System.out.print(" ");
			}
			e = encrypt(pb,refl, lRotor, cRotor, rRotor, msg.charAt(i));
			System.out.print(e);	
		}
		System.out.println("");
	}
	
	public static void rotate(Rotor l, Rotor c,Rotor r) {
		if (r.rotateRotor(true)) {
			if (c.rotateRotor(true)) {
				l.rotateRotor(true);
			}
		}
	}
	
	public static void wait(int ms) {
		try {
		    Thread.sleep(ms);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public static char encrypt(Plugboard pb,Reflector refl, Rotor l, Rotor c,Rotor r,char ch) {
		//System.out.println(e);
		char throughBoard = pb.passBoard(ch);	
		//int e = (int)(throughBoard - 64);
		//increment before enciphering
		rotate(l,c,r);
//		l.printRotor();
//		c.printRotor();
//		r.printRotor();
		char eRotor = passRotors(refl,l,c,r,throughBoard);
		return pb.passBoard(eRotor);
	}
	
	public static char passRotors(Reflector refl,Rotor l, Rotor c,Rotor r,char i) {
		char atRef = l.passRotFor(c.passRotFor(r.passRotFor(i)));
		char pastRef = refl.passRef(atRef);
		return r.passRotBack(c.passRotBack(l.passRotBack(pastRef)));
	}
	
	public static boolean correctStrInput(String[] arr, String in) {
		return Arrays.asList(arr).contains(in);
	}
	
	public static boolean correctCharInput(String str, char in) {
		if (str.indexOf(in) == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static int numRangeInput(String msg, Scanner keyboard, int lower, int upper) {
		System.out.println(msg);
		int n = 0;
		try {
			n = keyboard.nextInt();
			keyboard.nextLine(); // consume the \n after nextInt()
		} catch(Exception ex) {
			keyboard.nextLine(); // consume the \n after nextInt()
			System.out.println("Please enter an integer in the range of 0 to 13");
			return numRangeInput(msg, keyboard, lower, upper);
		}
		if (n >= lower && n <= upper) {
			return n;
		} else {
			System.out.println("Please enter an integer in the range of " + lower + " to " + upper);
			return numRangeInput(msg, keyboard, lower, upper);
		}
	}
	public static String[] updateRotorChoice(String[] opt, String v) {
		switch (v) {
			case "I":   opt[0] = "I(Already Used)";
					    break;
			case "II":  opt[1] = "II(Already Used)";
			            break;
			case "III": opt[2] = "III(Already Used)";
			            break;
			case "IV":  opt[3]= "IV(Already Used)";
			            break;
			case "V":   opt[4] = "V(Already Used)";
			            break;
		}
		return opt;
	}
	public static String rOptToString(String[] opt) {
		String str = "(";
		String[] o = {"I","II","III","IV","V"};
		for (String s : opt) {
			if (correctStrInput(o, s)) {
				str = str + s + ",";
			}
		}
		str = str.substring(0,str.length()-1) + ")";
		return str;
	}
}
