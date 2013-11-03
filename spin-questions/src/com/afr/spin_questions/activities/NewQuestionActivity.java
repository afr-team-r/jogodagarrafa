package com.afr.spin_questions.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.bo.QuestionsManager;

public class NewQuestionActivity extends SherlockActivity {

	private int groupId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.new_question);

		groupId = this.getIntent().getExtras().getInt("groupId");

		final EditText newTitle = (EditText) this.findViewById(R.id.categoryName);
		Button okButton = (Button) this.findViewById(R.id.newCategoryButton);

		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				groupId = QuestionsManager.getGroupIdByIndex(groupId);
				
				QuestionsManager.addQuestionToGroup(groupId, String.valueOf(newTitle.getText()));
				finish();
			}
		});

	}

}
