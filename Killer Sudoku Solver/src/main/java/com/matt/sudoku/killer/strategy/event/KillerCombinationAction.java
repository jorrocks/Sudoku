package com.matt.sudoku.killer.strategy.event;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.event.ActionQueue;
import com.matt.sudoku.commons.strategy.event.StrategyAction;
import com.matt.sudoku.killer.domain.KillerCombination;
import com.matt.sudoku.killer.domain.KillerUnit;
import com.matt.sudoku.killer.strategy.KillerStrategyUtils;

public class KillerCombinationAction extends StrategyAction {
	private static final Logger log = LoggerFactory.getLogger(KillerCombinationAction.class);
	private final KillerUnit killerUnit;

	public KillerCombinationAction(KillerUnit killerUnit) {
		super();
		this.killerUnit = killerUnit;
	}

	public KillerUnit getKillerUnit() {
		return killerUnit;
	}

	@Override
	public void execute(SudokuGridManager gridManager, ActionQueue queue) {
		KillerStrategyUtils utils = new KillerStrategyUtils();
		List<KillerCombination> combinations = utils.getCombinations(killerUnit);
		if (combinations == null) {
			throw new RuntimeException("Unsolvable!!!");
		} else if (combinations.size() == 1) {
			// TODO
			// remove all except XXX in the current unit
			
			for (Box boxInUnit : killerUnit.boxes()) {
				BoxValue boxValue = gridManager.getGrid().get(boxInUnit);
				Set<Integer> reduced = boxValue.reduceOthers(combinations.get(0).getValues());
				reduced.forEach(i -> queue.fireReducedEvent(i, boxInUnit));
			}
			
			// remove all XXX from super set units.
		} else {
			// are there any values that are common to all combinations?
			
			// are there any values that aren't in any combinations?
		}
	}

	@Override
	public int getPrecedence() {
		return 100;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((killerUnit == null) ? 0 : killerUnit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KillerCombinationAction other = (KillerCombinationAction) obj;
		if (killerUnit == null) {
			if (other.killerUnit != null)
				return false;
		} else if (!killerUnit.equals(other.killerUnit))
			return false;
		return true;
	}
	
}
