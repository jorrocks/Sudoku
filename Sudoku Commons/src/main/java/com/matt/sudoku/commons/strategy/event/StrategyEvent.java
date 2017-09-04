package com.matt.sudoku.commons.strategy.event;

import com.matt.sudoku.commons.domain.Box;

public class StrategyEvent {
	private final Box box;
	private final int value;
	private final StrategyEventType type;
	
	public StrategyEvent(Box box, int value, StrategyEventType type) {
		super();
		this.box = box;
		this.value = value;
		this.type = type;
	}
	
	public Box getBox() {
		return box;
	}
	
	public int getValue() {
		return value;
	}
	
	public StrategyEventType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "StrategyEvent [box=" + box + ", value=" + value + ", type=" + type + "]";
	}
	
}
