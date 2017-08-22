package com.matt.sudoku.ui.event;

import javax.swing.JToggleButton;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class KillerUnitTotalEnteredEvent extends ApplicationEvent {

	private int killerUnitTotal;
	public KillerUnitTotalEnteredEvent(JToggleButton source, int killerUnitTotal) {
		super(source);
		Assert.isTrue(source.isSelected(), String.format("Button %s should be selected before firing this event.", source));
		this.killerUnitTotal = killerUnitTotal;
	}
	public int getKillerUnitTotal() {
		return killerUnitTotal;
	}
}
