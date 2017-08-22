package com.matt.sudoku.commons.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.print.GridPrinter;

public abstract class GridFactory {
	@Autowired
	protected GridPrinter gridPrinter;
	
	public abstract Grid buildGrid(String input);
}
