package kch;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtils {
	private static final String key = "NrIspqi4knQyxKI9JOh4flIQc";
	private static final String secret = "tM9ZzMCiYlXkEkcGLHm9ayPRPzxXUdz5ki43YLLzrXRnlNYHrH";
	private static final String token = "2859875251-E1NKXDeGrI5kqhjaQWE74eeXLEYEhn6TSYvG6q0";
	private static final String tokenSecret = "09kYd1TVf1dujq2vxmxNFNSx3SERNZr9AWqUe0Gtwuh00";
	private static TwitterFactory tf;
	private static TwitterUtils singleton = new TwitterUtils();

	/**
	 * シングルトンインスタンス取得
	 * @return シングルトンインスタンス
	 */
	public static TwitterUtils getInstance(){
		return singleton;
	}
	private TwitterUtils(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(key)
		  .setOAuthConsumerSecret(secret)
		  .setOAuthAccessToken(token)
		  .setOAuthAccessTokenSecret(tokenSecret);
		tf = new TwitterFactory(cb.build());
	}

	/**
	 * ツイッターのインスタンスを呼び出す．
	 * 普段はこれからAPIを呼ぶべし

	 * @return ツイッターのインスタンスオブジェクト
	 */
	public Twitter getTwitterInstance(){
		return tf.getInstance();
	}

	public static String sanitize(String str){
		if (str == null) return "";
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\n", "");
		str = str.replaceAll("\t", "");
		str = str.replaceAll("&","&amp;");
		str = str.replaceAll("<","&lt;");
		str = str.replaceAll(">","&gt;");
		str = str.replaceAll("\"","&quot;");
		str = str.replaceAll("'","&#39;");
		return str;
	}

}
