
public class Element {
	
	private Element next;
	private int value;
	
	public Element(int value) {
		this.value = value;
	}
	
	public Element getNext() {
		
		return next;
		
	}
	
	public void setNext(Element next) {
		this.next = next;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void increaseByOne() {
		value++;
	}

}
