package com.matt.sudoku.solver.runner;

import java.util.List;

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
import com.matt.sudoku.commons.strategy.NakedTwin;

@Profile("Sudoku")
@Component
@ComponentScan(basePackageClasses = {NakedTwin.class})
public class SudokuRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(SudokuRunner.class);
	
	@Autowired
	private List<Unit> units;
	
	@Autowired
	private SudokuGridManager gridFactory;
	
	@Autowired
	private EliminateSolved eliminateSolvedStrategy;

	@Autowired
	private NakedTwin nakedTwinStrategy;

	@Override
	public void run(String... arg0) throws Exception {
//		log.info("rows : {}", String.join(", ", rows.stream().map(c -> c.toString()).collect(Collectors.toList())));
//		log.info("columns : {}", String.join(", ", columns.stream().map(c -> c.toString()).collect(Collectors.toList())));
//		
//		log.info("boxes : {}", String.join(", ", boxes.stream().map(c -> c.toString()).collect(Collectors.toList())));
//		
//		log.info("boxMap {}.  E.g. Box B7 is {}", boxMap, boxMap.get("B7"));
//		
//		log.info("units : {}", String.join(", ", units.stream().map(u -> u.toString()).collect(Collectors.toList())));
		
//		log.info("parseInt('1')={}", Grid.parseValue('1'));
//		log.info("parseInt('9')={}", Grid.parseValue('9'));
//		log.info("parseInt('0')={}", Grid.parseValue('0'));
//		log.info("parseInt('.')={}", Grid.parseValue('.'));
		
//		Grid g = gridFactory.buildGrid("..3.2.6..9..3.5..1..18.64....81.29..7.......8..67.82....26.95..8..2.3..9..5.1.3..", boxMap);
//		Grid g = gridFactory.buildGrid("4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......", boxMap);
//		Grid g = gridFactory.buildGrid("..9.1...."+
//						  "8....7..."+
//						  ".56..3..9"+
//						  "54..7...."+
//						  "19..4..26"+
//						  "....9..54"+
//						  "4..2..19."+
//						  "...7....5"+
//						  "....5.3..", boxMap);
		Grid g = gridFactory.buildGrid("...64..7."+
						  "......4.3"+
						  "9..1..6.."+
						  "..7.2...1"+
						  "3.......7"+
						  "4...9.5.."+
						  "..6..5..2"+
						  "1.5......"+
						  ".4..72...");
		log.info("\n{}", g);
		
		int i=0;
		for (; eliminateSolvedStrategy.eliminate(g); i++);
		log.info("{} eliminates", i);
		
		log.info("\n{}", g.print());

		boolean anyImprovement;
		do {
			anyImprovement = false;
			for (Unit unit : units) {
				boolean nakedTwinImprovement = nakedTwinStrategy.nakedTwins(unit, g);
				if (nakedTwinImprovement) {
					log.info("nakedTwin in unit : {}", unit);
					for (; eliminateSolvedStrategy.eliminate(g); i++);
				}
				anyImprovement = nakedTwinImprovement || anyImprovement;
			}
		} while (anyImprovement);
		
		log.info("\n{}", g.print());
	}

}
