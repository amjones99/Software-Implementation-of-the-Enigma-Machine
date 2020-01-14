package Enigma;

public class Rotor {
	private char curPos; //window letter
	private final char maxPos = 'Z';
	private char carryNotch;
	private char[][] innerCon;
	private int ringSet;
	private char[][] innerCon1 = {{'A','E'},{'B','K'},{'C','M'},{'D','F'},{'E','L'},{'F','G'},{'G','D'},{'H','Q'},{'I','V'},{'J','Z'},{'K','N'},{'L','T'},{'M','O'},{'N','W'},{'O','Y'},{'P','H'},{'Q','X'},{'R','U'},{'S','S'},{'T','P'},{'U','A'},{'V','I'},{'W','B'},{'X','R'},{'Y','C'},{'Z','J'}};
	private char[][] innerCon2 = {{'A','A'},{'B','J'},{'C','D'},{'D','K'},{'E','S'},{'F','I'},{'G','R'},{'H','U'},{'I','X'},{'J','B'},{'K','L'},{'L','H'},{'M','W'},{'N','T'},{'O','M'},{'P','C'},{'Q','Q'},{'R','G'},{'S','Z'},{'T','N'},{'U','P'},{'V','Y'},{'W','F'},{'X','V'},{'Y','O'},{'Z','E'}};
	private char[][] innerCon3 = {{'A','B'},{'B','D'},{'C','F'},{'D','H'},{'E','J'},{'F','L'},{'G','C'},{'H','P'},{'I','R'},{'J','T'},{'K','X'},{'L','V'},{'M','Z'},{'N','N'},{'O','Y'},{'P','E'},{'Q','I'},{'R','W'},{'S','G'},{'T','A'},{'U','K'},{'V','M'},{'W','U'},{'X','S'},{'Y','Q'},{'Z','O'}};
	private char[][] innerCon4 = {{'A','E'},{'B','S'},{'C','O'},{'D','V'},{'E','P'},{'F','Z'},{'G','J'},{'H','A'},{'I','Y'},{'J','Q'},{'K','U'},{'L','I'},{'M','R'},{'N','H'},{'O','X'},{'P','L'},{'Q','N'},{'R','F'},{'S','T'},{'T','G'},{'U','K'},{'V','D'},{'W','C'},{'X','M'},{'Y','W'},{'Z','B'}};
	private char[][] innerCon5 = {{'A','V'},{'B','Z'},{'C','B'},{'D','R'},{'E','G'},{'F','I'},{'G','T'},{'H','Y'},{'I','U'},{'J','P'},{'K','S'},{'L','D'},{'M','N'},{'N','H'},{'O','L'},{'P','X'},{'Q','A'},{'R','W'},{'S','M'},{'T','J'},{'U','Q'},{'V','O'},{'W','F'},{'X','E'},{'Y','C'},{'Z','K'}};
	
	public Rotor(String rotorSel, int offset,char startPos) {
		int posNum = (int)(startPos - 64);
		switch (rotorSel) {
			case "I":	innerCon = innerCon1;
						carryNotch = 'Q';
						break;
			case "II":	innerCon = innerCon2;
						carryNotch = 'E';
						break;
			case "III":	innerCon = innerCon3;
						carryNotch = 'V';
						break;
			case "IV":	innerCon = innerCon4;
						carryNotch = 'J';
						break;
			case "V":	innerCon = innerCon5;
						carryNotch = 'Z';
						break;
		}
		ringSet = offset;
		for(int j = 1; j < offset; j++) {
			this.ringSetting();
		}
		for (int i = 1; i < posNum; i++) {
			this.rotateRotor(false);
		}
		curPos = startPos;
	}
	
	public char passRotFor(char inp) {
		int i = (int)(inp - 65);
//		System.out.println(i);
//		System.out.println(innerCon[i][1]);
		int k = 0;
		for (int j = 0; j < 26; j ++) {
			if (innerCon[j][0] == innerCon[i][1]) {
//				System.out.println(j);
				k = j;
				break;
			}
		}
		k = (k + (ringSet-1)) % 26;
		//System.out.println("\n" + (char)(k + 65));
		return (char)(k + 65);
	}
	
	public char passRotBack(int inp) {
		int i = (int)(inp - 65);
		i = (i - (ringSet-1) + 26) % 26;
		int k = 0;
		for (int j = 0; j < 26; j ++) {
			if (innerCon[j][1] == innerCon[i][0]) {
				k = j;
				break;
			}
		}
		//System.out.println("\n" + (char)(k + 65));
		return (char)(k + 65);
	}
	
	public boolean rotateRotor(boolean notch) {
		char[] c = innerCon[0];
		for (int i = 0; i < 25; i++) {
			innerCon[i] = innerCon[i+1];
		}
		innerCon[25] = c;
		
		curPos = innerCon[0][0];
		
		if (curPos == carryNotch && notch) {
			//System.out.println("Notch hit, rotate next rotor");
			return true;
		}
		return false;
	}
	
	public void ringSetting() {
		char c = innerCon[25][1];
		for (int i = 25; i > 0; i--) {
			innerCon[i][1] = innerCon[i-1][1];
		}
		innerCon[0][1] = c;
	}
	
	public char getCurPos() {
		return curPos;
	}
	
	public char[][] getCon() {
		return innerCon;
	}
	
	public char getCarryNotch() {
		return carryNotch;
	}
	
	public void printRotor() {
		System.out.println("The current position for this rotor is: " + curPos);
		System.out.println("The current wiring connection setup is:");
		for (int i = 0; i < 26; i++) {
			System.out.print("[" + innerCon[i][0] +", " + innerCon[i][1] +"]");
		}
		System.out.println("\nThe carry notch for this rotor is: " + carryNotch);
	}
}
