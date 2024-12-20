package com.newgen.logger;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.message.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustomMessage implements Message {

	private String messageString;
	private static final long serialVersionUID = 1L;
	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

	public CustomMessage() {
		this(null);
	}

	public CustomMessage(Object msgObj) {
		System.out.println("Into CustomeMessage::"+msgObj);
		parseMessageAsJson(msgObj);
	}

	public CustomMessage(String msgStr) {

		Map<String, String> msgObj = new HashMap<>();
		msgObj.put("message", msgStr);
		parseMessageAsJson(msgObj);
	}

	private void parseMessageAsJson(Object msgObj) {
		System.out.println("Into ParseMessages::");
		messageString = GSON.toJson(msgObj);
	}

	@Override
	public String getFormattedMessage() {
		return messageString;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public Object[] getParameters() {
		return new Object[0];
	}

	@Override
	public Throwable getThrowable() {
		return null;
	}
}