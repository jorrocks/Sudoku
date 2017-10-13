package com.matt.sudoku.killer.strategy.event;

import java.util.Set;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.strategy.event.StrategyEvent;

import lombok.Data;

@Data
public class FoundNakedTwin implements StrategyEvent {
	private final Set<Box> boxes;
	private final Set<Integer> values;
	
	@Override
	public String toString() {
		return "FoundNakedTwin [box=" + boxes + ", value=" + values + "]";
	}

}
