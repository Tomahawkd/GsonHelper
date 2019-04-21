package io.tomahawkd.gson.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class TypeAdapterRegister {

	private static TypeAdapterRegister instance;

	private Map<Type, Object> registerer;

	public static TypeAdapterRegister getInstance() {
		if (instance == null) instance = new TypeAdapterRegister();
		return instance;
	}

	public void register(Type type, Object typeAdapter) {
		if (typeAdapter instanceof JsonSerializer<?>
				|| typeAdapter instanceof JsonDeserializer<?>
				|| typeAdapter instanceof InstanceCreator<?>
				|| typeAdapter instanceof TypeAdapter<?>) {

			registerer.put(type, typeAdapter);

		} else throw new IllegalArgumentException("type adapter not compatible");
	}

	public void registerToBuilder(GsonBuilder builder, Type type) {
		Object typeAdapter = registerer.get(type);
		if (typeAdapter!= null) builder.registerTypeAdapter(type, typeAdapter);
	}

	public boolean isRegistered(Type type) {
		return registerer.get(type) != null;
	}
}
