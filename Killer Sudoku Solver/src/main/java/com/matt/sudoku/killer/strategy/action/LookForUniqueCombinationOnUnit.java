package com.matt.sudoku.killer.strategy.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.event.StrategyAction;
import com.matt.sudoku.commons.strategy.event.StrategyEvent;
import com.matt.sudoku.killer.domain.KillerCombination;
import com.matt.sudoku.killer.domain.KillerUnit;
import com.matt.sudoku.killer.strategy.KillerStrategyUtils;
import com.matt.sudoku.killer.strategy.event.ClearedValue;
import com.matt.sudoku.killer.strategy.event.FoundNakedTwin;

import lombok.extern.slf4j.Slf4j;

/**
 * Looks in the Killer Unit to see if there is a unique combination of values that solves it.
 */
@Slf4j
public class LookForUniqueCombinationOnUnit extends StrategyAction {
	private final KillerUnit killerUnit;

	public LookForUniqueCombinationOnUnit(KillerUnit killerUnit) {
		super();
		this.killerUnit = killerUnit;
	}

	public KillerUnit getKillerUnit() {
		return killerUnit;
	}

	@Override
	public List<StrategyEvent> execute(SudokuGridManager gridManager) {
		KillerStrategyUtils utils = new KillerStrategyUtils();
		List<KillerCombination> combinations = utils.getCombinations(killerUnit);
		if (combinations == null) {
			throw new RuntimeException("Unsolvable!!!");
		} else {
			List<Set<Integer>> listOfSets = combinations.stream().map(kc -> kc.getValues()).collect(Collectors.toList());
			Set<Integer> intersectionValues = listOfSets.stream().skip(1)
				    .collect(()->new HashSet<>(listOfSets.get(0)), Set::retainAll, Set::retainAll);
						
			// remove all except XXX in the current unit
			List<StrategyEvent> resultEvents = new ArrayList<>();
			
			if (intersectionValues.isEmpty()) return resultEvents;

			for (Box boxInUnit : killerUnit.boxes()) {
				BoxValue boxValue = gridManager.getGrid().get(boxInUnit);
				Set<Integer> reduced = boxValue.reduceOthers(intersectionValues);
				reduced.forEach(i -> resultEvents.add(new ClearedValue(boxInUnit, i)));
			}
			
			// remove all XXX from super set units.
			resultEvents.add(new FoundNakedTwin(killerUnit.boxes(), intersectionValues));
			log.info("Found a Unique Combination at {} of values {}.  Generating {} events.", killerUnit.boxes(), intersectionValues, resultEvents.size());
			return resultEvents;
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
		LookForUniqueCombinationOnUnit other = (LookForUniqueCombinationOnUnit) obj;
		if (killerUnit == null) {
			if (other.killerUnit != null)
				return false;
		} else if (!killerUnit.equals(other.killerUnit))
			return false;
		return true;
	}
	
}
