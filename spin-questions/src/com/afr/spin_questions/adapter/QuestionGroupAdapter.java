package com.afr.spin_questions.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.afr.spin_questions.R;
import com.afr.spin_questions.beans.QuestionGroup;
import com.afr.spin_questions.bo.QuestionsManager;

public class QuestionGroupAdapter extends BaseAdapter {

	private List<QuestionGroup> groups;
	private LayoutInflater mInflater;
	private ViewHolder holder;

	static class ViewHolder {
		private TextView name;
		private CheckBox active;
	}

	public QuestionGroupAdapter(Context context, List<QuestionGroup> groups) {
		mInflater = LayoutInflater.from(context);
		this.groups = groups;
	}

	@Override
	public int getCount() {
		return groups.size();
	}

	@Override
	public Object getItem(int index) {
		return groups.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	public void updateItems(List<QuestionGroup> groups) {
		this.groups = groups;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {

		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.groupquestion_adapter_item, null);
			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.active = (CheckBox) convertView.findViewById(R.id.active);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final QuestionGroup qg = groups.get(posicao);

		holder.name.setText(qg.toString());
		holder.active.setChecked(qg.isEnabled());

		holder.active.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				qg.setEnabled(isChecked);
				
				if(isChecked) {
					QuestionsManager.addToActiveGroup(qg.getId());
				} else {
					QuestionsManager.removeFromActiveGroup(qg.getId());
				}
			}
		});

		return convertView;
	}

}
