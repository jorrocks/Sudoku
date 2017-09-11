package com.matt.sudoku.commons.strategy.event;

import java.util.Set;

public interface ActionFactory {
	public Set<StrategyAction> buildActions(StrategyEvent event);
}
