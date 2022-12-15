
public class List {

	private static final String START_LABEL = "|START|";
	public Element head;

	// Aufgabe 1
	public void addElement(Element newElement) {

	}

	// Aufgabe 2
	public int getSize() {
		return 0;
	}

	// Aufgabe 3
	public void increase() {

	}

	// Aufgabe 4
	public void deleteElement() {

	}

	// Methode, die zur Darstellung der Liste dient.
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder(START_LABEL);

		Element pointer = head;
		while (pointer != null) {
			str.append("---> ");
			str.append(pointer.getValue());
			pointer = pointer.getNext();
		}

		str.append("---> null");

		return str.toString();
	}
}
