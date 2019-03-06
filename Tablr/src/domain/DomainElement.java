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
	
}
