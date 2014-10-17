package kch;

/**
 * ユーザアカウントを表すBeanクラス （getter/setterとデフォルトコンストラクタを持つ）
 *
 * Axis2によるREST APIの返り値オブジェクトになるサンプルクラス
 *
 * @author shin
 *
 */
public class User {
	private String name;
	private String pass;

	/* デフォルトコンストラクタ （必須） */
	public User() {
		this("noname", "nopass");
	}

	public User(String name, String pass) {
		this.setName(name);
		this.setPass(pass);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
}
