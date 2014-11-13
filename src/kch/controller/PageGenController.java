package kch.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import twitter4j.TwitterException;
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
	private final int LIMIT = 9;
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
		AccountModel am = new AccountModel();
		AccessTwitter at = new AccessTwitter();
		List<DBObject> oldList = am.getOldList();

		//何人更新したかを計算する．
		int count = 0;
		long order = 0;

		for(DBObject old:oldList){
			//9人更新したら終了する
			if(count>=LIMIT) break;
			String userId = (String) old.get("userId");
			Date TLdate = (Date) old.get("TLdate");
			if(TLdate==null) TLdate = new Date(0);
			else TLdate.setTime(TLdate.getTime()-JST);
			Date newTLdate = at.getTLdate(userId);

			if(TLdate.before(newTLdate)){
				try {
					generate(userId);
					am.updateTLDate(userId,newTLdate);
					count++;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			Date date = new Date();
			date.setTime(date.getTime()+JST+order);
			order+=1000;
			am.updateDate(userId,date);
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
	 * @param TLdate タイムラインの最終更新日時
	 * @return 更新したかの是非
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

		String userName = "";
		try {
			userName = at.getUserName(userId);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		am.setName(userId, userName);

		Emotion emotion = ae.getEmotion(tweetList);
		am.setScore(userId, emotion);
		return;
	}

	/**
	 * TLに関係なく常に更新できるコマンド
	 * @param userId
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String force(String userId,String password) throws UnsupportedEncodingException, IOException{
		logger.info("PageGenController.force");
		if(!password.equals(PASSWORD)) {
			logger.warning("password is incorrect.");
			return "password is incorrect.";
		}

		AccountModel am = new AccountModel();
		AccessTwitter at = new AccessTwitter();

		generate(userId);

		Date newTLdate = at.getTLdate(userId);
		am.updateTLDate(userId,newTLdate);

		Date date = new Date();
		date.setTime(date.getTime()+JST);
		am.updateDate(userId,date);

		return "done";
	}
}
