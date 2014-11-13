package kch.controller;

import static org.junit.Assert.*;
import kch.controller.SignUpController;
import kch.utils.MongoDBUtils;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class TestSignUpController {
	private DBCollection coll = MongoDBUtils.getInstance().getAccountCollection();

	/**
	 * 正常系：未登録
	 * 対象：SignUpController.registerAccount
	 * 条件：データベースを空にしてからID:ossansC3を登録してみる
	 * 期待値：0(正常終了)が返る
	 */
	@Test
	public void testRegisterAccount01() throws Exception{
		coll.drop();
		SignUpController sua = new SignUpController();
		int expected = 0;
		int actual = sua.execute("ossansC3");
		assertEquals(expected,actual);
	}

	/**
	 * 正常系：DBに登録済み
	 * 対象：SignUpController.registerAccount
	 * 条件：データベースを空にしてからID:ossansC3を登録し，再度登録する
	 * 期待値：1(登録済み)が返る
	 */
	@Test
	public void testRegisterAccount02() throws Exception{
		coll.drop();
		coll.insert(new BasicDBObject("userId","ossansC3"));
		SignUpController sua = new SignUpController();
		int expected = 1;
		int actual = sua.execute("ossansC3");
		assertEquals(expected,actual);
	}

	/**
	 * 正常系：Twitterに未登録
	 * 対象：SignUpController.registerAccount
	 * 条件：データベースを空にしてからID:daffdafdaafdafdafadafdaffdafを登録し，再度登録する
	 * 期待値：2(Twitterに未登録)が返る
	 */
	@Test
	public void testRegisterAccount03() throws Exception{
		coll.drop();
		SignUpController sua = new SignUpController();
		int expected = 2;
		int actual = sua.execute("daffdafdaafdafdafadafdaffdaf");
		assertEquals(expected,actual);
	}

}
