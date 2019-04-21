package io.tomahawkd.gson;

public class Example {

	public static void main(String[] args) {
		String content = "{ \"id\": 1, \"message\": \"123\", \"date\": \"1970-01-01\" }";
		System.out.println(new ExampleMessage().parse(content));
	}
}