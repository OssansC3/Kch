package kch.utils;

import java.util.ArrayList;
import java.util.List;

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

	private List<Integer> getScoreList(List<Integer> likeList, List<Integer> joyList, List<Integer> angerList){
		List<Integer> scoreList = new ArrayList<Integer>();
		for(int i=0;i<size;i++){
			scoreList.add(likeList.get(i)+joyList.get(i)-angerList.get(i));
		}
		return scoreList;
	}

	private int getTotalScore(List<Integer> scoreList){
		int totalScore = 0;
		for(int score:scoreList){
			totalScore += score;
		}
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
