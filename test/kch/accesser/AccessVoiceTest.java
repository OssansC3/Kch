package kch.accesser;

import static org.junit.Assert.*;

import org.junit.Test;

import am.ik.voicetext4j.EmotionalSpeaker;

public class AccessVoiceTest {

	private final static String API_KEY = "5rrawjshfwagqsx7";


	@Test
	public void testTestSay() throws Exception {
		AccessVoice av = new AccessVoice();
		av.testSay();
	}


	@Test
	public void testVO4jHARUKA01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HARUKA.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("このGPGのバージョンでは--use--agentは使えませんって怒られた。いやあ、そう言われましても...");
	}

	@Test
	public void testVO4jHIKARI01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("あなたのことが大好きです。");
	}

	@Test
	public void testVO4jBEAR01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.BEAR.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("何見てるんだよ");
	}

	@Test
	public void testVO4jTAKERU01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.TAKERU.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("森のなかでバーベキュー");
	}

	@Test
	public void testVO4jSANTA01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.SANTA.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("メリークリスマス");
	}

}
