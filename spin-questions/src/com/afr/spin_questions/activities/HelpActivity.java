package com.afr.spin_questions.activities;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.afr.spin_questions.R;

public class HelpActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ajuda);

		final TextView text = (TextView) this.findViewById(R.id.ajudaText);
		Button okButton = (Button) this.findViewById(R.id.ajudaButton);
		
		text.setMovementMethod(new ScrollingMovementMethod());
		
		text.setText("Instruções básicas: \n\n " +
						"- Para jogar, apenas clique na garrafa, a pergunta aparecerá abaixo da mesma" +
						"\n\n- Para editar ou deletar uma categoria, dê um longo clique (mantenha apertado) encima da categoria na lista" +
						"\n\n- Para editar ou remover uma pergunta, apenas clique sobre ela" +
						"\n\n- As perguntas são salvas automaticamente ao fechar o aplicativo normalmente" +
						"\n\n- A velocidade de rotação máxima é 200 (gira mais rápido) e o mínimo é zero (bem lento)" +
						"\n\n- Divirta-se :)" +
						"\n\n\n Dúvidas, sugestões, reclamações, apenas uma conversa?" +
						"\n\n Estou à disposição: ricardofagodoy@gmail.com\n");

		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {		
				finish();
			}
		});

	}

}
