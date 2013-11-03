package com.afr.spin_questions.beans;

import com.afr.spin_questions.R;

public enum BottleStyle {
	GarrafaUm(1, R.drawable.garrafa1), GarrafaDois(2, R.drawable.garrafa2), GarrafaTres(
			3, R.drawable.garrafa3), GarrafaQuatro(4, R.drawable.garrafa4), GarrafaCinco(
			5, R.drawable.garrafa5);

	private int id;
	private int imageNameId;

	public static final int MAX_BOTTLES = 5;

	BottleStyle(int id, int imageNameId) {
		this.id = id;
		this.imageNameId = imageNameId;
	}

	public int getId() {
		return this.id;
	}

	public int getImageNameId() {
		return this.imageNameId;
	}

	public static BottleStyle getBottle(int id) {
		switch (id) {
		case 1:
			return BottleStyle.GarrafaUm;
		case 2:
			return BottleStyle.GarrafaDois;
		case 3:
			return BottleStyle.GarrafaTres;
		case 4:
			return BottleStyle.GarrafaQuatro;
		case 5:
			return BottleStyle.GarrafaCinco;
		}

		return BottleStyle.GarrafaUm;
	}
}
