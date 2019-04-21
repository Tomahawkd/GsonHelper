package io.tomahawkd.gson.net;

import io.tomahawkd.gson.ErrorMessage;
import io.tomahawkd.gson.Message;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Response<ExpectMessage extends Message> {

	private int status;
	private Class<ExpectMessage> expectMessageClass;
	private Message message;

	private Response(int status, String result, Class<ExpectMessage> clazz) {
		this.status = status;
		this.expectMessageClass = clazz;

		try {
			Message contentMessage;
			if (!isError()) {
				contentMessage = (Message) this.expectMessageClass.getMethod("parse", String.class)
						.invoke(expectMessageClass.newInstance(), result);
			} else {
				contentMessage = new ErrorMessage(status, result);
			}

			this.message = contentMessage;

		} catch (IllegalAccessException |
				InvocationTargetException |
				NoSuchMethodException |
				ClassCastException |
				InstantiationException e) {
			throw new IllegalStateException(e.getCause());
		}
	}

	public static <T extends Message> Response<T> executeForClass(Class<T> clazz,
	                                                       String method,
	                                                       String target_url,
	                                                       @Nullable Map<String, String> param,
	                                                       @Nullable Map<String, String> header,
	                                                       String content) throws IOException {

		StringBuilder target = new StringBuilder(target_url);
		if (param != null) {
			target.append("?");
			param.forEach((k, v) -> target.append(k).append("=").append(v).append("&"));
			target.deleteCharAt(target.length() - 1);
		}

		URL url = new URL(target.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);

		if (header != null) {
			header.forEach(conn::addRequestProperty);
		}

		if (method.equals("POST")) {
			conn.setDoOutput(true);

			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = content.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}
		}
		conn.setConnectTimeout(2000);
		conn.setReadTimeout(2000);

		conn.connect();

		StringBuilder text = new StringBuilder();
		int responseCode = conn.getResponseCode();

		InputStreamReader in;
		if (responseCode == HttpURLConnection.HTTP_OK ||
				responseCode == HttpURLConnection.HTTP_ACCEPTED ||
				responseCode == HttpURLConnection.HTTP_CREATED) {
			in = new InputStreamReader(conn.getInputStream());
		} else {
			in = new InputStreamReader(conn.getErrorStream());
		}

		BufferedReader buff = new BufferedReader(in);
		String line = buff.readLine();
		while (line != null) {
			text.append(line).append("\n");
			line = buff.readLine();
		}

		return new Response<T>(responseCode, text.toString(), clazz);
	}

	public int getStatus() {
		return status;
	}

	public ExpectMessage getExpectMessage() {
		if (isError()) return null;

		try {
			return this.expectMessageClass.cast(message);
		} catch (ClassCastException e) {
			return null;
		}
	}

	public ErrorMessage getErrorMessage() {
		if (isError()) return (ErrorMessage) message;
		else return null;
	}

	public boolean isError() {
		return status != HttpURLConnection.HTTP_OK &&
				status != HttpURLConnection.HTTP_ACCEPTED &&
				status != HttpURLConnection.HTTP_CREATED;
	}

	public String getContentMessage() {
		return message.getMessage();
	}

	@Override
	public String toString() {
		return "Response{" +
				"status=" + status +
				", content='\n" + getContentMessage() + "\n\'" +
				'}';
	}
}
