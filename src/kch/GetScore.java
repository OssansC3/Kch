package kch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import kch.model.AccountModel;
import kch.utils.MongoDBUtils;

/**
 * 取得したツイートを感情解析APIに投げてスコアを取得，
 * 取得したスコアをデータベースに登録する．
 * @author r-kou, 2014033
 *
 */

public class GetScore {
	private Logger logger;
	private String apikey= "ADFAAD4575EAF50C9A4B19708C8C1BAD27170112"; //感情解析APIを使用するためのAPIKEY

	public GetScore() {
		logger = Logger.getLogger(getClass().getName());
	}

	public int getScore(String userId,List<String> tweetList) throws UnsupportedEncodingException, IOException{
		logger.info("GetScore.getScore");
		AccountModel am = new AccountModel();
		userId = MongoDBUtils.sanitize(userId);

		List<Integer> scoreList= new ArrayList<Integer>();
		for(String tweet:tweetList){
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

		am.setScore(userId,total_score,scoreList);

		return total_score;
	}
}
