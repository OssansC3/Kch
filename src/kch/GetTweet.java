package kch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import twitter4j.*;

/**
 * ついーとを取得する
 *
 * @author 2014004 高　良多朗
 *
 */
public class GetTweet {
	private Twitter twitter;
	private Logger logger;

	public GetTweet() {
		twitter = TwitterUtils.getInstance().getTwitterInstance();
		logger = Logger.getLogger(getClass().getName());
	}
	/**
	 * ツイート内容を取得するapi
	 * @param アドレス
	 * @return ツイート内容
	 */
	public int getTweet(String userId) {
		logger.info("GetTweet.getTweet");

		File outFile = new File("tweet.txt");
		try{
			FileWriter out = new FileWriter(outFile);
			List<Status> statusList = twitter.getUserTimeline(userId, new Paging(1,10));
			for(Status status:statusList){
				out.write(format(status.getText())+"\n");
			}
			out.close();
			return 0;
		} catch(TwitterException e){
			logger.severe(e.getMessage());
			return 1;
		} catch (IOException e) {
			logger.severe(e.getMessage());
			return 2;
		}
	}

	private String format(String str){
		return str.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
	}
}
