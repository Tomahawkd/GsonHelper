package io.tomahawkd.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExampleDateConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = json.getAsString().replace("\"", "");
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return context.serialize(formatter.format(src));
	}
}
