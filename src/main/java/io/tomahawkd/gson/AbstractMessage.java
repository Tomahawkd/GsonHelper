package io.tomahawkd.gson;

import com.google.gson.GsonBuilder;
import io.tomahawkd.gson.util.TypeAdapterRegister;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AbstractMessage implements Message {

	// transient is marked for gson parser for not building this field into json
	private transient GsonBuilder builder;

	protected AbstractMessage() {
		this.builder = register(new GsonBuilder());
	}

	public Message parse(String data) {
		return builder.create().fromJson(data, this.getClass());
	}

	/**
	 * Register custom type adapter/serializer/deserializer if necessary
	 *
	 * @param builder gson builder for registration
	 * @return gson builder
	 */
	@Contract("_ -> param1")
	GsonBuilder register(GsonBuilder builder) {
		Field[] fields = this.getClass().getFields();
		for (Field field : fields) {

			// Ignore transient marked field
			if (Modifier.isTransient(field.getModifiers())) continue;

			Class type = field.getType();

			// when we are meeting a Message class or subclass, we shall register its field
			if (AbstractMessage.class.isAssignableFrom(type)) {
				try {
					((AbstractMessage) field.getType().newInstance()).register(builder);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				// look up for registered class in register
			} else if (TypeAdapterRegister.getInstance().isRegistered(type)) {
				TypeAdapterRegister.getInstance().registerToBuilder(builder, type);
			}
		}

		return builder;
	}

	@Override
	public String buildJson() {
		return builder.create().toJson(this);
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
