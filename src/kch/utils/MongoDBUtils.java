package kch.utils;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * MongoDBを使うためのユーティリティクラス
 * 好きに使ってね！
 * @author r-kou
 *
 */
public class MongoDBUtils {
	private static Mongo m;
	private static DB db;
	private static final String dbName = "kch";
	private static final String DB_ACCOUNT_COLLECTION = "account";
	private static final String DB_QUE_COLLECTION = "que";
	private static MongoDBUtils singleton = new MongoDBUtils();

	/**
	 * シングルトンインスタンス取得
	 * @return シングルトンインスタンス
	 */
	public static MongoDBUtils getInstance(){
		return singleton;
	}

	private MongoDBUtils() {
		try {
			m = new MongoClient("localhost",27017);
			db = m.getDB(dbName);
		} catch(UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBの取得
	 * @return DBオブジェクト
	 */
	public DB getDB(){
		return db;
	}

	/**
	 * <p>
	 * HTML特殊文字のサニタイズ．</br>
	 * 危険な特殊文字を全て置換する．</br>
	 * Stringを使う場合は必ず呼ぶこと．</br>
	 * </p>
	 *
	 * @param str サニタイズ対象の文字列
	 * @return サニタイズ後の文字列
	 */
	public static String sanitize(String str){
		if (str == null) return "";
		str = str.replaceAll("&","&amp;");
		str = str.replaceAll("<","&lt;");
		str = str.replaceAll(">","&gt;");
		str = str.replaceAll("\"","&quot;");
		str = str.replaceAll("'","&#39;");
		return str;
	}

	/**
	 * Accountコレクションの取得．
	 * @return Accountコレクション
	 */
	public DBCollection getAccountCollection(){
		return db.getCollection(DB_ACCOUNT_COLLECTION);
	}

	/**
	 * Queコレクションの取得．
	 * @return Queコレクション
	 */
	public DBCollection getQueCollection(){
		return db.getCollection(DB_QUE_COLLECTION);
	}
}
