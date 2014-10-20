package kch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 取得したツイートを感情解析APIに投げてスコアを取得，
 * 取得したスコアをデータベースに登録する．
 *
 * @author r-kou
 *
 */
public class GetScore {
	private DBCollection coll;
	private Logger logger;

	public GetScore() {
		coll = MongoDBUtils.getInstance().getAccountCollection();
		logger = Logger.getLogger(getClass().getName());
	}

	public void getScore(String userId,List<String> tweetList){
		logger.info("GetScore.getScore");
		List<Integer> scoreList = new ArrayList<Integer>();

		for(String tweet:tweetList){
			//TODO:感情解析APIに投げる
			//現状はとりあえず感情1を投げる
			tweet = tweet+"";//警告消し
			scoreList.add(1);
		}
		DBObject updateScore = new BasicDBObject("score",scoreList.toArray());
		DBObject update = new BasicDBObject("$set",updateScore);
		DBObject query = new BasicDBObject("userId",userId);
		coll.update(query, update);
	}

}
