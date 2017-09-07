package com.matt.sudoku.killer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.matt.sudoku.commons.config.AbstractSudokuConfig;
import com.matt.sudoku.commons.domain.ColumnUnit;
import com.matt.sudoku.commons.domain.RowUnit;
import com.matt.sudoku.commons.domain.SquareUnit;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.print.GridPrinter;
import com.matt.sudoku.commons.print.LargeGridPrinter;
import com.matt.sudoku.commons.strategy.event.ActionQueue;

@Configuration
public class SudokuConfig extends AbstractSudokuConfig {

	@Bean 
	public List<Unit> units(List<RowUnit> rowUnits, List<ColumnUnit> columnUnits, List<SquareUnit> squareUnits) {
		List<Unit> result = new ArrayList<>();
		result.addAll(rowUnits);
		result.addAll(columnUnits);
		result.addAll(squareUnits);
		return result;
	}
	
	@Bean
	public GridPrinter gridPrinter() {
		return new LargeGridPrinter();
	}
	
	@Bean
	public SudokuGridManager gridManager() {
		return new SudokuGridManager();
	}
	
	@Bean
	public ActionQueue actionQueue() {
		return new ActionQueue();
	}
}
