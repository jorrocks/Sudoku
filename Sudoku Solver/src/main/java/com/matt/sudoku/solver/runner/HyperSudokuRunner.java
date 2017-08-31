package com.matt.sudoku.solver.runner;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.EliminateSolved;
import com.matt.sudoku.commons.strategy.EliminateUniqueInUnit;
import com.matt.sudoku.commons.strategy.IntersectingUnits;
import com.matt.sudoku.commons.strategy.NakedTwin;

@Profile("HyperSudoku")
@Component
@ComponentScan(basePackageClasses = {NakedTwin.class, EliminateUniqueInUnit.class, IntersectingUnits.class})
public class HyperSudokuRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(HyperSudokuRunner.class);
	
	@Autowired
	private List<Unit> units;
	
	@Autowired
	private SudokuGridManager gridFactory;
	
	@Autowired
	private EliminateSolved eliminateSolvedStrategy;
	
	@Autowired
	private NakedTwin nakedTwinStrategy;

	@Autowired
	private IntersectingUnits intersectingUnitsStrategy;

	@Autowired
	private EliminateUniqueInUnit eliminateUniqueInUnitStrategy;

	@Override
	public void run(String... arg0) throws Exception {
		log.info("units : {}", String.join(", ", units.stream().map(u -> u.toString()).collect(Collectors.toList())));
//		Grid g = new Grid("95.....41"+
//						  "1..9.4..6"+
//						  ".3.....9."+
//						  "........."+
//						  "...147..."+
//						  "........."+
//						  ".7.....6."+
//						  "2..4.3..9"+
//						  "64.....12");
		Grid g = gridFactory.buildGrid("........7"+
						  "5..2.69.."+
						  "....38..."+
						  "6.....7.."+
						  ".7.....2."+
						  "..5.....9"+
						  "...14...."+
						  "..97.5..1"+
						  "1........");
		log.info("\n{}", g);
		
		int i=0;
		for (; eliminateSolvedStrategy.eliminate(g); i++);
		log.info("{} eliminates", i);
		
		log.info("\n{}", g.print());

		boolean anyImprovement;
		do {
			anyImprovement = false;
			for (Unit unit : units) {
				boolean unitImprovement = false;
				unitImprovement = eliminateUniqueInUnitStrategy.eliminateUniqueInUnit(unit, g) || unitImprovement;
				unitImprovement = nakedTwinStrategy.nakedTwins(unit, g) || unitImprovement;
				unitImprovement = intersectingUnitsStrategy.intersectingUnitsStrategy(unit, g) || unitImprovement;
				if (unitImprovement) for (; eliminateSolvedStrategy.eliminate(g); i++);
				anyImprovement = unitImprovement || anyImprovement;
			}
		} while (anyImprovement);
		
		log.info("\n{}", g.print());
	}

}
