package com.afr.spin_questions.beans;

public class Question {

	private Integer id;
	private String text;

	public Question() {
		this(null, null);
	}

	public Question(String text) {
		this(null, text);
	}

	public Question(Integer id, String text) {
		this.id = id;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}
}
