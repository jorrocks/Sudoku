package com.matt.sudoku.commons.strategy.event;

import java.util.List;

import com.matt.sudoku.commons.factory.SudokuGridManager;

public abstract class StrategyAction implements Comparable<StrategyAction> {
	public abstract int getPrecedence();

	@Override
	public int compareTo(StrategyAction o) {
		if (!this.getClass().equals(o.getClass()))
			return Integer.compare(this.getPrecedence(), o.getPrecedence());
		else {
			return Integer.compare(this.hashCode(), o.hashCode());
		}
	}

	public abstract List<StrategyEvent> execute(SudokuGridManager gridManager);
	
}
