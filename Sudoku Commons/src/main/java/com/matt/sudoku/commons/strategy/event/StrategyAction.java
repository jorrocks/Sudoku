package com.matt.sudoku.commons.strategy.event;

import com.matt.sudoku.commons.factory.SudokuGridManager;

public abstract class StrategyAction implements Comparable<StrategyAction> {
	public abstract int getPrecedence();

	@Override
	public int compareTo(StrategyAction o) {
		return Integer.compare(this.getPrecedence(), o.getPrecedence());
	}

	public abstract void execute(SudokuGridManager gridManager, ActionQueue queue);
	
}
