package domain;

import facades.CommunicationManager;


public class DomainElement {
	
	/**
	 * Reference to the communicationsmanager that is used to talk to the UI.
	 */
	protected CommunicationManager communicationManager;

	public void setCommunicationManager(CommunicationManager c) {
		this.communicationManager = c;
	}
	
	public CommunicationManager getCommunicationManager() {
		return this.communicationManager;
	}
	
	private boolean isError = false;
	
	public void isError(){
		isError = true;
	}
	public void isNotError(){
		isError = false;
	}
	
}
