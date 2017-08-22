package com.matt.sudoku.commons.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.matt.sudoku.commons.print.GridPrinter;
import com.matt.sudoku.commons.print.SmallGridPrinter;

public class Grid {
	private final BoxMap boxMap;
	private final Map<Box, BoxValue> valueMap;
	private final GridPrinter gridPrinter;
	
	public Grid(String values, BoxMap boxMap, GridPrinter gridPrinter) {
		this.boxMap = boxMap;
		this.gridPrinter = gridPrinter;
		
		assert values != null;
		assert values.length() == Row.ROW_CHARS.length() * Column.COLUMN_CHARS.length();
		this.valueMap = new HashMap<>();
		int v = 0;
		for (char r : Row.ROW_CHARS.toCharArray()) {
			for (char c : Column.COLUMN_CHARS.toCharArray()) {
				valueMap.put(boxMap.get(r, c), parseValue(values.charAt(v++)));
			}
		}
	}
	
	public BoxValue get(char row, char column) {
		return get(boxMap.get(row, column));				
	}
	
	public BoxValue get(Box box) {
		return valueMap.get(box);
	}
	
	public Set<Box> boxes() {
		return valueMap.keySet();
	}
	
	public static BoxValue parseValue(char c) {
		if (c >= '1' && c <= '9') 
			return new BoxValue(c - '0');
		else return new BoxValue();
	}
	
	@Override
	public String toString() {
		return new SmallGridPrinter().print(this);
	}
	
	public String print() {
		return gridPrinter.print(this);
	}
}
