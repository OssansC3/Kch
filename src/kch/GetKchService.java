package kch;

import com.mongodb.MongoException;

public class GetKchService {
	//暫定的に画像パスを設定
	private String ERROR_IMAGE_URI="/images/error.jpg";
	private String GREEN_IMAGE_URI="/images/green.jpg";
	private String YELLOW_IMAGE_URI="/images/yellow.jpg";
	private String RED_IMAGE_URI="/images/red.jpg";

	public GetKchService(){
	}

	public String GetKchImageURI(String userId){
		if(userId==null){
			return "null";
		}
		userId = MongoDBUtils.sanitize(userId);
		AccountModel account = new AccountModel();
		if(!account.isRegistered(userId)){
			return ERROR_IMAGE_URI;
		}
		int score = 0;
		try{
			score = account.getScore(userId);
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

