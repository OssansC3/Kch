package kch.accesser;

import am.ik.voicetext4j.EmotionalSpeaker;



public class AccessVoice {

	private final static String API_KEY = "5rrawjshfwagqsx7";

	public void testSay() throws Exception {
        System.setProperty("voicetext.apikey", API_KEY);

        EmotionalSpeaker.HARUKA.ready()
                .pitch(105)
                .speed(105)
                .very().happy()
                .speak("おはようございます");

        // you can use speak(text, "API_KEY") instead of using System.setProperty("voicetext.apikey", "API_KEY")
    }
}
