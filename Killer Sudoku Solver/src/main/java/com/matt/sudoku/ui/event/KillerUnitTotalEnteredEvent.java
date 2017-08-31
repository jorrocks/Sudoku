package com.matt.sudoku.ui.event;

import java.util.Set;

import org.springframework.context.ApplicationEvent;

public class KillerUnitTotalEnteredEvent extends ApplicationEvent {

	private final int killerUnitTotal;
	private final Set<String> boxCoords;
	public KillerUnitTotalEnteredEvent(Object source, Set<String> boxCoords, int killerUnitTotal) {
		super(source);
		this.killerUnitTotal = killerUnitTotal;
		this.boxCoords = boxCoords;
	}
	public int getKillerUnitTotal() {
		return killerUnitTotal;
	}
	public Set<String> getBoxCoordinates() {
		return boxCoords;
	}
}
