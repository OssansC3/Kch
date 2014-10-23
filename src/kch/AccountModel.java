package kch;

import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class AccountModel {
	private DBCollection coll;
	private Logger logger;
	private String userId;

	public AccountModel(String userId){
		coll = MongoDBUtils.getInstance().getAccountCollection();
		logger = Logger.getLogger(getClass().getName());
		this.userId = userId;
	}


	/**
	 * ユーザーIDが登録済みかをチェック
	 * @param userId ユーザーID
	 * @return 登録済みならtrue,そうでないならfalse
	 * @throws MongoException
	 */
	public boolean isRegistered() throws MongoException{
		logger.info("AccountModel.isRegistered");
		DBObject query = new BasicDBObject("userId",userId);

		try{
			if(coll.findOne(query) == null) return false;
		} catch(MongoException e){
			logger.severe(e.getMessage());
			throw e;
		}
		return true;
	}

	/**
	 * 取得したユーザーIDをDBに登録する．
	 */
	public void registerAccount(){
		logger.info("AccountModel.registerAccount");
		coll.insert(new BasicDBObject("userId",userId));
	}

	/**
	 * 取得したスコアをDBに登録する．
	 */
	public void registerScore(int score){
		logger.info("AccountModel.registerScore");
		this.isRegistered();
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);
		object.put("score", score);
		
	}

	/**
	 * DBからスコアを取得する．
	 */
	public int getScore(){
		logger.info("AccountModel.getScore");
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);
		return Integer.parseInt(object.get("score").toString());
	}
}
