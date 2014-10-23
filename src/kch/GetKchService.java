package kch;

import com.mongodb.MongoException;

public class GetKchService {
	private String ERROR_IMAGE_URI="error";
	private String GREEN_IMAGE_URI="green";
	private String YELLOW_IMAGE_URI="yellow";
	private String RED_IMAGE_URI="red";

	public GetKchService(){
	}

	public String GetKchImageURI(String userId){
		if(userId==null){
			return "nullらしい";
		}
		AccountModel account = new AccountModel(userId);
		if(!account.isRegistered()){
			return ERROR_IMAGE_URI;
		}
		int score = 0;
		try{
			score = account.getScore();
		}
		catch(MongoException e){
			return RED_IMAGE_URI;
		}

		//scoreから3段階評価への対応をとり、URIを投げる
		//現状適当
		if(score > 20){
			return GREEN_IMAGE_URI;
		}

		return ERROR_IMAGE_URI;
	}

}

