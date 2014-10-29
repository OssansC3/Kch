package kch;

import java.util.List;
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
		coll.insert(new BasicDBObject("score","0"));
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
		coll.save(object);
	}

	/**
	 * DBからスコアを取得する．
	 */
	public int getScore() throws MongoException{
		logger.info("AccountModel.getScore");
		try{
			this.isRegistered();
		}
		catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);
		return  (int)object.get("score");
	}

	public void setScore(int totalScore,List<Integer> scoreList) throws MongoException{
		logger.info("AccountModel.setScore");

		DBObject qUser = new BasicDBObject("userId",userId);
		DBObject qScore = new BasicDBObject("$set",new BasicDBObject("score",scoreList.toArray()));
		DBObject qTotal = new BasicDBObject("$set",new BasicDBObject("totalScore",totalScore));

		coll.update(qUser, qScore);
		coll.update(qUser, qTotal);
	}
}
