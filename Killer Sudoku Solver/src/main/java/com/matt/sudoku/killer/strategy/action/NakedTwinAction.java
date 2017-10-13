package com.matt.sudoku.killer.strategy.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.event.StrategyAction;
import com.matt.sudoku.commons.strategy.event.StrategyEvent;
import com.matt.sudoku.killer.strategy.event.ClearedValue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NakedTwinAction extends StrategyAction {

	private final Unit unit; 
	private final Set<Box> nakedTwinBoxes;
	private final Set<Integer> nakedTwinValues;

	@Override
	public int getPrecedence() {
		return 200;
	}

	@Override
	public List<StrategyEvent> execute(SudokuGridManager gridManager) {
		List<StrategyEvent> resultEvents = new ArrayList<>();
		
		Grid grid = gridManager.getGrid();
		// remove other values from the nakedTwin Boxes
		for (Box b : nakedTwinBoxes) {
			BoxValue bv = grid.get(b);
			Set<Integer> reduced = bv.reduceOthers(nakedTwinValues);
			reduced.forEach(i -> resultEvents.add(new ClearedValue(b, i)));
		}
		
		// remove values from the other (non nakedTwin) boxes
		Set<Box> otherBoxes = new HashSet<>();
		otherBoxes.addAll(unit.boxes());
		otherBoxes.removeAll(nakedTwinBoxes);
		for (Box b : otherBoxes) {
			BoxValue bv = grid.get(b);
			Set<Integer> reduced = bv.reduceValues(nakedTwinValues);
			reduced.forEach(i -> resultEvents.add(new ClearedValue(b, i)));
		}
		
		return resultEvents;
	}

}
