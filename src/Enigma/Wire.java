package Enigma;

public class Wire {
	
	private char plugA;
	private char plugB;
	
	public Wire(char a, char b) {
		plugA = a;
		plugB = b;
	}
	
	public char[] getPlugs() {
		char[] plugs = {plugA,plugB};
		return plugs;
	}
	
	public char getA() {
		return plugA;
	}
	
	public char getB() {
		return plugB;
	}
	
	public String toString() {
		return "wire connecting Plug " + plugA + " and Plug " + plugB;
	}
}
