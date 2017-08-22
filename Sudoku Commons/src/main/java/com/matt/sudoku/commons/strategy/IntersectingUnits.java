package com.matt.sudoku.commons.strategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;

@Component
public class IntersectingUnits {
	private static final Logger log = LoggerFactory.getLogger(IntersectingUnits.class);
	private UnitManager unitManager;
	
	@Autowired
	public IntersectingUnits(UnitManager unitManager) {
		this.unitManager = unitManager;
	}
	
	/**
	 * Looks at the boxes that are an intersection of 2 units.  If the intersecting boxes
	 * are the only boxes that contain a solution value for one unit, then they must also be 
	 * the only boxes that contain the solution for the other unit.
	 * @param grid
	 * @return
	 */
	public boolean intersectingUnitsStrategy(Unit unit, Grid grid) {
		boolean changes = false;
		for (Unit otherUnit : unitManager.getIntersectingUnits(unit)) {
			changes = intersectingUnitsStrategy(unit, grid, otherUnit) || changes;
		}
		
		return changes;
	}

	public boolean intersectingUnitsStrategy(Unit unit, Grid grid, Unit otherUnit) {
		log.debug("Entered Unit[{}].intersectingUnitsStrategy(Grid, Unit[{}]", this, otherUnit);
		boolean changes = false;

		Set<Box> intersectingBoxes = unitManager.getIntersectingBoxes(unit, otherUnit);
		if (intersectingBoxes.size() <= 1) return false;
		Set<Box> thisUnitRemainingBoxes = new HashSet<>(unit.boxes());
		thisUnitRemainingBoxes.removeAll(intersectingBoxes);
		Set<Box> otherUnitRemainingBoxes = new HashSet<>(otherUnit.boxes());
		otherUnitRemainingBoxes.removeAll(intersectingBoxes);
		
//		if (thisUnitRemainingBoxes.stream().map(b->grid.get(b)).allMatch(bv->bv.isSolved())) return false;
		// all values that are in the thisUnitRemainingBoxes
		Set<Integer> thisUnitRemainingValues = thisUnitRemainingBoxes.stream()
//				.filter(b->grid.get(b).isSolved() == false)
				.map(b->grid.get(b).get())
				.flatMap(vs->vs.stream()).distinct()
				.collect(Collectors.toSet());
		if (thisUnitRemainingValues.isEmpty()) return false;
		
		// all values that are unsolved and are in the intersecting boxes
		Stream<Integer> intersectingValues = intersectingBoxes.stream()
	//			.filter(b->grid.get(b).isSolved() == false)
				.map(b->grid.get(b).get())
				.flatMap(vs->vs.stream()).distinct();

		// and values that are in the intersection region, but not in the remaining of thisUnit
		Integer[] intersectingSpecificValues = intersectingValues
				.filter(v -> !thisUnitRemainingValues.contains(v))
				.toArray(Integer[]::new);
		if (intersectingSpecificValues.length == 0) return false;
		
		// remove all these values from the otherUnitRemainingBoxes
		for (Box otherBox : otherUnitRemainingBoxes) {
			BoxValue otherBoxValue = grid.get(otherBox);
			boolean thisChange = otherBoxValue.reduce(intersectingSpecificValues);
			if (thisChange) log.info("Unit[{}].intersectingUnitsStrategy(Grid, Unit[{}] reduced Box:{}, {}", this, otherUnit, otherBox, intersectingSpecificValues);
			changes = thisChange || changes;
		}
		
		return changes;
	}
}
