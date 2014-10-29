package kch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.DBObject;
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
	 * DBに登録されたアカウントいくつか取得して感情値を更新する．
	 * <ol>
	 * <li>queコレクションからアカウントデータを9個取得する．</li>
	 * <li>各アカウントについて，感情値を解析してAccountコレクションを更新する．</li>
	 * <li>queコレクション中の更新したアカウントの時刻を更新する．</li>
	 * </ol>
	 */
	public void execute(){
		logger.info("PageGenController.execute");
		QueModel qm = new QueModel();
		List<DBObject> oldList = qm.getOldList(9);

		//更新順を保持するための値
		long order = 0;

		for(DBObject old:oldList){
			System.out.println(old);
			String userId = (String) old.get("userId");

			try {
				generate(userId);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Date date = new Date();
			date.setTime(date.getTime()+order);
			//更新順を保持するために1秒ずつ加算する
			order += 1000;
			qm.updateDate(userId,date);
		}
	}

	/**
	 * ツイートのタイムラインからデータを取る．
	 * <ol>
	 * <li>ユーザーIDをサニタイズする．</li>
	 * <li>ユーザーIDからツイート情報を取得する．</li>
	 * <li>指定したユーザーが存在しない，または一度もつぶやいていない場合はその場で終了する．</li>
	 * <li>取得したユーザーについて，感情値を解析する．</li>
	 * <li>取得した感情値をDBに登録する．</li>
	 * <li></li>
	 * </ol>
	 *
	 * @param userId ツイートを取得するユーザー名
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void generate(String userId) throws UnsupportedEncodingException, IOException{
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
