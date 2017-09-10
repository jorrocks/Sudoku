package com.matt.sudoku.commons.strategy.event;

import java.util.Collections;
import java.util.Set;

import com.matt.sudoku.commons.domain.Box;

public class StrategyEvent {
	private final Set<Box> boxes;
	private final Set<Integer> values;
	private final StrategyEventType type;
	
	public StrategyEvent(Box box, int value, StrategyEventType type) {
		this(Collections.singleton(box), Collections.singleton(value), type);
	}
	
	public StrategyEvent(Set<Box> boxes, Set<Integer> values, StrategyEventType type) {
		super();
		this.boxes = boxes;
		this.values = values;
		this.type = type;
	}
	
	public Set<Box> getBoxes() {
		return boxes;
	}
	
	public Set<Integer> getValues() {
		return values;
	}
	
	public StrategyEventType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "StrategyEvent [box=" + boxes + ", value=" + values + ", type=" + type + "]";
	}
	
}
