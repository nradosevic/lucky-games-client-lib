package rs.innolab.lgclientlib.app;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpServer;

import rs.innolab.lgclientlib.config.Configuration;
import rs.innolab.lgclientlib.restsdk.GameHttpHandler;

public class LuckyGamesClient {
	
	static HttpServer server = null;
	private static final Logger LOG = LoggerFactory.getLogger(LuckyGamesClient.class);
	private static final int DEFAULT_PORT = 8085;

	private static boolean retry = false;

	public static void main(String[] args) {

		init();

	}
	
	public static void init() {
		init(DEFAULT_PORT);

	}
	
	public static void init(int port) {
		try {
			initNetwork();
			initServer(port);
			LOG.info("Client rest app started successfully...");

		} catch (Exception e) {
			LOG.error("There was an error initializing provider");
			e.printStackTrace();
		}
	}

	private static void initServer(int port) {
		try {

			// Create HTTPServer on port 8085
			server = HttpServer.create(new InetSocketAddress(port), 0);

			// Create endpoints
			server.createContext("/bet", new GameHttpHandler("bet"));

			server.setExecutor(null);
			server.start();
			LOG.info("Server started on port " + port);
		} catch (Exception e) {
			e.printStackTrace();
			if (!retry) {
				retry = true;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			initServer(port);
		}

	}

	
	private static void initNetwork() {
		Configuration.initializeVariables();
		
	}
}
