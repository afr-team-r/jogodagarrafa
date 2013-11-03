package com.afr.spin_questions.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.beans.QuestionGroup;
import com.afr.spin_questions.bo.QuestionsManager;

public class EditQuestionActivity extends SherlockActivity {

	private int groupId, questionId;
	private Button SaveButton = null;
	private Button DeleteButton = null;
	private TextView title = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_question);

		groupId = this.getIntent().getExtras().getInt("groupId");
		questionId = this.getIntent().getExtras().getInt("questionId");

		SaveButton = (Button) this.findViewById(R.id.SaveButton);
		DeleteButton = (Button) this.findViewById(R.id.DeleteButton);
		title = (TextView) this.findViewById(R.id.questionText);

		groupId = QuestionsManager.getGroupIdByIndex(groupId);
		QuestionGroup qg = QuestionsManager.getQuestionGroupById(groupId);

		title.setText(qg.getQuestions().get(questionId).toString());

		SaveButton.setOnClickListener(saveButtonListener);
		DeleteButton.setOnClickListener(deleteButtonListener);
	}

	OnClickListener saveButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			QuestionsManager.getQuestionGroupById(groupId).getQuestions()
					.get(questionId).setText(String.valueOf(title.getText()));

			finish();
		}
	};

	OnClickListener deleteButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			QuestionsManager.getQuestionGroupById(groupId).removeQuestion(
					questionId);
			
			finish();
		}
	};

}
