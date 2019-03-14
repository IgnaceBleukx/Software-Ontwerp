package domain;



public abstract class DomainElement {
	

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
	
	public boolean getError() {
		return this.isError;
	}
	
}
