package kch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * ついーとを取得する
 *
 * @author 2014004 高　良多朗
 *
 */
public class GetTweet {

	private static final String key = "NrIspqi4knQyxKI9JOh4flIQc";
	private static final String secret = "tM9ZzMCiYlXkEkcGLHm9ayPRPzxXUdz5ki43YLLzrXRnlNYHrH";
	private static final String token = "2859875251-E1NKXDeGrI5kqhjaQWE74eeXLEYEhn6TSYvG6q0";
	private static final String tokenSecret = "09kYd1TVf1dujq2vxmxNFNSx3SERNZr9AWqUe0Gtwuh00";
	private static TwitterFactory tf;

	public GetTweet(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(key)
		  .setOAuthConsumerSecret(secret)
		  .setOAuthAccessToken(token)
		  .setOAuthAccessTokenSecret(tokenSecret);
		tf = new TwitterFactory(cb.build());
	}

	/**
	 * ツイート内容を取得するapi
	 * @param アドレス
	 * @return ツイート内容
	 */
	public String getTweet(String userId) {
		File outFile = new File("tweet.txt");
		try{
			FileWriter out = new FileWriter(outFile);
			Twitter twitter = tf.getInstance();
			List<Status> statusList = twitter.getUserTimeline(userId, new Paging(1,10));
			for(Status status:statusList){
				out.write(format(status.getText())+"\n");
			}
			out.close();
			return "done.";
		} catch(TwitterException e){
			System.err.println("TwitterAPIError:"+e);
			return "TwitterAPIError:"+e;
		} catch (IOException e) {
			System.err.println("FileOutputError:"+e);
			return "FileOutputError:"+e;
		}
	}

	private String format(String str){
		return str.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
	}
}
