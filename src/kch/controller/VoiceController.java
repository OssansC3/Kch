package kch.controller;

import kch.accesser.AccessVoice;

public class VoiceController {
	public void voiceOver1() throws Exception{
		AccessVoice av = new AccessVoice();
		av.testSay();
	}
}
