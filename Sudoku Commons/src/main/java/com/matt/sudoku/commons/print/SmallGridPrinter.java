package com.matt.sudoku.commons.print;

import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.commons.print.GridPrinter;

public class SmallGridPrinter implements GridPrinter {

	@Override
	public String print(Grid grid) {
		StringBuilder gridBuilder = new StringBuilder();
		
		for (char r : Row.ROW_CHARS.toCharArray()) {
			if (r == 'D' || r == 'G') 
				gridBuilder.append("------+------+------").append(System.lineSeparator());
			StringBuilder rowBuilder = new StringBuilder();
			for (char c : Column.COLUMN_CHARS.toCharArray()) {
				BoxValue value = grid.get(r, c);
				if (c == '4' || c == '7') 
					rowBuilder.append('|');
				rowBuilder.append(value);
				rowBuilder.append(' ');
			}
			gridBuilder.append(rowBuilder.toString()).append(System.lineSeparator());
		}
		
		return gridBuilder.toString();
	}

}
