package application;

import java.util.ArrayList;

/**
 * This class stores a stack of mementos.
 * @author minoh
 *
 */
public class MementoCaretaker {
	// Where all mementos are saved
	ArrayList<Memento> savedMementos = new ArrayList<Memento>();
	
	
	/**
	 * Add a new memento to the saved memento list.
	 * @param memento
	 */
	public void addMemento(Memento memento) {
		
		savedMementos.add(memento); 
	}
	
	
	/**
	 * Retrieve a saved memento from the list where the index of the list is @param index.
	 * @return memento
	 */
	public Memento getMemento(int index) {
		Memento memento = savedMementos.get(index); 
		if (savedMementos.size() > 0) {
			savedMementos.remove(savedMementos.size()-1);
		}
		return memento; 
	}
	
	/**
	 * Remove all elements in the saved memento list.
	 */
	public void resetStates() {
		savedMementos.clear();
	}


}
