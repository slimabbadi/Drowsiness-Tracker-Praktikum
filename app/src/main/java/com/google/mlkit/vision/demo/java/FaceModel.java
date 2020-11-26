package com.google.mlkit.vision.demo.java;

import java.util.ArrayList;
import java.util.List;

public class FaceModel {

	public static final float THRESHOLD = 500000000;
	private float diff = 0;
	private final float decreaseFactor = 0.5f;

	List<Subscriber> subscriberList;
	public FaceModel(){
		subscriberList = new ArrayList<>();
	}

	public void updateEyeOpenProbabilities(float leftEye, float rightEye) {


		/* TODO: Finde eine Möglichkeit, diff entsprechend anzupassen, je nachdem wie sehr die Augen geschlossen sind (openProbability = 0).
		     Lege dabei fest, wie schnell der Grenzwert (THRESHOLD) erreicht werden soll.
		 */

		/* TODO: Lege den Grenzwert fest. Das sollte zusammen mit diff festgelegt werden.*/



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
