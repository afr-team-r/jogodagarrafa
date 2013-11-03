package com.afr.spin_questions.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({ "SD_LOCATION", "DEFAULT_CONFIGURATION", "sdLocation","DEFAULT_QUESTIONS" })
public class ConfigurationBean {

	public static final String DEFAULT_CONFIGURATION = "default.json";
	public static final String SD_LOCATION = "AFR/spinquestions/";

	public List<String> getQuestionFiles() {
		return questionFiles;
	}

	public void setQuestionFiles(List<String> questionFiles) {
		this.questionFiles = questionFiles;
	}

	public Integer getGameNumberPlayers() {
		return gameNumberPlayers;
	}

	public void setGameNumberPlayers(Integer gameNumberPlayers) {
		this.gameNumberPlayers = gameNumberPlayers;
	}

	public Integer getGameRotationSpeed() {
		return gameRotationSpeed;
	}

	public void setGameRotationSpeed(Integer gameRotationSpeed) {
		this.gameRotationSpeed = gameRotationSpeed;
	}

	public Integer getGameRotationSpins() {
		return gameRotationSpins;
	}

	public void setGameRotationSpins(Integer gameRotationSpins) {
		this.gameRotationSpins = gameRotationSpins;
	}

	public Integer getGameBottleStyle() {
		return gameBottleStyle;
	}

	public void setGameBottleStyle(Integer gameBottleStyle) {
		this.gameBottleStyle = gameBottleStyle;
	}

	private List<String> questionFiles;

	private Integer gameNumberPlayers;
	private Integer gameRotationSpeed;
	private Integer gameRotationSpins;

	private Integer gameBottleStyle;

	public ConfigurationBean() {

	}

}
