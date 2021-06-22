package rs.innolab.lgclientlib.config;

import java.util.HashMap;

public class Configuration {
	
	private static HashMap<String, String> networks;
	
	// Network
	public static final String MAIN_NET = "";
//	public static final String TEST_NET = "http://localhost:8080/smart-games/player/";
	public static final String TEST_NET = "http://162.241.175.126:8060/smart-games/";
	
	
	public static void  initializeVariables() {
		
		networks = new HashMap<String, String>();
		
		networks.put("MAIN_NET", Configuration.MAIN_NET);
		networks.put("TEST_NET", Configuration.TEST_NET);
		
	}
	
	public static String findNetwork(String key) {
		return networks.get(key);
	}
}
