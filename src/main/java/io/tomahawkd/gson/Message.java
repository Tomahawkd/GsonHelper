package io.tomahawkd.gson;

import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Contract;

public interface Message {

	Message parse(String data);

	@Contract("_ -> param1")
	GsonBuilder register(GsonBuilder builder);

	String getMessage();

	String buildJson();
}
