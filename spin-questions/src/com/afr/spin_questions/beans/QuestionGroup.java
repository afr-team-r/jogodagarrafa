package com.afr.spin_questions.beans;

import java.util.ArrayList;
import java.util.List;

public class QuestionGroup {

	private Integer id;
	private String name;
	private boolean enabled;
	private List<Question> questions;

	public QuestionGroup() {
		this(null, null);
	}

	public QuestionGroup(String name) {
		this(null, name);

	}

	public QuestionGroup(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.enabled = true;
		this.questions = new ArrayList<Question>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question q) {
		this.questions.add(q);
	}

	public void removeQuestion(Question q) {
		this.questions.remove(q);
	}

	public void removeQuestion(int id) {
		this.questions.remove(id);
	}

	public String toString() {
		return id + " - " + name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
