package kch.controller;

import java.util.logging.Logger;

import kch.rest.GetKchService;
import kch.utils.MongoDBUtils;
import com.mongodb.MongoException;

public class GetKchController {
	//暫定的に画像パスを設定
	private String ERROR_IMAGE_URI="error.jpg";
	private String GREEN_IMAGE_URI="green.jpg";
	private String YELLOW_IMAGE_URI="yellow.jpg";
	private String RED_IMAGE_URI="red.jpg";
	private Logger logger;
	private GetKchService rest;

	public GetKchController() {
		logger = Logger.getLogger(getClass().getName());
		rest = new GetKchService();
	}

	public String executeImage(String userId) throws Exception{
		logger.info("GetKchController.executeImage");
		if(userId==null){
			return "null";
		}
		userId = MongoDBUtils.sanitize(userId);
		if(!rest.isRegistered(userId)){
			return ERROR_IMAGE_URI;
		}
		int score = 0;
		try{
			score = rest.getTotalScore(userId);
		}
		catch(MongoException e){
			return RED_IMAGE_URI;
		}

		//scoreから3段階評価への対応をとり、URIを投げる
		//現状適当
		if(score > 70){
			return GREEN_IMAGE_URI;
		}
		else if (score > 30){
			return YELLOW_IMAGE_URI;
		}
		else{
			return RED_IMAGE_URI;
		}
	}

	public Object[][] executeChart(String userId){
		logger.info("GetKchController.executeChart");
		userId = MongoDBUtils.sanitize(userId);

		int[] likeList = rest.getLikeList(userId);
		int[] joyList = rest.getJoyList(userId);
		int[] angerList = rest.getAngerList(userId);
		Object[][] emotionArray = new Object[likeList.length][3];
		for(int i=0;i<likeList.length;i++){
			Object[] emotion = new Object[]{likeList[i],joyList[i],angerList[i]};
			emotionArray[i] = emotion;
		}

		return emotionArray;
	}

	public int executeGauge(String userId){
		logger.info("GetKchController.executeGauge");
		userId = MongoDBUtils.sanitize(userId);

		if(userId==null){
			return 0;
		}
		userId = MongoDBUtils.sanitize(userId);
		if(!rest.isRegistered(userId)){
			return 0;
		}
		int score = 0;
		try{
			score = rest.getTotalScore(userId);
		}
		catch(MongoException e){
			return 0;
		}

		return score;
	}
}
