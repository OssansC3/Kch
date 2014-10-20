package kch;

import java.util.List;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * 取得したツイートの感情値をデータベースに登録する．
 *
 * @author コワリョワタロワ
 *
 */
public class PageGenController {
	private DBCollection coll;
	private Logger logger;

	public PageGenController(){
		coll = MongoDBUtils.getInstance().getAccountCollection();
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * ツイートのタイムラインからデータを取る．
	 * <ol>
	 * <li>ユーザーIDをサニタイズする．</li>
	 * <li>ユーザーIDからツイート情報を取得する．</li>
	 * <li>指定したユーザーが存在しない，または一度もつぶやいていない場合はその場で終了する．</li>
	 * <li></li>
	 * <li></li>
	 * <li></li>
	 * </ol>
	 *
	 * @param userId ツイートを取得するユーザー名
	 */
	public void execute(String userId){
		logger.info("PageGenController.execute");
		userId = MongoDBUtils.sanitize(userId);

		if(!isRegistered(userId)){
			logger.warning("アカウント"+userId+"は登録されていません。");
			return;
		}

		GetTweet gt = new GetTweet();
		GetScore gs = new GetScore();

		List<String> tweetList = gt.getTweet(userId);
		if(tweetList.size() == 0){
			logger.warning("ユーザー"+userId+"は一度もつぶやいていません．");
			return;
		}
		gs.getScore(userId, tweetList);
	}

	/**
	 * ユーザーIDが登録済みかをチェック
	 * @param userId ユーザーID
	 * @return 登録済みならtrue,そうでないならfalse
	 * @throws MongoException
	 */
	private boolean isRegistered(String userId) throws MongoException{
		logger.info("PageGenController.isRegistered");

		DBObject query = new BasicDBObject("userId",userId);
		try{
			if(coll.findOne(query) == null) return false;
		} catch(MongoException e){
			logger.severe(e.getMessage());
			throw e;
		}
		return true;
	}

}
