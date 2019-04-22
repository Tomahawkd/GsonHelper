package io.tomahawkd.gson;

import com.google.gson.GsonBuilder;

public class ErrorMessage extends AbstractMessage {

	private int errorCode;
	private String errorMessage;

	public ErrorMessage() {
		this(-1, "");
	}

	public ErrorMessage(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Message parse(String data) {
		return new ErrorMessage();
	}

	@Override
	public String toString() {
		return "ErrorMessage{" +
				"errorCode=" + errorCode +
				", errorMessage='" + errorMessage + '\'' +
				'}';
	}
}
