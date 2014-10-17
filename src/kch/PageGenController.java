package kch;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * アカウント情報からツイートを取得，感情スコアを計算，HTMLを作成する． 　
 *
 * @author 2014004
 *
 */
public class PageGenController {

	public static void main(String[] argv) {
		GetTweet gt = new GetTweet();
		String str = gt.getTweet("cloud_spiral");
		System.out.println(str);
		try {
			File inFile = new File("tweet.txt");
			FileReader in = new FileReader(inFile);
			while (in.ready()) {
				System.out.print((char)in.read());
			}
			in.close();
		} catch (IOException e) {
			System.err.println("FileInputError:" + e);
		}

	}
}
