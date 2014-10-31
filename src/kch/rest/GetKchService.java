package kch.rest;

import java.util.List;

import kch.model.AccountModel;

public class GetKchService {
	private AccountModel am;

	public GetKchService(){
		this.am = new AccountModel();
	}

	public int[] getScoreList(String userId){
		return toArray(am.getScoreList(userId));
	}

	public int[] getLikeList(String userId){
		return toArray(am.getLikeList(userId));
	}

	public int[] getJoyList(String userId){
		return toArray(am.getJoyList(userId));
	}

	public int[] getAngerList(String userId){
		return toArray(am.getAngerList(userId));
	}

	public int getTotalScore(String userId){
		return am.getTotalScore(userId);
	}

	private int[] toArray(List<Integer> list){
		int[] array = new int[list.size()];
		for (int i=0; i<list.size(); i++) {
		  array[i] = list.get(i); // Integer
		}
		return array;
	}

}

