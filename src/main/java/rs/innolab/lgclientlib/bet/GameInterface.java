package rs.innolab.lgclientlib.bet;

import rs.innolab.lgclientlib.requests.GenericBetRequest;

/**
 * GAME INTERFACE DESCRIPTION HERE
 **/
public interface GameInterface {
	
	/** 
	 * Takes result and collects bets after cut for ongoing round. 
	 * Increases or decreases players' balance depending on result.
	 * @param sessionId 
	 * 
	 * @param bets stores the data in (Key, Value) pairs where Key corresponds 
	 * the number player can bet on, and Value corresponds to confirm bet. 
	 * ---------------CHANGE THIS------------------------
	**/
	public <T extends Object> T bet(GenericBetRequest betRequest, String sessionId);
	
	
//	Response bet(HashMap<String, String> bets);
}
