package com.matt.sudoku.commons.strategy;

import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Unit;

@Component
public class EliminateUniqueInUnit {

	public boolean eliminateUniqueInUnit(Unit unit, Grid grid) {
		boolean changes = false;
		for (Box box : unit.boxes()) {
			if (grid.get(box).isSolved() == false) {
				changes = eliminateUniqueInUnit(unit, grid, box) || changes;
			}
		}
		return changes;
	}
	
	private boolean eliminateUniqueInUnit(Unit unit, Grid grid, Box box) {
		BoxValue boxValue = grid.get(box);
		if (boxValue.isSolved()) return false;
		for (Integer value : boxValue.get()) {
			boolean unique = unit.boxes().stream().filter(b -> !b.equals(box)).allMatch(b -> grid.get(b).contains(value) == false);
			if (unique) {
				boxValue.solve(value);
				return true;
			}
		}
		return false;
	}

}
