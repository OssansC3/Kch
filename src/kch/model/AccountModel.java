package kch.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import kch.utils.Emotion;
import kch.utils.MongoDBUtils;

import com.mongodb.BasicDBList;
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
	 * @param userId 登録するユーザー
	 */
	public void registerAccount(String userId){
		logger.info("AccountModel.registerAccount");
		DBObject user = new BasicDBObject();
		user.put("userId",userId);
		user.put("like",newScore());
		user.put("joy",newScore());
		user.put("anger",newScore());
		user.put("score",newScore());
		user.put("totalScore",0);
		user.put("date",newDate());
		coll.insert(user);
	}

	/**
	 * 取得したスコアでDBを更新する．
	 *
	 * @param userId 更新するユーザー
	 * @param emotion 感情スコア
	 * @throws MongoException
	 */
	public void setScore(String userId,Emotion emotion) throws MongoException{
		logger.info("AccountModel.setScore");

		DBObject qUser = new BasicDBObject("userId",userId);
		DBObject qLike = new BasicDBObject("$set",new BasicDBObject("like",emotion.getLikeList().toArray()));
		DBObject qJoy = new BasicDBObject("$set",new BasicDBObject("joy",emotion.getJoyList().toArray()));
		DBObject qAnger = new BasicDBObject("$set",new BasicDBObject("anger",emotion.getAngerList().toArray()));
		DBObject qScore = new BasicDBObject("$set",new BasicDBObject("score",emotion.getScoreList().toArray()));
		DBObject qTotal = new BasicDBObject("$set",new BasicDBObject("totalScore",emotion.getTotalScore()));

		coll.update(qUser, qLike);
		coll.update(qUser, qJoy);
		coll.update(qUser, qAnger);
		coll.update(qUser, qScore);
		coll.update(qUser, qTotal);
	}

	/**
	 * 更新するユーザー一覧を取得する．
	 * <ol>
	 * <li>ユーザーの一覧を更新時間が若い順に取得する．</li>
	 * <li>取得したユーザーリストを返す．</li>
	 * </ol>
	 * @return 更新するユーザーのリスト
	 */
	public List<DBObject> getOldList(){
		logger.info("AccountModel.getOldList");
		List<DBObject> oldList = new ArrayList<DBObject>();

		DBCursor cursor = coll.find().sort(new BasicDBObject("date",1));
		while(cursor.hasNext()){
			oldList.add(cursor.next());
		}

		return oldList;
	}

	/**
	 * ユーザー一覧を取得する．
	 * <ol>
	 * <li>登録済みのユーザーを全て取得する．</li>
	 * <li>取得したユーザーリストを返す．</li>
	 * </ol>
	 * @return 更新するユーザーのリスト
	 */
	public List<String> getUserList(){
		logger.info("AccountModel.getUserList");
		List<String> userList = new ArrayList<String>();

		DBCursor cursor = coll.find().sort(new BasicDBObject("userId",1));
		while(cursor.hasNext()){
			userList.add((String)cursor.next().get("userId"));
		}

		return userList;
	}

	public List<String> getUserData(String userId){
		logger.info("AccountModel.getUserData");
		List<String> dataList = new ArrayList<String>();
		StringBuilder sb;
		BasicDBList list;

		try{
			if(!this.isRegistered(userId)) return dataList;
		} catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);

		dataList.add((String)"userId:"+object.get("userId"));

		sb = new StringBuilder("like:");
		list = (BasicDBList)object.get("like");
		for(int i=0;i<list.size();i++) sb.append(list.get(i)+",");
		dataList.add(sb.substring(0, sb.length()-2).toString());

		sb = new StringBuilder("joy:");
		list = (BasicDBList)object.get("joy");
		for(int i=0;i<list.size();i++) sb.append(list.get(i)+",");
		dataList.add(sb.substring(0, sb.length()-2).toString());

		sb = new StringBuilder("anger:");
		list = (BasicDBList)object.get("anger");
		for(int i=0;i<list.size();i++) sb.append(list.get(i)+",");
		dataList.add(sb.substring(0, sb.length()-2).toString());

		sb = new StringBuilder("score:");
		list = (BasicDBList)object.get("score");
		for(int i=0;i<list.size();i++) sb.append(list.get(i)+",");
		dataList.add(sb.substring(0, sb.length()-2).toString());

		dataList.add((String)"totalScore:"+object.get("totalScore"));

		dataList.add((String)"date:"+object.get("date"));

		return dataList;
	}

	/**
	 * DBに新しく登録するユーザーのキューを設定する．
	 * <ol>
	 * <li>スコアを処理していないユーザーでもっとも最後に登録された時刻を取得する．</li>
	 * <li>取得した時刻に1秒を追加したものを追加するユーザーの時刻に設定する．</li>
	 * <li>未処理ユーザーが存在しない場合はDate(0)に設定する．</li>
	 * <li>設定した時刻を返す．</li>
	 * </ol>
	 * @param userId 登録するユーザー
	 * @return 新しいデート
	 */
	private Date newDate(){
		DBObject query = new BasicDBObject("date",new BasicDBObject("$lt",new Date(10000000)));
		DBCursor cursor = coll.find(query).sort(new BasicDBObject("date",-1));

		Date date;
		if(cursor.hasNext()){
			date = (Date)cursor.next().get("date");
			date.setTime(date.getTime()+1000);
		} else {
			date = new Date(0);
		}

		return date;
	}

	/**
	 * 0だけ入ったリストを返す．
	 * 正直作るほどのメソッドではないけど一応追加
	 * @return 0が一つだけ入った配列
	 */
	private BasicDBList newScore(){
		BasicDBList list = new BasicDBList();
		list.add(0);
		return list;
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

	/**
	 * ユーザーの最新のＴＬの時間を更新する．
	 * <ol>
	 * <li>指定したユーザーの時刻を指定したものに更新する．</li>
	 * </ol>
	 * @param userId 更新するユーザー
	 * @param date 更新する時間
	 */
	public void updateTLDate(String userId,Date date){
		logger.info("AccountModel.updateDate");
		DBObject qUser = new BasicDBObject("userId",userId);
		DBObject qDate = new BasicDBObject("$set",new BasicDBObject("TLdate",date));

		coll.update(qUser, qDate);
	}

	/**
	 * DBから合計スコアを取得する．
	 *
	 * @param userId 取得するユーザー
	 * @return 合計スコア
	 */
	public int getTotalScore(String userId) throws MongoException{
		logger.info("AccountModel.getTotalScore");
		try{
			if(!this.isRegistered(userId)) return 0;
		}
		catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);
		return  (int)object.get("totalScore");
	}

	/**
	 * DBからスコアを取得する．
	 *
	 * @param userId 取得するユーザー
	 * @return スコア配列
	 */
	public List<Integer> getScoreList(String userId) throws MongoException{
		logger.info("AccountModel.getScoreList");
		List<Integer> scoreList = new ArrayList<Integer>();
		try{
			if(!this.isRegistered(userId)) return scoreList;
		} catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);

		BasicDBList list = (BasicDBList)object.get("score");
		for(int i=0;i<list.size();i++) scoreList.add((int)list.get(i));
		return scoreList;
	}

	/**
	 * DBからlikeスコアを取得する．
	 *
	 * @param userId 取得するユーザー
	 * @return likeスコア配列
	 */
	public List<Integer> getLikeList(String userId) throws MongoException{
		logger.info("AccountModel.getLikeList");
		List<Integer> scoreList = new ArrayList<Integer>();
		try{
			if(!this.isRegistered(userId)) return scoreList;
		} catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);

		BasicDBList list = (BasicDBList)object.get("like");
		for(int i=0;i<list.size();i++) scoreList.add((int)list.get(i));
		return scoreList;
	}

	/**
	 * DBからjoyスコアを取得する．
	 *
	 * @param userId 取得するユーザー
	 * @return joyスコア配列
	 */
	public List<Integer> getJoyList(String userId) throws MongoException{
		logger.info("AccountModel.getJoyList");
		List<Integer> scoreList = new ArrayList<Integer>();
		try{
			if(!this.isRegistered(userId)) return scoreList;
		} catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);

		BasicDBList list = (BasicDBList)object.get("joy");
		for(int i=0;i<list.size();i++) scoreList.add((int)list.get(i));
		return scoreList;
	}

	/**
	 * DBからangerスコアを取得する．
	 *
	 * @param userId 取得するユーザー
	 * @return angerスコア配列
	 */
	public List<Integer> getAngerList(String userId) throws MongoException{
		logger.info("AccountModel.getAngerList");
		List<Integer> scoreList = new ArrayList<Integer>();
		try{
			if(!this.isRegistered(userId)) return scoreList;
		} catch(MongoException e){
			throw e;
		}
		DBObject query = new BasicDBObject("userId",userId);
		DBObject object = coll.findOne(query);

		BasicDBList list = (BasicDBList)object.get("anger");
		for(int i=0;i<list.size();i++) scoreList.add((int)list.get(i));
		return scoreList;
	}

}
