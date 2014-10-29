package kch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class QueModel {
	private DBCollection coll;
	private Logger logger;

	public QueModel(){
		coll = MongoDBUtils.getInstance().getQueCollection();
		logger = Logger.getLogger(getClass().getName());
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
		logger.info("QueModel.getOldList");
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
	 * ユーザーの更新時間を更新する．
	 * <ol>
	 * <li>指定したユーザーの時刻を指定したものに更新する．</li>
	 * </ol>
	 * @param userId 更新するユーザー
	 * @param date 更新する時間
	 */
	public void updateDate(String userId,Date date){
		DBObject qUser = new BasicDBObject("userId",userId);
		DBObject qDate = new BasicDBObject("$set",new BasicDBObject("date",date));

		coll.update(qUser, qDate);
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
}
