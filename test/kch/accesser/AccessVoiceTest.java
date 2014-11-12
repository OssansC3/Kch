package kch.accesser;

import static org.junit.Assert.*;

import org.junit.Test;

import am.ik.voicetext4j.EmotionalSpeaker;

public class AccessVoiceTest {

	private final static String API_KEY = "5rrawjshfwagqsx7";

	/*
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
	*/

	//@Test
	public void testVO4jHIKARI01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("世界中で1日に行われる放屁の回数を合計すると1日に約700億回にもなり、");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().angry()
        .speak("これを平均すると1人あたり1日に約10回もおならをしている、ということになります。");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().sad()
        .speak("これを平均すると1人あたり1日に約10回もおならをしている、ということになります。");

	}

	@Test
	public void testVO4jHaruka01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HARUKA.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("世界中で1日に行われる放屁の回数を合計すると1日に約700億回にもなり、");

        EmotionalSpeaker.HARUKA.ready()
        .pitch(105)
        .speed(105)
        .very().angry()
        .speak("これを平均すると1人あたり1日に約10回もおならをしている、ということになります。");

        EmotionalSpeaker.HARUKA.ready()
        .pitch(105)
        .speed(105)
        .very().sad()
        .speak("これを平均すると1人あたり1日に約10回もおならをしている、ということになります。");

	}

/*
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
*/
}
