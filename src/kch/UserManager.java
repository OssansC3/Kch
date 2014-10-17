package kch;

import java.util.ArrayList;
import java.util.List;

/**
 * ユーザアカウントを管理するロジッククラス
 * デモなので中身は適当 （書式/重複チェックは省略）
 *
 * @author shin
 *
 */
public class UserManager {
	private List<User> users;

	public UserManager() {
		users = new ArrayList<User>();
	}

	public boolean createUser(String name, String pass) {
		User user = new User(name, pass);
		users.add(user);

		// デモなので全部true
		return true;
	}

	public List<User> getAllUsers() {
		return users;
	}
}
