package io.tomahawkd.gson;

import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Contract;

public interface Message {

	/**
	 * Parse Message, receive string form of json, parse it and return correspond message class
	 *
	 * @param data json
	 * @return correspond instance
	 */
	Message parse(String data);

	@Contract("_ -> param1")
	GsonBuilder register(GsonBuilder builder);

	String getMessage();

	/**
	 * Build json message from class
	 *
	 * @return json string
	 */
	String buildJson();
}
