package kch;

import static org.junit.Assert.*;

import org.junit.Test;
public class TestGetTweet {

	/**
	 * 正常系
	 * 対象：GetTweet.getTweet
	 * 条件：ユーザーID:"cloud_spiral"を入力して実行
	 * 期待値：0(正常終了)
	 */
	@Test
	public void testGetTweet01() throws Exception{
		GetTweet gt = new GetTweet();
		int expected = 0;
		int actual = gt.getTweet("cloud_spiral");
		assertEquals(expected,actual);
	}

	/**
	 * 正常系
	 * 対象：GetTweet.getTweet
	 * 条件：ユーザーID:"ossansC3"を入力して実行
	 * 期待値：0(正常終了)
	 */
	@Test
	public void testGetTweet02() throws Exception{
		GetTweet gt = new GetTweet();
		int expected = 0;
		int actual = gt.getTweet("ossansC3");
		assertEquals(expected,actual);
	}

	/**
	 * 異常系
	 * 対象：GetTweet.getTweet
	 * 条件：ユーザーID:"daffdafdaafdafdafadafdaffdaf"(存在しないID)を入力して実行
	 * 期待値：1(TwitterException)
	 */
	@Test
	public void testGetTweet03() throws Exception{
		GetTweet gt = new GetTweet();
		int expected = 1;
		int actual = gt.getTweet("daffdafdaafdafdafadafdaffdaf");
		assertEquals(expected,actual);
	}

}
