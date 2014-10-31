package kch.accesser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import kch.utils.Emotion;
import kch.utils.JSONObjectT;

/**
 * 取得したツイートを感情解析APIに投げてスコアを取得，
 * 取得したスコアをデータベースに登録する．
 * @author r-kou, 2014033
 *
 */

public class AccessEmotion {
	private Logger logger;
	private String apikey= "ADFAAD4575EAF50C9A4B19708C8C1BAD27170112"; //感情解析APIを使用するためのAPIKEY

	public AccessEmotion() {
		logger = Logger.getLogger(getClass().getName());
	}

	public Emotion getEmotion(List<String> tweetList) throws UnsupportedEncodingException, IOException{
		logger.info("AccessEmotion.getEmotion");

		List<Integer> likeList= new ArrayList<Integer>();
		List<Integer> joyList= new ArrayList<Integer>();
		List<Integer> angerList= new ArrayList<Integer>();

		for(String tweet:tweetList){
			tweet = tweet+"";//警告消し

			//TweetListをUTF-8エンコード
			String encText = URLEncoder.encode(tweet,"UTF-8");

			//WebAPIのURLを文字列に設定
			String url = "http://ap.mextractr.net/ma9/emotion_analyzer?out=json&apikey="+apikey+"&text="+encText;

			//URLからjsonを取得
			JSONObjectT jso = new JSONObjectT();
			jso.setJson(url);

			likeList.add(jso.getLikeDislike());
			joyList.add(jso.getJoySad());
			angerList.add(jso.getAngerFear());
		}

		return new Emotion(likeList,joyList,angerList);
	}
}
