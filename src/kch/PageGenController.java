package kch;

import java.util.List;
import java.util.logging.Logger;
/**
 * 取得したツイートの感情値をデータベースに登録する．
 *
 * @author コワリョワタロワ
 *
 */
public class PageGenController {
	private Logger logger;

	public PageGenController(){
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
		AccountModel am = new AccountModel(userId);
		GetTweet gt = new GetTweet();
		GetScore gs = new GetScore();

		if(!am.isRegistered()){
			logger.warning("アカウント"+userId+"は登録されていません。");
			return;
		}

		List<String> tweetList = gt.getTweet(userId);

		if(tweetList.size() == 0){
			logger.warning("ユーザー"+userId+"は一度もつぶやいていません．");
			return;
		}

		gs.getScore(userId, tweetList);
	}
}
