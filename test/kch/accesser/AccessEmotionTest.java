package kch.accesser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import kch.utils.Emotion;

import org.junit.Test;

public class AccessEmotionTest {

	@Test
	public void test() throws UnsupportedEncodingException, IOException {
		List<String> tweetList= new ArrayList<String>();

		//tweetListに任意の文字列を入力すると、感情解析APIの解析結果を確認できます。
		tweetList.add("イライラする。超苛つく。めっちゃムカついて腸が煮えくり返る気持ちだ。マジムカついてる。激おこ。");

		//tweetList.add("悲しい悲しい悲しい悲しい悲しい悲しい悲しい 美味しい美味しい美味しい美味しい");
		//tweetList.add("Gレコは冨野xガンダムという事で見てて、まだ何とも言えないんだけど、ワクワクしないんだよなぁ。まだ過小評価されてるAGEのが面白い気がすんだけど。確かに酷い演出とか突っ込みどころもあるがAGEは過小評価され過ぎな気がするし最後まで見てほしいところ...。");

		/*
		tweetList.add("岡田斗司夫さんが『Gレコ』を酷評「全然ダメじゃん。富野さん何やってんだよ」");
		tweetList.add("嬉しい悲しいこの気持ち");
		tweetList.add("まんじゅう美味しいたぬきダッシュ");
		*/

		AccessEmotion ae = new AccessEmotion();
		Emotion emo = ae.getEmotion(tweetList);

		System.out.println("TotalScore is "+emo.getTotalScore());

		for(int i =0; i < tweetList.size();i++){
			System.out.println("tweet\t"+i+" is "+tweetList.get(i));
			System.out.println("Anger\t" +i+ " is "+ emo.getAngerList().get(i));
			System.out.println("Joy\t" +i+ " is "+ emo.getJoyList().get(i));
			System.out.println("like\t" +i+ " is "+ emo.getLikeList().get(i));
			System.out.println("------------------------------------");
		}

	}
}
