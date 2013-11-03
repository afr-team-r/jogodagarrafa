package com.afr.spin_questions.activities;

import java.util.Random;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.afr.spin_questions.R;
import com.afr.spin_questions.beans.BottleStyle;
import com.afr.spin_questions.beans.ConfigurationBean;
import com.afr.spin_questions.bo.ConfigurationManager;
import com.afr.spin_questions.bo.QuestionsManager;

public class MainActivity extends SherlockActivity {

	private static final long BASE_SLEEP_TIME = 200L;
	private static final String INITIAL_MESSAGE = "Toque na garrafa para começar!";
	
	enum MenuItens {
		SHOW_QUESTIONS(0, "Perguntas"), SHOW_CONFIGS(1, "Configurações"), EXIT(
				2, "Sair");

		private String displayText;
		private final int id;

		MenuItens(int id, String displayText) {
			this.displayText = displayText;
			this.id = id;
		}

		public String getDisplayText() {
			return this.displayText;
		}

		public int getId() {
			return this.id;
		}
	}

	private boolean isSpinning = false;
	private Bitmap bMapRotate = null;

	private TextView question = null;
	private ImageView pointer = null;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.perguntas:
			this.startActivity(new Intent(this, QuestionListActivity.class));
			break;

		case R.id.configuracoes:
			this.startActivity(new Intent(this, ConfigurationActivity.class));
			break;
			
		case R.id.ajuda:
			this.startActivity(new Intent(this, HelpActivity.class));
		break;

		case R.id.sair:
			this.finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	/* MENU END */

	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		QuestionsManager.persistQuestions();
		ConfigurationManager.persistConfigurations();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		pointer.setImageResource(BottleStyle.getBottle(ConfigurationManager.getInstance().getGameBottleStyle()).getImageNameId());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		question = (TextView) findViewById(R.id.question);
		pointer = (ImageView) findViewById(R.id.pointer);
		pointer.setImageResource(BottleStyle.getBottle(ConfigurationManager.getInstance().getGameBottleStyle()).getImageNameId());

		question.setText(INITIAL_MESSAGE);

		pointer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (!isSpinning) {

					new Thread(new Runnable() {
						public void run() {

							ConfigurationBean config = ConfigurationManager
									.getInstance();

							float rotationAngle = (int) (360 / config
									.getGameNumberPlayers());
							long speedTime = BASE_SLEEP_TIME
									- config.getGameRotationSpeed();

							isSpinning = true;

							Matrix matrix = new Matrix();
							Bitmap bMap = BitmapFactory.decodeResource(
									getResources(), BottleStyle.getBottle(ConfigurationManager.getInstance().getGameBottleStyle()).getImageNameId());

							int rotations = config.getGameRotationSpins() * config.getGameNumberPlayers() + new Random().nextInt(config.getGameNumberPlayers());
									
							for (int i = 0; i < rotations; i++) {

								matrix.postRotate(rotationAngle);

								bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
										bMap.getWidth(), bMap.getHeight(),
										matrix, true);

								refreshImage.sendEmptyMessage(0);

								try {
									Thread.sleep(speedTime);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
	
							refreshQuestion.sendEmptyMessage(0);
							isSpinning = false;
						}
					}).start();
				}
			}
		});
		
		if(ConfigurationManager.isDefaultConfiguration()) {
			this.startActivity(new Intent(this, HelpActivity.class));
		}
		
	}

	/* Handlers to hadle with UI */

	private Handler refreshImage = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pointer.setImageBitmap(bMapRotate);
		}
	};

	private Handler refreshQuestion = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			question.setText(QuestionsManager.getRandomQuestion());
		}
	};

}
