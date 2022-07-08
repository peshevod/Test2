package com.example.restservice;

public class Greeting {

	private final long id;
	private final String content;
	private final String addition = "добавка";

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getAddition() {
		return addition;
	}
}
