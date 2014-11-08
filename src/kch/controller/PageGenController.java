package kch.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import kch.accesser.AccessEmotion;
import kch.accesser.AccessTwitter;
import kch.model.AccountModel;
import kch.utils.Emotion;

import com.mongodb.DBObject;
/**
 * 取得したツイートの感情値をデータベースに登録する．
 *
 * @author コワリョワタロワ
 *
 */
public class PageGenController {
	private Logger logger;
	private final String PASSWORD = "tAOiBpvSPZFutAfhuobSaa1V2HY2U1nEM3vMlFXI";
	private final long JST = 32400000;

	public PageGenController(){
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * DBに登録されたアカウントいくつか取得して感情値を更新する．
	 * <ol>
	 * <li>勝手に実行されないようにパスワードを設定．”tAOiBpvSPZFutAfhuobSaa1V2HY2U1nEM3vMlFXI”</li>
	 * <li>queコレクションからアカウントデータを9個取得する．</li>
	 * <li>各アカウントについて，感情値を解析してAccountコレクションを更新する．</li>
	 * <li>queコレクション中の更新したアカウントの時刻を更新する．</li>
	 * </ol>
	 */
	public String execute(String password){
		logger.info("PageGenController.execute");
		if(!password.equals(PASSWORD)) {
			logger.warning("password is incorrect.");
			return "password is incorrect.";
		}
		AccountModel qm = new AccountModel();
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
			date.setTime(date.getTime()+JST+order);
			//更新順を保持するために1秒ずつ加算する
			order += 1000;
			qm.updateDate(userId,date);
		}

		return "done";
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
		AccountModel am = new AccountModel();
		AccessTwitter at = new AccessTwitter();
		AccessEmotion ae = new AccessEmotion();

		if(!am.isRegistered(userId)){
			logger.warning("アカウント"+userId+"は登録されていません。");
			return;
		}

		List<String> tweetList = at.getTweetList(userId);

		if(tweetList.size() == 0){
			logger.warning("ユーザー"+userId+"は一度もつぶやいていません．");
			return;
		}

		Emotion emotion = ae.getEmotion(tweetList);

		am.setScore(userId, emotion);
	}
}
