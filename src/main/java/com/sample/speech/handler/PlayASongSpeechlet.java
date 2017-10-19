package com.sample.speech.handler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.json.SpeechletResponseEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioItem;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayer;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.Stream;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.PlayDirective;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.StopDirective;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFailedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackNearlyFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStartedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStoppedRequest;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class PlayASongSpeechlet implements Speechlet, AudioPlayer{
	private static final Logger log = LoggerFactory.getLogger(PlayASongSpeechlet.class);
	
	@Override
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
		log.debug("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		log.debug("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		
		log.debug("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        
        log.debug("intent "+intent+"|"+"intentName "+intentName);
        if ("MunniBadnaamIntent".equals(intentName)) {
            return requestPlay();
        } else if ("PlayMunniBadnaamIntent".equals(intentName)) {
            return playMunniBadnaam();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
	}

	@Override
	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		 log.debug("inside onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
	                session.getSessionId());	
	}
	
	private SpeechletResponse requestPlay() {
    	log.debug("inside requestPlay");
        String speechText = "Please say play or stop";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Play");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
	
	/**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
    	log.debug("inside getWelcomeResponse");
        String speechText = "Welcome to the Alexa Skills Kit, you can say play";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Welcome");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SpeechletResponse playMunniBadnaam() {
    	log.debug("inside playMunniBadnaam with");
		Stream stream = new Stream();
    	stream.setUrl("https://audio1.maxi80.com");
    	
    	AudioItem audioItem = new AudioItem();
    	audioItem.setStream(stream);
    	
        PlayDirective playDirective = new PlayDirective();
        playDirective.setPlayBehavior(PlayBehavior.ENQUEUE);
        playDirective.setAudioItem(audioItem);

        final SpeechletResponse response = new SpeechletResponse();
        
        response.setDirectives(Arrays.<Directive>asList(playDirective));

        SpeechletResponseEnvelope envelope = new SpeechletResponseEnvelope();
        envelope.setResponse(response);
        
        log.debug("inside onPlaybackStarted with envelope={}", envelope);
        return envelope.getResponse();
    }
    
	@Override
	public SpeechletResponse onPlaybackFailed(SpeechletRequestEnvelope<PlaybackFailedRequest> requestEnvelope) {
		log.debug("inside onPlaybackFailed with envelope={}", requestEnvelope);
		String speechText = "Munni Badnaam Filed to Play";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("PlayBackFailed");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	@Override
	public SpeechletResponse onPlaybackFinished(SpeechletRequestEnvelope<PlaybackFinishedRequest> requestEnvelope) {
		log.debug("inside onPlaybackFinished with envelope={}", requestEnvelope);
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackNearlyFinished(
			SpeechletRequestEnvelope<PlaybackNearlyFinishedRequest> requestEnvelope) {
		log.debug("inside onPlaybackNearlyFinished with envelope={}", requestEnvelope);
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackStarted(SpeechletRequestEnvelope<PlaybackStartedRequest> requestEnvelope) {
		log.debug("inside onPlaybackStarted with envelope={}", requestEnvelope);
		return null;
	}

	@Override
	public SpeechletResponse onPlaybackStopped(SpeechletRequestEnvelope<PlaybackStoppedRequest> requestEnvelope) {
		log.debug("inside onPlaybackStoppedw with requestEnvelope={}", requestEnvelope);
		StopDirective stopDirective = new StopDirective();
    	
		final SpeechletResponse response = new SpeechletResponse();
        response.setDirectives(Arrays.<Directive>asList(stopDirective));

        SpeechletResponseEnvelope envelope = new SpeechletResponseEnvelope();
        envelope.setResponse(response);

        log.debug("inside onPlaybackStopped with envelope={}", envelope);
        return envelope.getResponse();
	}
}
