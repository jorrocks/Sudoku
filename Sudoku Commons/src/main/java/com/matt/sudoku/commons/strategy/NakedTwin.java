package com.matt.sudoku.commons.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Unit;

@Component
public class NakedTwin {
	private static final Logger log = LoggerFactory.getLogger(NakedTwin.class);
	
	public boolean nakedTwins(Unit unit, Grid grid) {
		boolean changes = false;
		
		int numberSolvedBoxesInUnit = (int)unit.boxes().stream().filter(b -> grid.get(b).isSolved()).count();
		if (numberSolvedBoxesInUnit == 9) return false;
		
		for (Box box : unit.boxes()) {		
			BoxValue boxValue = grid.get(box);
			int numberPossiblesInBox = boxValue.get().size();
			if (boxValue.isSolved() == false && numberPossiblesInBox < 9-numberSolvedBoxesInUnit) {
				// check all other boxes for subsets of this.  if we find numberPossiblesInBox subsets, then nakedTwin
				List<Box> nakedTwins = new ArrayList<>();
				nakedTwins.add(box);
				unit.boxes().stream()
					.filter(b -> !box.equals(b))
					.filter(b -> grid.get(box).isSuperSetOf(grid.get(b)))
					.forEach(b -> nakedTwins.add(b));
				if (nakedTwins.size() < numberPossiblesInBox) {
					// no naked twin.  continue
				} else if (nakedTwins.size() == numberPossiblesInBox) {
					// naked twin
					boolean thisTwinChanges = false;
					Integer[] nakedTwinValues = nakedTwins.stream()
							.map(b -> grid.get(b).get())
							.flatMap(v -> v.stream())
							.distinct()
							.toArray(Integer[]::new);
					
					for (Box otherBox : unit.boxes().stream()
						.filter(b -> !nakedTwins.contains(b)).collect(Collectors.toList())) {
						BoxValue otherBoxValue = grid.get(otherBox);
						thisTwinChanges = otherBoxValue.reduce(nakedTwinValues) || thisTwinChanges;
						changes = thisTwinChanges || changes;
					}
					if (thisTwinChanges) log.info("NakedTwin in {} removed values {}", this, Arrays.stream(nakedTwinValues).map(String::valueOf).reduce("", String::concat));
				} else {
					log.error("\n"+grid.print());
					throw new RuntimeException(String.format("Unsolvable.  Attempting Naked Twin on Unit:%s, Box:%s.  Impossible to put only %s values in %s Boxes.", this, box, boxValue.get(), nakedTwins));
				}
			}
		}
		
		return changes;
	}

}
