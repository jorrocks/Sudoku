package com.matt.sudoku.commons.print;

import com.matt.sudoku.commons.domain.BoxValue;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.Grid;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.commons.print.GridPrinter;

public class LargeGridPrinter implements GridPrinter {

	@Override
	public String print(Grid grid) {
		StringBuilder gridBuilder = new StringBuilder("     1     2     3     4     5     6     7     8     9  ").append(System.lineSeparator()).append(System.lineSeparator());
		
		for (char r : Row.ROW_CHARS.toCharArray()) {
			if (r == 'D' || r == 'G') 
				gridBuilder.append("   =================#=================#=================").append(System.lineSeparator());
			else if (r != 'A')
				gridBuilder.append("   -----+-----+-----#-----+-----+-----#-----+-----+----- ").append(System.lineSeparator());
			
			for (int ri = 0 ; ri<3 ; ri++) {
				StringBuilder rowBuilder = new StringBuilder();
				if (ri == 1) rowBuilder.append(r).append(" ");
				else rowBuilder.append("  ");
				for (char c : Column.COLUMN_CHARS.toCharArray()) {
					BoxValue boxValue = grid.get(r, c);
					if (c == '4' || c == '7') 
						rowBuilder.append('#');
					else if (c == '1')
						rowBuilder.append(' ');
					else
						rowBuilder.append('|');
					rowBuilder.append(' ');
					for (int ci=0 ; ci<3 ; ci++) {
						if (boxValue.isSolved()) {
							if (ri==1 && ci==1)
								rowBuilder.append(boxValue);
							else rowBuilder.append(' ');
						} else {
							int position = 3 * ri + ci + 1;
							rowBuilder.append(boxValue.contains(position) ? String.valueOf(position) : ".");
						}
					}
					rowBuilder.append(' ');
				}
				gridBuilder.append(rowBuilder.toString()).append(System.lineSeparator());
			}
		}
		
		return gridBuilder.toString();
	}

}
