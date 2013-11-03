package com.afr.spin_questions.activities;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.beans.Question;
import com.afr.spin_questions.beans.QuestionGroup;
import com.afr.spin_questions.bo.QuestionsManager;

public class InsideQuestionListActivity extends SherlockActivity {

	private int groupId;
	private ArrayAdapter<Question> adapter;

	protected void onResume() {
		super.onResume();
		
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inside_question_list);

		groupId = this.getIntent().getExtras().getInt("groupId");

		ListView questionsList = (ListView) findViewById(R.id.listQuestionsByGroup);
		Button newQuestionButton = (Button) this.findViewById(R.id.novaPergunta);
		TextView title = (TextView) this.findViewById(R.id.groupTitle);

		QuestionGroup questionGroup = QuestionsManager.getQuestionGroupsAsList().get(groupId);
		
		title.setText(questionGroup.getName());

		List<Question> questions = questionGroup.getQuestions();

		adapter = new ArrayAdapter<Question>(this, android.R.layout.simple_list_item_1, questions);

		questionsList.setAdapter(adapter);
		
		questionsList.setOnItemClickListener(editQuestion);
		newQuestionButton.setOnClickListener(newQuestionsClick);
	}

	OnClickListener newQuestionsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			Intent i = new Intent(v.getContext(), NewQuestionActivity.class);
			i.putExtra("groupId", groupId);
			v.getContext().startActivity(i);
		}
	};
	
	OnItemClickListener editQuestion = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View parent, int pos,
				long id) {
			
			Intent intent = new Intent(parent.getContext(), EditQuestionActivity.class);

			intent.putExtra("groupId", groupId);
			intent.putExtra("questionId", pos);

			parent.getContext().startActivity(intent);
		}
	};

}
