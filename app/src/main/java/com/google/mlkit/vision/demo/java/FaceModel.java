package com.google.mlkit.vision.demo.java;

import java.util.ArrayList;
import java.util.List;

public class FaceModel {

	public static final float THRESHOLD = 100; //5000000000
	private float diff = 0;
	private final float decreaseFactor = 0.5f;

	List<Subscriber> subscriberList;
	public FaceModel(){
		subscriberList = new ArrayList<>();
	}

	public void updateEyeOpenProbabilities(float leftEye, float rightEye) {

		//TODO

		if(leftEye < 0.8f && rightEye < 0.8f) {
			//Beide Augen sind höchstens zu 80% geöffnet

			//Durchschnitt von beiden Augen bilden
			float mean = (leftEye + rightEye) / 2;

			//Den Anteil, zu dem die Augen geschlossen sind, aufaddieren
			diff += (1 - mean);
		} else {
			//Beide Augen sind min. zu 80% geöffnet

			//In dem Fall den Wert langsam dekrementieren
			diff -= decreaseFactor;
			if (diff < 0) {
				diff = 0;
			}
		}

		if (diff >= THRESHOLD) {
			for (Subscriber subscriber : subscriberList) {
				subscriber.notifySubscriber();
			}

			//2/3 des Schwellwertes abziehen, damit der Alarm nicht gleich wieder auslöst!
			diff -= (THRESHOLD*2/3);
		}
	}

	public boolean addSubscriber(Subscriber subscriber) {
		return subscriberList.add(subscriber);
	}

	public float getDiff() {
		return diff;
	}
}
