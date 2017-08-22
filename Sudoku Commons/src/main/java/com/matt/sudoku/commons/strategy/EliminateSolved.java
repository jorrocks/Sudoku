package com.matt.sudoku.commons.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.UnitManager;

@Component
public class EliminateSolved {
	
	@Autowired
	private UnitManager unitManager;
	
	/**
	 * looks at all solved boxes and eliminates the solved value from all boxes that share a common unit.
	 * @return true if the grid changed in any way.
	 */
	public boolean eliminate(Grid grid) {
		boolean changes = false;
		for (Box box : grid.boxes()) {
			BoxValue boxValue = grid.get(box);
			if (boxValue.isSolved()) {
				Integer solvedValue = boxValue.getSolved();
				for (Box unitBox : unitManager.getBoxesConnectedThroughABoxesUnits(box)) {
					changes = grid.get(unitBox).reduce(solvedValue) || changes;
				}
			}			
		}		
		return changes;
	}

}
