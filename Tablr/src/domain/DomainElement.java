package domain;

import facades.CommunicationManager;


public class DomainElement {
	
	/**
	 * Reference to the communicationmanager that is used to talk to the UI.
	 */
	protected CommunicationManager communicationManager;
	
	/**
	 * Sets the communicationManager for this DomainElement.
	 * @param c
	 */
	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
	}
	
	/**
	 * Returns the communicationManager this DomainElement uses.
	 */
	public CommunicationManager getCommunicationManager() {
		return this.communicationManager;
	}
	
	/**
	 * Variable representing whether this DomainElement is in the Error state.
	 */
	private boolean isError = false;
	
	/**
	 * Sets the state of this DomainElement to the Error state.
	 */
	public void isError(){
		isError = true;
	}
	
	/**
	 * Sets the state of this DomainElement to the 'not-Error' state.
	 */
	public void isNotError(){
		isError = false;
	}
	
}
