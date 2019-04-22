# GsonHelper
helper class for [gson](https://github.com/google/gson)

## Develop Custom Message
Since I made full use of java's reflection technique, now building a custom message is much easier.

### How to use
#### Custom Messge
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

#### Network Request
A `Response` class is made for easier connection and transform from json text to class.
```
// return Response<YourClass>
Response.executeForClass(YourClass.class, method, url, parameter, header, content)
```
You can set parameter/header to if there is no additional data

Now you can check if there is something wrong
```
if (response.isError()) {
    ErrorMessage err = response.getErrorMessage();
} else {
    YourClass message = response.getExpectMessage();
}
```

### About Custom Serializer/Deserializer/TypeAdapter

Just add one line for registration using annotation `@JsonAdapter`

Example can be found [here](https://github.com/Tomahawkd/GsonHelper/blob/master/src/main/java/io/tomahawkd/gson/ExampleDateConverter.java)

```

// Use at field level
@JsonAdapter(YourClassAdapter.class)
private Class yourclass;

// Use at class level
@JsonAdapter(YourClassAdapter.class)
public class YourClass {

}
```