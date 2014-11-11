package kch.accesser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import kch.utils.MongoDBUtils;
import kch.utils.TwitterUtils;
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
public class AccessTwitter {
	private Twitter twitter;
	private Logger logger;

	public AccessTwitter() {
		twitter = TwitterUtils.getInstance().getTwitterInstance();
		logger = Logger.getLogger(getClass().getName());
	}
	/**
	 * ツイート内容を取得する．<br/>
	 * 取得した内容はリストにして返す．<br/>
	 * アカウントが登録されていない，または指定したアカウントが一度もつぶやいていない場合は空リストを返す．
	 *
	 * @param アドレス
	 * @return ツイート内容のリスト．例外の場合は空リスト．
	 */
	public List<String> getTweetList(String userId) {
		logger.info("AccessTwitter.getTweetList:"+userId);
		userId = MongoDBUtils.sanitize(userId);

		List<String> tweetList = new ArrayList<String>();
		try{
			List<Status> statusList = twitter.getUserTimeline(userId, new Paging(1,10));
			for(Status status:statusList){
				String str = status.getText();
				StringTokenizer sta = new StringTokenizer(str, " ");
				StringBuilder sb = new StringBuilder();
				while(sta.hasMoreTokens()) {
					String next = sta.nextToken();
					next = cut(next,"#");
					next = cut(next,"@");
					next = cut(next,"http");
					next = cut(next,"RT");
					sb.append(next);
				}
				tweetList.add(TwitterUtils.sanitize(sb.toString()));
			}
			logger.info("AccessTwitter.GET:"+tweetList.toString());
			return tweetList;
		} catch(TwitterException e){
			logger.severe(e.getMessage());
			return new ArrayList<String>();
		}
	}

	public Date getTLdate(String userId){
		logger.info("AccessTwitter.getTLdate:"+userId);
		userId = MongoDBUtils.sanitize(userId);

		try{
			List<Status> statusList = twitter.getUserTimeline(userId, new Paging(1,1));
			if(statusList.isEmpty()) return new Date();
			else return statusList.get(0).getCreatedAt();
		} catch(TwitterException e){
			logger.severe(e.getMessage());
			return new Date();
		}

	}

	private String cut(String str,String tar){
		int remove = str.indexOf(tar);
		if(remove == -1) return str;
		else if(remove == 0) return "";
		else return str.substring(0, remove);
	}

}
