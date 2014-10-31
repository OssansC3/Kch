package kch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import kch.accesser.AccessEmotion;
import kch.utils.Emotion;

import org.junit.Test;

public class GetScoreTest {

	@Test
	public void testGetScore() throws UnsupportedEncodingException, IOException {
		List<String> tweetList= new ArrayList<String>();
		tweetList.add("Gレコは冨野xガンダムという事で見てて、まだ何とも言えないんだけど、ワクワクしないんだよなぁ。まだ過小評価されてるAGEのが面白い気がすんだけど。確かに酷い演出とか突っ込みどころもあるがAGEは過小評価され過ぎな気がするし最後まで見てほしいところ...。");
		tweetList.add("岡田斗司夫さんが『Gレコ』を酷評「全然ダメじゃん。富野さん何やってんだよ」");
		tweetList.add("嬉しい悲しいこの気持ち");
		tweetList.add("まんじゅう美味しいたぬきダッシュ");

		AccessEmotion gs = new AccessEmotion();
		Emotion actual = gs.getEmotion(tweetList);

		System.out.println(actual.getTotalScore());
	}

}
