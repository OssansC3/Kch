package kch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.DBCollection;

/**
 * 取得したツイートを感情解析APIに投げてスコアを取得，
 * 取得したスコアをデータベースに登録する．
 * @author r-kou, 2014033
 *
 */

public class GetScore {
	private DBCollection coll;
	private Logger logger;
	private String apikey= "ADFAAD4575EAF50C9A4B19708C8C1BAD27170112"; //感情解析APIを使用するためのAPIKEY

	public GetScore() {
		coll = MongoDBUtils.getInstance().getAccountCollection();
		logger = Logger.getLogger(getClass().getName());
	}

	public int getScore(String userId,List<String> tweetList) throws UnsupportedEncodingException, IOException{
		logger.info("GetScore.getScore");
		userId = MongoDBUtils.sanitize(userId);

		List<Integer> scoreList= new ArrayList<Integer>();
		for(String tweet:tweetList){
			//TODO:感情解析APIに投げる
			//現状はとりあえず感情1を投げる

			tweet = tweet+"";//警告消し

			//TweetListをUTF-8エンコード
			String encText = URLEncoder.encode(tweet,"UTF-8");

			//WebAPIのURLを文字列に設定
			String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText;

			//URLからjsonを取得
			JSONObjectT jso = new JSONObjectT();
			jso.setJson(url);

			//感情解析で取得した値は、暫定的に"好き嫌い"のみを反映
			scoreList.add(jso.getLikeDislike());
		}

		int total_score = 0;
		for(int score:scoreList){
			total_score += score;
		}

		/*
		DBObject updateScore = new BasicDBObject("score",scoreList.toArray());
		DBObject update = new BasicDBObject("$set",updateScore);
		DBObject query = new BasicDBObject("userId",userId);
		coll.update(query, update);
		*/

		//合計値を返す
		return total_score;
	}
}
