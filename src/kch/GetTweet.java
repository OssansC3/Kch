package kch;

import java.util.List;
import twitter4j.*;

/**
 * ついーとを取得する
 *
 * @author 2014004 高　良多朗
 *
 */
public class GetTweet {

	/**
	 * ツイート内容を取得するapi
	 * @param アドレス
	 * @return ツイート内容
	 */
	public void getTweet() {
		try{
			Twitter twitter = new TwitterFactory().getInstance();
			User user = twitter.verifyCredentials();
			List<Status> statues = twitter.getHomeTimeline();
			System.out.println("show @"+user.getScreenName()+" shows");
			for (Status status:statues) {
			System.out.println("@"+status.getUser().getScreenName()+":"+status.getText());
			}
			} catch (TwitterException e) {
			e.printStackTrace();
			System.out.println(""+e.getMessage());
		}	}

}
