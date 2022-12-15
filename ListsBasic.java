
public class ListsBasic {
	
	public static void main (String[] args) {
	List list = new List();
	
	Element element1 = new Element(1);
	Element element2 = new Element(2);
	Element element3 = new Element(3);
	Element element4 = new Element(4);
	Element element5 = new Element(5);
	Element element6 = new Element(6);
	Element element7 = new Element(7);
	Element element8 = new Element(8);
	Element element9 = new Element(9);
	Element element10 = new Element(10);
	
	list.addElement(element1);
	list.addElement(element2);
	list.addElement(element3);
	list.addElement(element4);
	list.addElement(element5);
	list.addElement(element6);
	list.addElement(element7);
	list.addElement(element8);
	list.addElement(element9);
	list.addElement(element10);
	
	System.out.println("---------- Einfügen der Elemente in die Liste ----------");
	System.out.println("Deine Lösung: " + list.toString());
	System.out.println("Richtige Lösung: |START|---> 1---> 2---> 3---> 4---> 5---> 6---> 7---> 8---> 9---> 10---> null");
	System.out.println("");
	
	System.out.println("---------- Zählen der Elemente ----------");
	System.out.println("Deine Lösung: " + list.getSize() + " Elemente");
	System.out.println("Richtige Lösung: 10 Elemente");
	System.out.println("");
	
	list.increase();
	
	System.out.println("---------- Hochzählen der Werte jedes Elementes -----------");
	System.out.println("Deine Lösung: " + list.toString());
	System.out.println("Richtige Lösung: |START|---> 2---> 3---> 4---> 5---> 6---> 7---> 8---> 9---> 10---> 11---> null");
	System.out.println("");
	
	list.deleteElement();
	
	System.out.println("---------- Löschen des ersten Elementes der Liste ----------");
	System.out.println("Deine Lösung: " + list.toString());
	System.out.println("Richtige Lösung: |START|---> 3---> 4---> 5---> 6---> 7---> 8---> 9---> 10---> 11---> null");
	System.out.println("");
	}
}
