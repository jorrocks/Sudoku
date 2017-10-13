package com.matt.sudoku.killer.strategy.event;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.strategy.event.StrategyEvent;

import lombok.Data;

@Data
public class ClearedValue implements StrategyEvent {
	private final Box box;
	private final Integer value;
}
