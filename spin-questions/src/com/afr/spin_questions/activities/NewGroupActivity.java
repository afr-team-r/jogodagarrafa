package com.afr.spin_questions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.bo.QuestionsManager;

public class NewGroupActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.new_question_group);

		final EditText newTitle = (EditText) this.findViewById(R.id.categoryName);
		Button okButton = (Button) this.findViewById(R.id.newCategoryButton);

		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int groupId = QuestionsManager.addQuestionGroup(newTitle.getText().toString());
				
				int id = QuestionsManager.findIndexByGroupId(groupId);
				
				Intent i = new Intent(v.getContext(), InsideQuestionListActivity.class);
				
				i.putExtra("groupId", id);
				
				v.getContext().startActivity(i);
				finish();
			}
		});

	}

}
