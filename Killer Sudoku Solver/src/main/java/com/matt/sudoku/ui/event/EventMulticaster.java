package com.matt.sudoku.ui.event;

import javax.swing.JToggleButton;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EventMulticaster implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publishKillerUnitEntered(JToggleButton jToggleButton, int total) {
		applicationEventPublisher.publishEvent(new KillerUnitTotalEnteredEvent(jToggleButton, total));
	}
}
