package de.htwg.se.util.observer;

/**
 * Observer that receives changes from Subjects.
 * UserInterface?!
 */
public interface ObserverObserver {
	
	/**
	 * Updates the Observer to the current State of the controller.
	 */
	void update();
}
