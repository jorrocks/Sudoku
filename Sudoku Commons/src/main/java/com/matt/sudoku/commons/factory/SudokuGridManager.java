package com.matt.sudoku.commons.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.print.GridPrinter;

@Component
public class SudokuGridManager {
	@Autowired
	private BoxMap boxMap;
	@Autowired
	protected GridPrinter gridPrinter;
	protected Grid grid;

	public Grid buildGrid(String input) {
		grid = new Grid(input, this.boxMap, this.gridPrinter);
		return grid;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
