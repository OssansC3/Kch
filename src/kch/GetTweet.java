package kch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

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
	 * ツイート内容を取得する．<br/>
	 * ツイッター例外が帰った場合は空リストを返す．
	 * @param アドレス
	 * @return ツイート内容のリスト．例外の場合は空リスト．
	 */
	public List<String> getTweet(String userId) {
		logger.info("GetTweet.getTweet");
		List<String> tweetList = new ArrayList<String>();
		try{
			List<Status> statusList = twitter.getUserTimeline(userId, new Paging(1,10));
			for(Status status:statusList){
				tweetList.add(format(status.getText()));
			}
			return tweetList;
		} catch(TwitterException e){
			logger.severe(e.getMessage());
			return new ArrayList<String>();
		}
	}

	private String format(String str){
		return str.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
	}
}
