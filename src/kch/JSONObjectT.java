package kch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.xml.crypto.URIDereferencer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;


/**
 *
 * JSONを扱うためのクラス
 * setJson(URL)すれば, 指定されたURLから帰ってくるJSONオブジェクトをフィールドにセットします
 * @author 2014033
 *
 */
public class JSONObjectT {

	private int angerfear, joysad, likedislike;
	private String text;

	public JSONObjectT(){
		angerfear=0;
		joysad=0;
		likedislike=0;
		text=null;
	}

	/**
	 * JSONがもらえるAPIのURLを渡すとJSONオブジェクトにして返してくれるメソッド
	 * 参考URL:http://nanairo.info/2013/04/java%E3%81%A7json/
	 * @param url
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public void setJson(String url) throws UnsupportedEncodingException, IOException {
		URL apiUrl=new URL(url);

		//webから取得していく
		String line,json="";
		BufferedReader reader = new BufferedReader(new InputStreamReader(apiUrl.openStream(), "UTF-8"));
		while((line = reader.readLine()) != null){
			json+=line;
		}
		reader.close();


		// JsonFactoryの生成
		JsonFactory factory = new JsonFactory();
		// JsonParserの取得
		@SuppressWarnings("deprecation")
		JsonParser parser = factory.createJsonParser(json);

		//JSONのパース処理
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String name = parser.getCurrentName();
			if(name != null) {
				parser.nextToken();
				if(name.equals("Angerfear")) {
					//名前
					this.setAngerFear(Integer.parseInt(parser.getText()));
				}else if(name.equals("joysad")) {
					//年齢
					this.setJoySad(Integer.parseInt(parser.getText()));
				}else if(name.equals("likedislike")) {
					//出身地
					this.setLikeDislike(Integer.parseInt(parser.getText()));
				}else if(name.equals("analyzed_text")) {
					//詳細情報
					this.setText(parser.getText());
				} else {
					//想定外のものは無視して次へ
					parser.skipChildren();
				}
			}
		}
	}

	public int getAngerFear() {
		return angerfear;
	}

	private void setAngerFear(int angerfear) {
		this.angerfear = angerfear;
	}

	public int getJoySad() {
		return joysad;
	}

	private void setJoySad(int joysad) {
		this.joysad = joysad;
	}

	public int getLikeDislike() {
		return likedislike;
	}

	private void setLikeDislike(int likedislike) {
		this.likedislike = likedislike;
	}


	/**
	 * テキストは取得時ではUTF-8でエンコードされているので、Getter中でデコード処理を行う
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getText() throws UnsupportedEncodingException {
		return URLDecoder.decode(text, "UTF-8");
	}

	private void setText(String text) {
		this.text = text;
	}
}

