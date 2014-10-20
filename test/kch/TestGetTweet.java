package kch;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
		GetTweet gt = new GetTweet();
		List<String> list = gt.getTweet("cloud_spiral");

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
		GetTweet gt = new GetTweet();
		List<String> list = gt.getTweet("daffdafdaafdafdafadafdaffdaf");

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
		GetTweet gt = new GetTweet();
		List<String> list = gt.getTweet("Dummy");

		int expected = 0;
		int actual = list.size();
		assertEquals(expected,actual);
	}

}
