package kch.controller;

import java.util.logging.Logger;

import kch.accesser.AccessTwitter;
import kch.model.AccountModel;
import kch.utils.MongoDBUtils;
import twitter4j.TwitterException;

import com.mongodb.MongoException;

/**
 * アカウント情報を取得，保存する．
 *
 * @author 2014004
 *
 */
public class SignUpController {
	private Logger logger;

	public SignUpController() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * 新規ユーザーを登録する．</br>
	 * <ol>
	 * <li>入力されたユーザーが既に登録済みかを確認し，登録済みなら1を返す．</li>
	 * <li>入力されたユーザーIDがtwitterに存在するかを確認し，存在しないなら2を返す．</li>
	 * <li>MongoExceptionが発生したら3を返す．</li>
	 * </ol>
	 * @param userId 登録するユーザーID
	 * @return 終了状態，0は正常終了，1はDBｎｉ登録済み,2はツイッターに存在しない，3はMongoDBのエラー
	 * @throws Exception
	 */
	public int execute(String userId) throws Exception{
		logger.info("SignUpController.execute");
		userId = MongoDBUtils.sanitize(userId);

		AccountModel am = new AccountModel();

		if(am.isRegistered(userId)){
			logger.warning("SignUpController.registerAccount:"+userId+" has already registered.");
			return 1;
		}

		if(isExist(userId)){
			logger.warning("SignUpController.registerAccount:"+userId+" doesn't exist on Twitter.");
			return 2;
		}

		String userName = getUserName(userId);

		try{
			am.registerAccount(userId,userName);
			return 0;
		} catch(MongoException e){
			logger.severe(e.getMessage());
			return 3;
		}
	}

	/**
	 * ユーザーIDがTwitterに存在するかをチェック
	 * @param userId ユーザーID
	 * @return ユーザーが存在するならtrue,存在しないならfalse
	 * @throws TwitterException
	 */
	private boolean isExist(String userId) throws TwitterException{
		AccessTwitter at = new AccessTwitter();
		return at.isExist(userId);
	}

	/**
	 * ユーザーIDから表示名を取得
	 * @param userId ユーザーID
	 * @return ユーザーの名前
	 * @throws TwitterException
	 */
	private String getUserName(String userId) throws TwitterException{
		AccessTwitter at = new AccessTwitter();
		return at.getUserName(userId);
	}
}
