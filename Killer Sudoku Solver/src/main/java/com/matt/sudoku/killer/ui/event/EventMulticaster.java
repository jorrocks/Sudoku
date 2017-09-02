package com.matt.sudoku.killer.ui.event;

import java.util.Set;

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

	public void publishKillerUnitEntered(Object source, Set<String> boxCoords, int total) {
		applicationEventPublisher.publishEvent(new KillerUnitTotalEnteredEvent(source, boxCoords, total));
	}
}
