package rs.innolab.lgclientlib.bet;

import rs.innolab.lgclientlib.requests.GenericBetRequest;

public class BetInvoker {
	
	private static GameInterface gameInterface;
	private static Class betRequestClass;
	private String sessionId;
	private GenericBetRequest betRequest;
	
	public BetInvoker(GenericBetRequest betRequest, String sessionId) {
		this.betRequest = betRequest;
		this.sessionId = sessionId;
	}
	
	public BetInvoker() {}
	
	public void configure(GenericBetRequest betRequest, String sessionId) {
		this.betRequest = betRequest;
		this.sessionId = sessionId;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T execute() {
		lock();
		Object betResult = gameInterface.bet(betRequest, sessionId);
		unlock();
		
		return (T) betResult;
	}
	
	public static void setGameInterface(GameInterface gameInferface) {
		BetInvoker.gameInterface = gameInferface;
	}
	
	private void lock() {
		// call lock API
	}
	
	private void unlock() {
		// call unlock API
	}

	public GenericBetRequest getBetRequest() {
		return betRequest;
	}

	public void setBetRequest(GenericBetRequest betRequest) {
		this.betRequest = betRequest;
	}

	public static Class getBetRequestClass() {
		return betRequestClass;
	}

	public static void setBetRequestClass(Class klass) {
		betRequestClass = klass;
	}

	

}
