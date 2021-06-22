package rs.innolab.lgclientlib.restsdk;

import org.json.JSONObject;

public class Response {
	private String message;
	private int code;
	private Object data;
	
	public JSONObject generateResponse () {
		return new JSONObject(this);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	


}
