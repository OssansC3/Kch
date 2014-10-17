package kch;

import java.util.List;

/**
 * REST APIから呼び出せるサービスインタフェースクラス
 * ロジックを持たない単なるラッパークラス，Axis2専用の皮
 *
 * 当該クラス名を変更する場合はservices.xmlのL8を書き換えること
 *
 * @author shin
 *
 */
public class GetTweet {

	// ユーザアカウントを管理するクラス
	private UserManager manager;

	// コンストラクタ
	// tomcat起動時に一度だけ実行される
	public GetTweet() {
		manager = new UserManager();
	}

	/**
	 * ■ デモAPI1
	 * 足し算API
	 * 2つのint型のパラメタを受け取り，int型を返すだけ
	 *
	 * @param p1
	 * @param p2
	 * @return
	 */
	public int plus (int p1, int p2) {
		return p1 + p2;
	}

	/**
	 * ■ デモAPI2
	 * ユーザアカウントを登録するAPI
	 *
	 * @param name
	 * @param pass
	 * @return 登録の可否 （デモなので書式/重複チェックなし，全部true）
	 */
	public boolean createUser(String name, String pass) {
		return manager.createUser(name, pass);
	}

	/**
	 * ■ デモAPI3
	 * ユーザ一覧を取得するAPI
	 * オブジェクトを返すデモ
	 *
	 * @return ユーザリスト
	 */
	public List<User> getAllUsers() {
		return manager.getAllUsers();
	}
}
