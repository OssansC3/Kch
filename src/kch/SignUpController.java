package kch;

import java.util.logging.Logger;

import twitter4j.User;
import twitter4j.Twitter;
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
	public int registerAccount(String userId) throws Exception{
		logger.info("SignUpController.registerAccount");
		userId = MongoDBUtils.sanitize(userId);

		AccountModel am = new AccountModel();

		if(am.isRegistered(userId)){
			logger.warning("SignUpController.registerAccount:"+userId+" has already registered.");
			return 1;
		}

		if(!isExistingOnTwitter(userId)){
			logger.warning("SignUpController.registerAccount:"+userId+" doesn't exist on Twitter.");
			return 2;
		}

		try{
			am.registerAccount(userId);
			am.insertDate(userId);
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
	private boolean isExistingOnTwitter(String userId) throws TwitterException{
		try{
			Twitter twitter = TwitterUtils.getInstance().getTwitterInstance();
			User user = twitter.showUser(userId);
			if(user==null) return false;
		} catch(TwitterException e){
			//コード34:存在しないならfalseを返し，それ以外ならラップして投げる
			if(e.getErrorCode()==34) return false;
			else {
				logger.severe(e.getMessage());
				throw e;
			}
		}
		return true;
	}
}
