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

	public GetKchController() {
		logger = Logger.getLogger(getClass().getName());
	}

	public String execute(String userId) throws Exception{
		logger.info("GetKchController.execute");
		if(userId==null){
			return "null";
		}
		userId = MongoDBUtils.sanitize(userId);
		GetKchService rest = new GetKchService();
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


}
