package com.afr.spin_questions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.adapter.QuestionGroupAdapter;
import com.afr.spin_questions.bo.QuestionsManager;

public class QuestionListActivity extends SherlockActivity {

	private QuestionGroupAdapter adapter = null;

	protected void onResume() {
		super.onResume();
		
		adapter.updateItems(QuestionsManager.getQuestionGroupsAsList());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_list);

		ListView questionsList = (ListView) findViewById(R.id.listQuestions);
		Button newQuestionsButton = (Button) this.findViewById(R.id.addQuestions);

		adapter = new QuestionGroupAdapter(this, QuestionsManager.getQuestionGroupsAsList());

		questionsList.setAdapter(adapter);

		questionsList.setOnItemClickListener(enterGroupClick);
		questionsList.setOnItemLongClickListener(editCategory);
		questionsList.setItemsCanFocus(true);

		newQuestionsButton.setOnClickListener(newQuestionsClick);
	}

	OnClickListener newQuestionsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			v.getContext().startActivity(
					new Intent(v.getContext(), NewGroupActivity.class));
		}
	};

	OnItemClickListener enterGroupClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View parent, int pos,
				long id) {
			
			Intent intent = new Intent(parent.getContext(), InsideQuestionListActivity.class);

			intent.putExtra("groupId", pos);

			parent.getContext().startActivity(intent);
		}
	};
	
	OnItemLongClickListener editCategory = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View parent,
				int pos, long id) {
			
			Intent intent = new Intent(parent.getContext(), EditCategoryActivity.class);
			
			intent.putExtra("groupId", pos);

			parent.getContext().startActivity(intent);
			
			return true;
		}
	};

}
