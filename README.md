# GsonHelper
helper class for [gson](https://github.com/google/gson)

## Develop Custom Message
Since I made full use of java's reflection technique, now building a custom message is much easier.

### How to use
Just focus on your message content.

Example can be found [here](https://github.com/Tomahawkd/GsonHelper/blob/master/src/main/java/io/tomahawkd/gson/ExampleMessage.java)

```java
public class YourMessage extends AbstractMessage {
	
	// Use this annotation if necessary
	// More information about field customization 
	// please go to https://github.com/google/gson
	@SerializedName("m")
	private String message;
	
    public String getMessage() {
    	return message;
    }
}
```

### About Custom Serializer/Deserializer/TypeAdapter

Just add one line for registration

Example can be found [here](https://github.com/Tomahawkd/GsonHelper/blob/master/src/main/java/io/tomahawkd/gson/ExampleDateConverter.java)

```java
public class DateSerializer implements JsonSerializer<Date> {
	
	static {
		TypeAdapterRegister.getInstance().register(Date.class, new DateSerializer());
	}
	
	@Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return context.serialize(src.toString());
	}
}
```