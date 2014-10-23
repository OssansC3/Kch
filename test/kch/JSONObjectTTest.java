package kch;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class JSONObjectTTest {

	/**
	 * @author 2014033
	 * encText1は以下のコメントをUTF-8エンコードしたものです
	 * "Gレコは冨野xガンダムという事で見てて、まだ何とも言えないんだけど、ワクワクしないんだよなぁ。まだ過小評価されてるAGEのが面白い気がすんだけど。確かに酷い演出とか突っ込みどころもあるがAGEは過小評価され過ぎな気がするし最後まで見てほしいところ...。"
	 *
	 * その結果として、以下の値が期待されます
	 * likedislike=1
	 * joysad=1
	 * angerfear=0
	 * analyzedtext="(入力文字列)"
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private String encText1 = "G%e3%83%ac%e3%82%b3%e3%81%af%e5%86%a8%e9%87%8ex%e3%82%ac%e3%83%b3%e3%83%80%e3%83%a0%e3%81%a8%e3%81%84%e3%81%86%e4%ba%8b%e3%81%a7%e8%a6%8b%e3%81%a6%e3%81%a6%e3%80%81%e3%81%be%e3%81%a0%e4%bd%95%e3%81%a8%e3%82%82%e8%a8%80%e3%81%88%e3%81%aa%e3%81%84%e3%82%93%e3%81%a0%e3%81%91%e3%81%a9%e3%80%81%e3%83%af%e3%82%af%e3%83%af%e3%82%af%e3%81%97%e3%81%aa%e3%81%84%e3%82%93%e3%81%a0%e3%82%88%e3%81%aa%e3%81%81%e3%80%82%e3%81%be%e3%81%a0%e9%81%8e%e5%b0%8f%e8%a9%95%e4%be%a1%e3%81%95%e3%82%8c%e3%81%a6%e3%82%8bAGE%e3%81%ae%e3%81%8c%e9%9d%a2%e7%99%bd%e3%81%84%e6%b0%97%e3%81%8c%e3%81%99%e3%82%93%e3%81%a0%e3%81%91%e3%81%a9%e3%80%82%e7%a2%ba%e3%81%8b%e3%81%ab%e9%85%b7%e3%81%84%e6%bc%94%e5%87%ba%e3%81%a8%e3%81%8b%e7%aa%81%e3%81%a3%e8%be%bc%e3%81%bf%e3%81%a9%e3%81%93%e3%82%8d%e3%82%82%e3%81%82%e3%82%8b%e3%81%8cAGE%e3%81%af%e9%81%8e%e5%b0%8f%e8%a9%95%e4%be%a1%e3%81%95%e3%82%8c%e9%81%8e%e3%81%8e%e3%81%aa%e6%b0%97%e3%81%8c%e3%81%99%e3%82%8b%e3%81%97%e6%9c%80%e5%be%8c%e3%81%be%e3%81%a7%e8%a6%8b%e3%81%a6%e3%81%bb%e3%81%97%e3%81%84%e3%81%a8%e3%81%93%e3%82%8d%2e%2e%2e%e3%80%82";
	private String apikey= "ADFAAD4575EAF50C9A4B19708C8C1BAD27170112"; //感情解析APIを使用するためのAPIKEY

	public void setJsonData() throws UnsupportedEncodingException, IOException{
		String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText1;
		JSONObjectT jso = new JSONObjectT();
		jso.setJson(url);
	}

	@Test
	public void testGetAngerFear() throws UnsupportedEncodingException, IOException {
		String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText1;
		JSONObjectT jso = new JSONObjectT();
		jso.setJson(url);
		assertEquals(0, jso.getAngerFear());
	}

	@Test
	public void testGetJoySad() throws UnsupportedEncodingException, IOException {
		String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText1;
		JSONObjectT jso = new JSONObjectT();
		jso.setJson(url);
		assertEquals(1, jso.getJoySad());
	}

	@Test
	public void testGetLikeDislike() throws UnsupportedEncodingException, IOException {
		String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText1;
		JSONObjectT jso = new JSONObjectT();
		jso.setJson(url);
		assertEquals(1, jso.getLikeDislike());
	}

	@Test
	public void testGetText() throws UnsupportedEncodingException, IOException {
		String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText1;
		JSONObjectT jso = new JSONObjectT();
		jso.setJson(url);
		String tweet = "Gレコは冨野xガンダムという事で見てて、まだ何とも言えないんだけど、ワクワクしないんだよなぁ。まだ過小評価されてるAGEのが面白い気がすんだけど。確かに酷い演出とか突っ込みどころもあるがAGEは過小評価され過ぎな気がするし最後まで見てほしいところ...。";
		System.out.println(jso.getText());
		//assertEquals( tweet, jso.getText());
	}

}
