package io.tomahawkd.gson;

import com.google.gson.GsonBuilder;
import io.tomahawkd.gson.util.TypeAdapterRegister;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;

public abstract class AbstractMessage implements Message {

	private transient GsonBuilder builder = new GsonBuilder();

	public Message parse(String data) {
		register(builder);
		return builder.create().fromJson(data, this.getClass());
	}

	@Contract("_ -> param1")
	public GsonBuilder register(GsonBuilder builder) {
		Field[] fields = this.getClass().getFields();
		for (Field field : fields) {
			Class type = field.getType();
			if (Message.class.isAssignableFrom(type)) {
				try {
					((Message) field.getType().newInstance()).register(builder);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (TypeAdapterRegister.getInstance().isRegistered(type)) {
				TypeAdapterRegister.getInstance().registerToBuilder(builder, type);
			}
		}

		return builder;
	}

	@Override
	public String buildJson() {
		return register(new GsonBuilder()).create().toJson(this);
	}

	@Override
	public String getMessage() {
		return toString();
	}

	@Override
	public String toString() {
		return buildJson();
	}
}
