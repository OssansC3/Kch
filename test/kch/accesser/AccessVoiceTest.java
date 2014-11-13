package kch.accesser;

import org.junit.Test;

import am.ik.voicetext4j.EmotionalSpeaker;

public class AccessVoiceTest {

	private final static String API_KEY = "5rrawjshfwagqsx7";

	@Test
	public void testVO4jHIKARI03(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("こんにちは");

		System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .happy()
        .speak("グロメンスーパーイケーバル大学院生さん");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .happy()
        .speak("今日も一日");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("お仕事頑張ってね！");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .angry()
        .speak("なんて、言うと思った？");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().angry()
        .speak("マジキモイ、死んでください。");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .sad()
        .speak("何想像してるんですか。警察呼びますよ。");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(90)
        .speed(70)
        .very().sad()
        .speak("私と一緒に、死んでください。");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(80)
        .speed(70)
        .very().sad()
        .speak("あなたとはいつまでも、一緒よ。いつまでも");

	}



	//@Test
	public void testTestSay() throws Exception {
		AccessVoice av = new AccessVoice();
		av.testSay();
	}


	//@Test
	public void testVO4jHARUKA01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HARUKA.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("このGPGのバージョンでは--use--agentは使えませんって怒られた。いやあ、そう言われましても...");
	}


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

	//@Test
	public void testVO4jHIKARI02(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .happy()
        .speak("特に遊び道具とかもないからテレビを見てるうちに寝ちゃったみたい");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .happy()
        .speak("……こうやってみると、やっぱりプロデューサーさんに似てるなぁ");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .sad()
        .speak("可愛いな……私もこんな子が欲しい……");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().sad()
        .speak("それはできれば……プロデューサーさんの……");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().sad()
        .speak("でも、プロデューサーさんとの子供だったら、この子みたいな……あれ？");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .happy()
        .speak("で、でもこの子が今プロデューサーさんだから……");

        EmotionalSpeaker.HIKARI.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("って、し、しかも私何言っちゃってるの！？");
	}


	//@Test
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


	//@Test
	public void testVO4jBEAR01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.BEAR.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("何見てるんだよ");
	}

	//@Test
	public void testVO4jTAKERU01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.TAKERU.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("森のなかでバーベキュー");
	}

	//@Test
	public void testVO4jSANTA01(){
        System.setProperty("voicetext.apikey", API_KEY);
        EmotionalSpeaker.SANTA.ready()
        .pitch(105)
        .speed(105)
        .very().happy()
        .speak("メリークリスマス");
	}

}
