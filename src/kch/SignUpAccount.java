package kch;

import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * アカウント情報を取得，保存する．
 *
 * @author 2014004
 *
 */
public class SignUpAccount {
	private DBCollection coll;
	private Logger logger;

	public SignUpAccount() {
		coll = MongoDBUtils.getInstance().getAccountCollection();
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * 新規ユーザーを登録する．</br>
	 * <ol>
	 * <li>入力されたユーザーが既に登録済みかを確認する．</li>
	 * <li>登録済みなら1を返す．</li>
	 * <li>未登録なら登録する．</li>
	 * <li>MongoExceptionが発生したら2を返す．</li>
	 * </ol>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int registerAccount(String userId) throws Exception{
		logger.info("SignUpAccount.registerAccount");

		if(!isUniqueName(userId)){
			logger.warning("SignUpAccount.registerAccount:"+userId+" has already registered.");
			return 1;
		}

		try{
			coll.insert(new BasicDBObject("userId",userId));
			return 0;
		} catch(MongoException e){
			logger.severe(e.getMessage());
			return 2;
		}
	}

	/**
	 * ユーザーIDの重複チェック
	 * @param userId ユーザーID
	 * @return 登録済みならfalse,そうでないならtrue
	 * @throws MongoException
	 */
	private boolean isUniqueName(String userId) throws MongoException{
		logger.info("SignUpAccount.isUniqueName");

		DBObject query = new BasicDBObject("userId",userId);
		try{
			if(coll.findOne(query) != null) return false;
		} catch(MongoException e){
			logger.severe(e.getMessage());
			throw e;
		}
		return true;
	}
}
