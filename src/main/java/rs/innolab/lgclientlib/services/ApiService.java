package rs.innolab.lgclientlib.services;

import java.util.List;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import rs.innolab.lgclientlib.config.Configuration;
import rs.innolab.lgclientlib.config.Network;
import rs.innolab.lgclientlib.requests.GenerateNumberRequest;
import rs.innolab.lgclientlib.requests.GenerateNumberResponse;
import rs.innolab.lgclientlib.requests.UpdateBalanceRequest;
import rs.innolab.lgclientlib.restsdk.Response;

/**
 * 
 * Class that provides an interaction with SMART GAMES backend. in order to
 * retrieve and/or change the data within it.
 * 
 * =================== CHANGE DESC (something else instead of smart games
 * backend)
 *
 */
public class ApiService {

	private String url;
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ApiService() {
		this(Network.MAIN_NET, null);
	}

	public ApiService(String network, String sessionId) {
		setUrl(Configuration.findNetwork(network));
		setSessionId(sessionId);
	}

	public GenerateNumberResponse generateNumber(long min, long max) {
		GenerateNumberRequest request = new GenerateNumberRequest();
		request.setMin(min);
		request.setMax(max);

		HttpResponse<GenerateNumberResponse> response = apiCall("post", "generate-number", null, request, GenerateNumberResponse.class);

		return response.getBody();
	}

	public Response updateBalance(long winnings, long totalBet) {
		UpdateBalanceRequest request = new UpdateBalanceRequest();
		request.setWinnnings(winnings);
		request.setTotalBet(totalBet);

		HttpResponse<Response> response = apiCall("post", "change-balance", null, request, Response.class);

		return response.getBody();
	}

	public <T> HttpResponse<T> apiCall(String type, String path, List<String> params, Object request,
			Class<T> responseClass) {

		if (type == "post") {
			return Unirest.post(this.url + path).header("accept", "application/json")
					.header("Content-Type", "application/json").header("Session-ID", sessionId).body(request)
					.asObject(responseClass);
		} else if (type == "get") {

			/**
			 * TO DO -> CALL GET USING UNIREST
			 **/

			return null;

		} else {
			/*
			 * TO DO -> FINISH THIS
			 */
			return null;
		}

	}

}
