package com.afr.spin_questions.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;
import com.afr.spin_questions.beans.BottleStyle;
import com.afr.spin_questions.beans.ConfigurationBean;
import com.afr.spin_questions.bo.ConfigurationManager;

public class ConfigurationActivity extends SherlockActivity {

	EditText numberPlayers;
	EditText rotationSpeed;
	EditText rotationSpins;
	
	Button mudarGarrafas;
	QuickContactBadge garrafas;

	@Override
	protected void onDestroy() {
		super.onDestroy();

		saveConfiguration();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration_layout);

		ConfigurationBean config = ConfigurationManager.getInstance();

		numberPlayers = (EditText) findViewById(R.id.numberPlayers);
		rotationSpeed = (EditText) findViewById(R.id.rotationSpeed);
		rotationSpins = (EditText) findViewById(R.id.rotationSpins);
		
		mudarGarrafas = (Button) findViewById(R.id.mudarGarrafas);
		Button saveButton = (Button) findViewById(R.id.save);
		
		garrafas = (QuickContactBadge) findViewById(R.id.garrafas);
		garrafas.setImageResource(BottleStyle.getBottle(ConfigurationManager.getInstance().getGameBottleStyle()).getImageNameId());

		numberPlayers.setText(String.valueOf(config.getGameNumberPlayers()));
		rotationSpeed.setText(String.valueOf(config.getGameRotationSpeed()));
		rotationSpins.setText(String.valueOf(config.getGameRotationSpins()));

		numberPlayers.setOnFocusChangeListener(new ValidateFields(
				numberPlayers, "Número de jogadores", 1, 60));
		rotationSpeed.setOnFocusChangeListener(new ValidateFields(
				rotationSpeed, "Velocidade da rotação", 1, 200));
		rotationSpins.setOnFocusChangeListener(new ValidateFields(
				rotationSpins, "Número de rotações", 0, 2000));

		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveConfiguration();
			}
		});
		
		mudarGarrafas.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int bottleId = ConfigurationManager.getInstance().getGameBottleStyle();
				
				bottleId++;
				
				if (bottleId > BottleStyle.MAX_BOTTLES)
					bottleId = 1;
				
				ConfigurationManager.getInstance().setGameBottleStyle(bottleId);
				garrafas.setImageResource(BottleStyle.getBottle(bottleId).getImageNameId());
			}
		});
		
	}

	private class ValidateFields implements OnFocusChangeListener {

		private Integer max = null, min = null;
		private EditText field = null;
		private String fieldName = null;

		public ValidateFields(EditText field, String fieldName, Integer min,
				Integer max) {
			this.setMin(min);
			this.setMax(max);
			this.field = field;
			this.fieldName = fieldName;
		}

		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			validateFields(this.field, this.fieldName, this.min, this.max);
		}

		public void setMax(Integer max) {
			this.max = max;
		}

		public void setMin(Integer min) {
			this.min = min;
		}
	}

	private void saveConfiguration() {

		validateFields(numberPlayers, "Número de jogadores", 1, 60);
		validateFields(rotationSpeed, "Velocidade da rotação", 1, 200);
		validateFields(rotationSpins, "Número de rotações", 0, 2000);

		ConfigurationManager.getInstance().setGameNumberPlayers(
				Integer.parseInt(numberPlayers.getText().toString()));

		ConfigurationManager.getInstance().setGameRotationSpeed(
				Integer.parseInt(rotationSpeed.getText().toString()));

		ConfigurationManager.getInstance().setGameRotationSpins(
				Integer.parseInt(rotationSpins.getText().toString()));
		
		this.finish();		
	}

	private void validateFields(EditText field, String fielName, int min,
			int max) {

		int value = Integer.parseInt(field.getText().toString());

		if (value > max) {
			value = max;
			Toast.makeText(getApplicationContext(),
					"Valor máximo permitido para " + fielName + ": " + max,
					Toast.LENGTH_SHORT).show();
		} else {
			if (value < min) {
				value = min;
				Toast.makeText(getApplicationContext(),
						"Valor mínimo permitido para " + fielName + ": " + min,
						Toast.LENGTH_SHORT).show();
			}
		}

		field.setText(String.valueOf(value));
	}
}