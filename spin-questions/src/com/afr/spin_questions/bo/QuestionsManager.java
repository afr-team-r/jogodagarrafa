package com.afr.spin_questions.bo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Environment;
import com.afr.spin_questions.beans.ConfigurationBean;
import com.afr.spin_questions.beans.Question;
import com.afr.spin_questions.beans.QuestionGroup;

public class QuestionsManager {

	private static final String QUESTIONS_FILE_PATTERN = "questions{0}.json";
	private static final String NO_ACTIVE_QUESTIONS = "Sem perguntas ativas!";
	private static final String NO_QUESTIONS_IN_GROUP = "Grupo de perguntas vazio!";

	@SuppressLint("UseSparseArrays")
	private static Map<Integer, QuestionGroup> questionGroups = new HashMap<Integer, QuestionGroup>();
	private static List<Integer> activeGroups = new ArrayList<Integer>();

	/* LOAD AND PERSIST QUESTION METHODS */

	public static void init(AssetManager am) {

		activeGroups.clear();
		questionGroups.clear();

		ObjectMapper mapper = new ObjectMapper();

		if (ConfigurationManager.isDefaultConfiguration()) {

			List<String> questionFiles = ConfigurationManager.getInstance().getQuestionFiles();
			
			for (int i = 0; i < questionFiles.size(); i++) {
				
				try {
	
					InputStream is = am.open(questionFiles.get(i));
							
					QuestionGroup defaultQg = mapper.readValue(is, QuestionGroup.class);
					is.close();
	
					QuestionsManager.addQuestionGroup(defaultQg);
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return;
		}
		
		List<String> questionFiles = ConfigurationManager.getInstance().getQuestionFiles();
		
		for (int i = 0; i < questionFiles.size(); i++) {

			File file = new File(
					Environment
							.getExternalStoragePublicDirectory(ConfigurationBean.SD_LOCATION),
					questionFiles.get(i));

			try {
				QuestionGroup qg = mapper.readValue(file, QuestionGroup.class);

					QuestionsManager.addQuestionGroup(qg);

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static boolean persistQuestions() {

		ConfigurationManager.getInstance().getQuestionFiles().clear();

		File sdFolder = Environment
				.getExternalStoragePublicDirectory(ConfigurationBean.SD_LOCATION);

		if (!sdFolder.exists()) {
			sdFolder.mkdirs();
		}

		ObjectMapper mapper = new ObjectMapper();

		try {

			for (Integer qgId : questionGroups.keySet()) {

				File sdQuestionFile = new File(sdFolder,
						QUESTIONS_FILE_PATTERN.replace("{0}",
								String.valueOf(qgId)));

				ConfigurationManager.getInstance().getQuestionFiles()
						.add(sdQuestionFile.getName());

				mapper.writeValue(sdQuestionFile, questionGroups.get(qgId));
			}

			return true;

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/******************************************************************************************************/

	/* GET RANDOM QUESTION */

	public static String getRandomQuestion() {

		Random rand = new Random();

		int activeGroupsSize = activeGroups.size();

		if (activeGroupsSize == 0) {
			return NO_ACTIVE_QUESTIONS;
		}

		int groupId = activeGroups.get(rand.nextInt(activeGroupsSize));

		List<Question> questions = questionGroups.get(groupId).getQuestions();

		if (questions.size() == 0) {
			return NO_QUESTIONS_IN_GROUP;
		}

		return questions.get(rand.nextInt(questions.size())).getText();
	}

	/******************************************************************************************************/

	/* CONTROLLERS */

	/* ACTIVE GROUP */

	public static void removeFromActiveGroup(Integer id) {
		activeGroups.remove(id);
	}

	public static void addToActiveGroup(int id) {
		activeGroups.add(id);
	}

	/* QUESTION GROUP */

	/* GETTERS AND SETTERS */

	public static Map<Integer, QuestionGroup> getQuestionGroups() {
		return QuestionsManager.questionGroups;
	}

	public static List<QuestionGroup> getQuestionGroupsAsList() {
		return new ArrayList<QuestionGroup>(
				QuestionsManager.questionGroups.values());
	}

	public static QuestionGroup getQuestionGroupById(int id) {
		return QuestionsManager.questionGroups.get(id);
	}

	/* MANIPULATE QUESTIONS */

	public static List<Question> getQuestionFromGroup(int id) {
		return QuestionsManager.questionGroups.get(id).getQuestions();
	}

	public static void addQuestionToGroup(int groupId, Question q) {
		QuestionsManager.questionGroups.get(groupId).addQuestion(q);
	}

	public static void addQuestionToGroup(int groupId, String questionText) {

		QuestionGroup qg = QuestionsManager.questionGroups.get(groupId);

		int newQuestionId = qg.getQuestions().size();

		addQuestionToGroup(groupId, new Question(newQuestionId, questionText));
	}

	public static Integer addQuestionGroup(QuestionGroup qg) {

		qg.setId(questionGroups.size() + 1);

		QuestionsManager.questionGroups.put(qg.getId(), qg);

		if (qg.isEnabled()) {
			QuestionsManager.addToActiveGroup(qg.getId());
		}

		return qg.getId();
	}

	public static Integer addQuestionGroup(String name) {

		QuestionGroup qg = new QuestionGroup(name);

		return addQuestionGroup(qg);
	}

	public static int findIndexByGroupId(int groupId) {

		List<QuestionGroup> qgList = getQuestionGroupsAsList();

		for (int i = 0; i < qgList.size(); i++) {
			if (qgList.get(i).getId().equals(groupId)) {
				return i;
			}
		}

		return -1;
	}

	public static int getGroupIdByIndex(int i) {
		return getQuestionGroupsAsList().get(i).getId();
	}

	public static void removeQuestionGroup(int id) {
		QuestionsManager.questionGroups.remove(id);
		removeFromActiveGroup(id);
	}

	public static void disableQuestionGroup(int id) {
		QuestionsManager.questionGroups.get(id).setEnabled(false);
	}

	public static void enableQuestionGroup(int id) {
		QuestionsManager.questionGroups.get(id).setEnabled(true);
	}

}
