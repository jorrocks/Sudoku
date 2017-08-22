package com.matt.sudoku.commons.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.factory.GridFactory;

@Component
@Profile({"HyperSudoku", "Sudoku"})
public class SudokuGridFactory extends GridFactory {
	@Autowired
	private BoxMap boxMap;

	@Override
	public Grid buildGrid(String input) {
		return new Grid(input, this.boxMap, this.gridPrinter);
	}
}
