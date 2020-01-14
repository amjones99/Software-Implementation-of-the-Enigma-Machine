package Enigma;

public class Reflector {
	private char[] connections = {'Y','R','U','H','Q','S','L','D','P','X','N','G','O','K','M','I','E','B','F','Z','C','W','V','J','A','T'};
	
	public Reflector() {
	}
	
	public char passRef(char inp) {
		//System.out.println((char)(connections[inp] +64));
		int i = (int)(inp - 65); //A -> 0, B -> 1, etc
		//System.out.println("\n"+ connections[i]);
		return connections[i];
	}

}
