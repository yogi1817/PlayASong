package com.sample.speech.handler;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class PlayASongSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler{

	private static final Set<String> supportedApplicationIds;
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.13551331-70f7-4cb6-b7ce-42af92f1c5b0");
    }
   
	
	public PlayASongSpeechletRequestStreamHandler() {
		super(new PlayASongSpeechlet(), supportedApplicationIds);
	}
}
