package com.matt.sudoku.solver.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.matt.sudoku.commons.config.AbstractSudokuConfig;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.ColumnUnit;
import com.matt.sudoku.commons.domain.CustomUnit;
import com.matt.sudoku.commons.domain.RowUnit;
import com.matt.sudoku.commons.domain.SquareUnit;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.commons.print.GridPrinter;
import com.matt.sudoku.commons.print.LargeGridPrinter;

@Profile("HyperSudoku")
@Configuration
public class HyperSudokuConfig extends AbstractSudokuConfig {

	@Bean
	public List<SquareUnit> hyperSquareUnits(BoxMap boxMap) {
		List<SquareUnit> result = new ArrayList<>();
		result.add(new SquareUnit(boxMap.get("B2"), boxMap));
		result.add(new SquareUnit(boxMap.get("B6"), boxMap));
		result.add(new SquareUnit(boxMap.get("F2"), boxMap));
		result.add(new SquareUnit(boxMap.get("F6"), boxMap));
		return result;
	}

	@Bean
	public List<CustomUnit> hyperCustomUnits(BoxMap boxMap) {
		List<CustomUnit> result = new ArrayList<>();
		result.add(new CustomUnit(boxMap.getBoxArray("A1", "A5", "A9", "E1", "E5", "E9", "I1", "I5", "I9")));

		result.add(new CustomUnit(boxMap.getBoxArray("B1", "C1", "D1", "B5", "C5", "D5", "B9", "C9", "D9")));
		result.add(new CustomUnit(boxMap.getBoxArray("F1", "G1", "H1", "F5", "G5", "H5", "F9", "G9", "H9")));

		result.add(new CustomUnit(boxMap.getBoxArray("A2", "A3", "A4", "E2", "E3", "E4", "I2", "I3", "I4")));
		result.add(new CustomUnit(boxMap.getBoxArray("A6", "A7", "A8", "E6", "E7", "E8", "I6", "I7", "I8")));
		return result;
	}
	
	@Bean 
	public List<Unit> units(List<RowUnit> rowUnits, List<ColumnUnit> columnUnits, List<SquareUnit> squareUnits, List<SquareUnit> hyperSquareUnits, List<CustomUnit> hyperCustomUnits) {
		List<Unit> result = new ArrayList<>();
		result.addAll(rowUnits);
		result.addAll(columnUnits);
		result.addAll(squareUnits);
		result.addAll(hyperSquareUnits);
		result.addAll(hyperCustomUnits);
		return result;
	}
	
	@Bean
	public GridPrinter gridPrinter() {
		return new LargeGridPrinter();
	}
}
