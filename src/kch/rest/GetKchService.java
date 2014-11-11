package kch.rest;

import java.util.List;

import kch.model.AccountModel;

public class GetKchService {
	private AccountModel am;

	public GetKchService(){
		this.am = new AccountModel();
	}

	public int[] getScoreList(String userId){
		return toIntArray(am.getScoreList(userId));
	}

	public int[] getLikeList(String userId){
		return toIntArray(am.getLikeList(userId));
	}

	public int[] getJoyList(String userId){
		return toIntArray(am.getJoyList(userId));
	}

	public int[] getAngerList(String userId){
		return toIntArray(am.getAngerList(userId));
	}

	public int getTotalScore(String userId){
		return am.getTotalScore(userId);
	}

	public boolean isRegistered(String userId) {
		return am.isRegistered(userId);
	}

	public String[] getUserData(String userId){
		return toStringArray(am.getUserData(userId));
	}

	public String[] getUserList(){
		return toStringArray(am.getUserList());
	}

	private int[] toIntArray(List<Integer> list){
		int[] array = new int[list.size()];
		for (int i=0; i<list.size(); i++) {
		  array[i] = list.get(i); // Integer
		}
		return array;
	}

	private String[] toStringArray(List<String> list){
		String[] array = new String[list.size()];
		for (int i=0; i<list.size(); i++) {
		  array[i] = list.get(i); // String
		}
		return array;
	}

}

