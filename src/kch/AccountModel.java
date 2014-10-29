package kch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class AccountModel {
	private DBCollection coll;
	private Logger logger;

	public AccountModel(){
		coll = MongoDBUtils.getInstance().getAccountCollection();
		logger = Logger.getLogger(getClass().getName());
	}


	/**
	 * ユーザーIDが登録済みかをチェック
	 * @param userId ユーザーID
	 * @return 登録済みならtrue,そうでないならfalse
	 * @throws MongoException
	 */
	public boolean isRegistered(String userId) throws MongoException{
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
	public void registerAccount(String userId){
		logger.info("AccountModel.registerAccount");
		coll.insert(new BasicDBObject("userId",userId));
		coll.insert(new BasicDBObject("score","0"));
	}

	/**
	 * 取得したスコアをDBに登録する．
	 */
	public void registerScore(String userId,int score){
		logger.info("AccountModel.registerScore");
		this.isRegistered(userId);
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);
		object.put("score", score);
		coll.save(object);
	}

	/**
	 * DBからスコアを取得する．
	 */
	public int getScore(String userId) throws MongoException{
		logger.info("AccountModel.getScore");
		try{
			this.isRegistered(userId);
		}
		catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);
		return  (int)object.get("totalScore");
	}

	public void setScore(String userId,int totalScore,List<Integer> scoreList) throws MongoException{
		logger.info("AccountModel.setScore");

		DBObject qUser = new BasicDBObject("userId",userId);
		DBObject qScore = new BasicDBObject("$set",new BasicDBObject("score",scoreList.toArray()));
		DBObject qTotal = new BasicDBObject("$set",new BasicDBObject("totalScore",totalScore));

		coll.update(qUser, qScore);
		coll.update(qUser, qTotal);
	}

	/**
	 * 更新するユーザー一覧を取得する．
	 * <ol>
	 * <li>更新時刻がもっとも若いユーザーをcount個取得する．</li>
	 * <li>登録済みユーザーがcount個以下なら全部取得する．</li>
	 * <li>取得したユーザーリストを返す．</li>
	 * </ol>
	 * @param count 取得する数
	 * @return 更新するユーザーのリスト
	 */
	public List<DBObject> getOldList(int count){
		logger.info("AccountModel.getOldList");
		List<DBObject> oldList = new ArrayList<DBObject>();

		DBCursor cursor = coll.find().sort(new BasicDBObject("date",1));
		int i=0;
		while(cursor.hasNext()&&i<count){
			oldList.add(cursor.next());
			i++;
		}

		return oldList;
	}

	/**
	 * DBに新しいユーザーのキューを登録する．
	 * <ol>
	 * <li>スコアを処理していないユーザーでもっとも最後に登録された時刻を取得する．</li>
	 * <li>取得した時刻に1秒を追加したものを追加するユーザーの時刻に設定する．</li>
	 * <li>登録するユーザーをDBに登録する．</li>
	 * </ol>
	 * @param userId 登録するユーザー
	 */
	public void insertDate(String userId){
		logger.info("AccountModel.insertDate");
		DBObject query = new BasicDBObject("date",new BasicDBObject("$lt",new Date(10000000)));
		DBCursor cursor = coll.find(query).sort(new BasicDBObject("date",-1));

		Date date;
		if(cursor.hasNext()){
			date = (Date)cursor.next().get("date");
			date.setTime(date.getTime()+1000);
		} else {
			date = new Date(0);
		}

		DBObject newUser = new BasicDBObject();
		newUser.put("userId", userId);
		newUser.put("date",date);
	}

	/**
	 * ユーザーの更新時間を更新する．
	 * <ol>
	 * <li>指定したユーザーの時刻を指定したものに更新する．</li>
	 * </ol>
	 * @param userId 更新するユーザー
	 * @param date 更新する時間
	 */
	public void updateDate(String userId,Date date){
		logger.info("AccountModel.updateDate");
		DBObject qUser = new BasicDBObject("userId",userId);
		DBObject qDate = new BasicDBObject("$set",new BasicDBObject("date",date));

		coll.update(qUser, qDate);
	}

}
