package rs.innolab.lgclientlib.restsdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import rs.innolab.lgclientlib.bet.BetInvoker;
import rs.innolab.lgclientlib.requests.GenericBetRequest;

public class GameHttpHandler implements HttpHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GameHttpHandler.class);

	private String endpoint;

	public GameHttpHandler(String endpoint) {
		this.endpoint = endpoint;
	}

	public void handle(HttpExchange httpExchange) {
		try {
			LOG.info("In handle..");

			Object response = new Object();

			// parse headers
			Map map = httpExchange.getRequestHeaders();
			@SuppressWarnings("unchecked")
			String sessionId = ((List<String>) map.get("Session-ID")).get(0);
			// Parse JSON Request Body
			JSONObject parsedRequest = parseRequestBody(httpExchange);

			// Checks and responds to CORS request -- this allows the ZoOm Browser SDK to
			// make cross-origin calls to the webserver hosting this code
			if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
//				sendResponse(httpExchange, response);
				return;
			}

			if (this.endpoint.equals("bet")) {
				try {
					BetInvoker invoker = new BetInvoker();
					GenericBetRequest betRequest = (GenericBetRequest) new Gson().fromJson(parsedRequest.toString(), invoker.getBetRequestClass());
					invoker.configure(betRequest, sessionId);
//					BetInvoker invoker = new BetInvoker(betRequest, sessionId);
					Object betResults = invoker.execute();
					sendResponse(httpExchange, betResults);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
	}

	private static void sendResponse(HttpExchange httpExchange, Object response) throws IOException {
		int statusCode;

		// Attempts to get status code from responsetypes, if not, use 200
		/*
		 * try { statusCode = response.getCode(); } catch(JSONException e){ statusCode =
		 * 200; }
		 */

		statusCode = 200;

		// Stringifiy responsetypes body
		String responseToSendString = new JSONObject(response).toString();

		// Send responsetypes back to client
		byte[] responseBytes = responseToSendString.getBytes("UTF-8");

		// Set Cors headers
		httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
		httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers",
				"*,X-Requested-With,Content-Type,X-Device-License-Key,X-User-Agent");
		httpExchange.getResponseHeaders().add("Content-Type", "application/json");

		if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
			httpExchange.sendResponseHeaders(204, -1);
			httpExchange.getResponseBody().close();
			return;
		}

		httpExchange.sendResponseHeaders(statusCode, responseBytes.length);

		OutputStream os = httpExchange.getResponseBody();
		os.write(responseBytes);
		os.close();

		// Log responsetypes that is not /status
		String path = httpExchange.getRequestURI().getPath();
		if (!path.equalsIgnoreCase("/status")) {
			System.out.println(responseToSendString);
			// ZoomDatabase.recordTransaction(httpExchange, response);
		}
	}

	private static JSONObject parseRequestBody(HttpExchange httpExchange) {
		try {
			InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);

			String inputLine;
			StringBuffer requestString = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				requestString.append(inputLine);
			}
			br.close();

			if (requestString.toString().isEmpty()) {
				return new JSONObject();
			} else {
				return new JSONObject(requestString.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}

}
