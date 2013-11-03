package com.afr.spin_questions.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.beans.ConfigurationBean;
import com.afr.spin_questions.bo.ConfigurationManager;
import com.afr.spin_questions.bo.QuestionsManager;

public class SplashActivity extends SherlockActivity implements Runnable {

	private static final Long SPLASH_TIME = 2000L;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		new Handler().postDelayed(this, SPLASH_TIME);

		loadConfiguration();
		loadQuestions();
	}

	@Override
	public void run() {
		this.startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	private void loadConfiguration() {

		try {

			File sdFile = new File(
					Environment
							.getExternalStoragePublicDirectory(ConfigurationBean.SD_LOCATION),
					ConfigurationBean.DEFAULT_CONFIGURATION);

			InputStream is = null;

			if (sdFile.exists()) {
				is = new FileInputStream(sdFile);
				ConfigurationManager.setDefaultConfiguration(false);
			} else {
				is = this.getResources().getAssets()
						.open(ConfigurationBean.DEFAULT_CONFIGURATION);
				ConfigurationManager.setDefaultConfiguration(true);
			}

			ConfigurationManager.init(is);

			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadQuestions() {
		QuestionsManager.init(getResources().getAssets());
	}

}
