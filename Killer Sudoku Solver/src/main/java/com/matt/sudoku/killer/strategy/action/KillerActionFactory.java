package com.matt.sudoku.killer.strategy.action;

import java.util.HashSet;
import java.util.Set;

import com.matt.sudoku.commons.domain.RowUnit;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.commons.strategy.event.ActionFactory;
import com.matt.sudoku.commons.strategy.event.StrategyAction;
import com.matt.sudoku.commons.strategy.event.StrategyEvent;
import com.matt.sudoku.killer.strategy.event.FoundNakedTwin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KillerActionFactory implements ActionFactory {
	private final UnitManager unitManager;
	
	@Override
	public Set<StrategyAction> buildActions(StrategyEvent event) {
		Set<StrategyAction> results = new HashSet<>();
		if (event instanceof FoundNakedTwin) {
			FoundNakedTwin foundNakedTwin = (FoundNakedTwin) event;
			unitManager.getSuperSetUnits(foundNakedTwin.getBoxes())
				.forEach(u -> results.add(new NakedTwinAction(u, foundNakedTwin.getBoxes(), foundNakedTwin.getValues())));
		}
		return results;
	}

}
