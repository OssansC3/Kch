package kch;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kch.utils.MongoDBUtils;

import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class TestGetScore {
	private DBCollection coll = MongoDBUtils.getInstance().getAccountCollection();

	/**
	 * 正常系
	 * 対象：GetScore.getScore
	 * 条件：初めにデータベースを初期化，"cloud_spiral"のみを登録する．<br/>
	 * ユーザーID:"cloud_spiral"を対象に，</br>
	 * ツイート内容を"味噌汁"10件として実行
	 * 期待値：スコアが登録されている．
	 */
	@Test
	public void testGetScore01() throws Exception{
		final String target = "cloud_spiral";
		final int number = 10;

		coll.drop();
		DBObject query = new BasicDBObject("userId",target);
		coll.insert(query);

		GetScore gs = new GetScore();
		List<String> list = new ArrayList<String>();
		for(int i=0;i<number;i++) list.add("味噌汁");

		gs.getScore(target,list);

		DBObject result = coll.findOne(query);
		BasicDBList actualList = (BasicDBList) result.get("score");

		int expected = number;
		int actual = actualList.size();
		assertEquals(expected,actual);
	}


}
