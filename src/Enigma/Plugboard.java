package Enigma;

public class Plugboard {
	
	private Wire[] connections;
	private int numberOfConnections;
	private String freePlugs;
	
	public Plugboard(int num) {
		numberOfConnections = num;
		connections = new Wire[num];
		freePlugs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}
	
	public String getFreePlugs() {
		String out = "";
		for (int i = 0; i < freePlugs.length(); i++) {
			if (freePlugs.charAt(i) != '-') {
				out = out + Character.toString(freePlugs.charAt(i));
			}
		}
		return out;
	}
	
	public boolean addConnection(Wire x) {
		for (Wire w : connections) {
			if (w != null) {
				if (w.getA() == x.getA() || w.getA() == x.getB() || w.getB() == x.getA() || w.getB() == x.getB()) {
					return false;
				}
			}
		}
		for (int i = 0; i < numberOfConnections; i++) {
			Wire w = connections[i];
			if (w == null) {
				connections[i] = x;
				freePlugs = freePlugs.replace(x.getA(), '-');
				freePlugs = freePlugs.replace(x.getB(), '-');
				return true;
			}
		}
		return false;
	}
	
	public String[] getConnections() {
		String[] conns = new String[numberOfConnections];
		for (int i = 0; i < numberOfConnections; i++) {
			Wire w = connections[i];
			if (w != null) {
				conns[i] = "There is a " + w.toString() + "\n";
			} else {
				if (conns[i-1].length() != 45) {
					conns[i] = "";
				} else if (numberOfConnections - i != 1) {
					conns[i] = "There are still " + (numberOfConnections-i) + " pairs to be connected\n";
				} else {
					conns[i] = "There is still a connection to be made\n";
				}
			}
			
		}
		return conns;
	}
	
	public char passBoard(char ch) {
		for (Wire w: connections) {
			if (w.getA() == ch) {
				return w.getB();
			} if (w.getB() == ch) {
				return w.getA();
			}
		}
		return ch;
	}
}
