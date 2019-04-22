package io.tomahawkd.gson;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExampleMessage extends AbstractMessage {

	@SerializedName("id")
	private int serial;
	private String message;
	@JsonAdapter(ExampleDateConverter.class)
	private Date date;

	public int getSerial() {
		return serial;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}
}