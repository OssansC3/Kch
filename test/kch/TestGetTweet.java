package kch;

import static org.junit.Assert.assertEquals;

import java.util.List;

import kch.accesser.AccessTwitter;

import org.junit.Test;
public class TestGetTweet {

	/**
	 * 正常系
	 * 対象：GetTweet.getTweet
	 * 条件：ユーザーID:"cloud_spiral"を入力して実行
	 * 期待値：10
	 */
	@Test
	public void testGetTweet01() throws Exception{
		AccessTwitter gt = new AccessTwitter();
		List<String> list = gt.getTweetList("cloud_spiral");

		int expected = 10;
		int actual = list.size();
		assertEquals(expected,actual);
	}

	/**
	 * 正常系
	 * 対象：GetTweet.getTweet
	 * 条件：ユーザーID:"daffdafdaafdafdafadafdaffdaf"(存在しないID)を入力して実行
	 * 期待値：0
	 */
	@Test
	public void testGetTweet02() throws Exception{
		AccessTwitter gt = new AccessTwitter();
		List<String> list = gt.getTweetList("daffdafdaafdafdafadafdaffdaf");

		int expected = 0;
		int actual = list.size();
		assertEquals(expected,actual);
	}

	/**
	 * 正常系
	 * 対象：GetTweet.getTweet
	 * 条件：ユーザーID:"Dummy"(一度もつぶやいていないID)を入力して実行
	 * 期待値：0
	 */
	@Test
	public void testGetTweet03() throws Exception{
		AccessTwitter gt = new AccessTwitter();
		List<String> list = gt.getTweetList("Dummy");

		int expected = 0;
		int actual = list.size();
		assertEquals(expected,actual);
	}
}
