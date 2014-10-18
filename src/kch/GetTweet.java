package kch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import twitter4j.*;

/**
 * ついーとを取得する
 *
 * @author 2014004 高　良多朗
 *
 */
public class GetTweet {
	/**
	 * ツイート内容を取得するapi
	 * @param アドレス
	 * @return ツイート内容
	 */
	public int getTweet(String userId) {
		File outFile = new File("tweet.txt");
		try{
			FileWriter out = new FileWriter(outFile);
			Twitter twitter = TwitterUtils.getInstance().getTwitterInstance();
			List<Status> statusList = twitter.getUserTimeline(userId, new Paging(1,10));
			for(Status status:statusList){
				out.write(format(status.getText())+"\n");
			}
			out.close();
			return 0;
		} catch(TwitterException e){
			System.err.println("TwitterAPIError:"+e.getMessage());
			return 1;
		} catch (IOException e) {
			System.err.println("FileOutputError:"+e.getMessage());
			return 2;
		}
	}

	private String format(String str){
		return str.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
	}
}
