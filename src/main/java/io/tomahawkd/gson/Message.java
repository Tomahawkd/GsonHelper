package io.tomahawkd.gson;

public interface Message {

	/**
	 * Parse Message, receive string form of json, parse it and return correspond message class
	 *
	 * @param data json
	 * @return correspond instance
	 */
	Message parse(String data);

	/**
	 * Get message content, {@link Message#toString()} by default
	 *
	 * @return message content
	 */
	String getMessage();

	/**
	 * Build json message from class
	 *
	 * @return json string
	 */
	String buildJson();
}
