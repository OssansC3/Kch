package kch;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class TestSignUpAccount {
	private DBCollection coll = MongoDBUtils.getInstance().getAccountCollection();

	/**
	 * 正常系：未登録
	 * 対象：SignUpAccount.registerAccount
	 * 条件：データベースを空にしてからID:user00を登録してみる
	 * 期待値：0(正常終了)が返る
	 */
	@Test
	public void testRegisterAccount01() throws Exception{
		coll.drop();
		SignUpAccount sua = new SignUpAccount();
		int expected = 0;
		int actual = sua.registerAccount("user00");
		assertEquals(expected,actual);
	}

	/**
	 * 正常系：登録済み
	 * 対象：SignUpAccount.registerAccount
	 * 条件：データベースを空にしてからID:user00を登録し，再度登録する
	 * 期待値：1(登録済み)が返る
	 */
	@Test
	public void testRegisterAccount02() throws Exception{
		coll.drop();
		coll.insert(new BasicDBObject("userId","user00"));
		SignUpAccount sua = new SignUpAccount();
		int expected = 1;
		int actual = sua.registerAccount("user00");
		assertEquals(expected,actual);
	}

}
