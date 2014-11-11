package kch.utils;

import java.util.ArrayList;
import java.util.List;
import java.math.*;

public class Emotion {
	private List<Integer> likeList;
	private List<Integer> joyList;
	private List<Integer> angerList;
	private int size;
	private List<Integer> scoreList;
	private int totalScore;

	public Emotion(List<Integer> likeList, List<Integer> joyList, List<Integer> angerList){
		this.likeList = likeList;
		this.joyList = joyList;
		this.angerList = angerList;
		this.size = likeList.size();
		this.scoreList = getScoreList(this.likeList,this.joyList,this.angerList);
		this.totalScore = getTotalScore(this.scoreList);
	}

	/**
	 * like, joy, angerの値を正規化し、0~100の値に変換し、各ツイート毎のスコアのリストを返す
	 * @param likeList
	 * @param joyList
	 * @param angerList
	 * @return
	 */
	private List<Integer> getScoreList(List<Integer> likeList, List<Integer> joyList, List<Integer> angerList){
		List<Integer> scoreList = new ArrayList<Integer>();

		//３つの感情スコアを0~100の間で正規化する
		//((like+3)+(joy+3)-|anger|*2)/12*100
		for(int i=0;i<size;i++){
			scoreList.add(
				(
					(likeList.get(i) +3)
					+(joyList.get(i) +3)
					-Math.abs(angerList.get(i)) *2
				)
				*100/12
			);
		}

		return scoreList;
	}

	/**
	 * 各ツイートのスコアのリストの平均値を返す
	 * 返り値は0~100の間になる
	 * @param scoreList
	 * @return
	 */
	private int getTotalScore(List<Integer> scoreList){
		int totalScore = 0;
		//平均値を求める
		for(int score:scoreList){
			totalScore += score;
		}
		totalScore /= scoreList.size();
		return totalScore;
	}

	public List<Integer> getLikeList() {
		return likeList;
	}

	public List<Integer> getJoyList() {
		return joyList;
	}

	public List<Integer> getAngerList() {
		return angerList;
	}

	public int getSize() {
		return size;
	}

	public List<Integer> getScoreList() {
		return scoreList;
	}

	public int getTotalScore() {
		return totalScore;
	}
}
