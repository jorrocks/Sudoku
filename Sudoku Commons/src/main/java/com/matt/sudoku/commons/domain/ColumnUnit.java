package com.matt.sudoku.commons.domain;

import java.util.HashSet;
import java.util.Set;

public class ColumnUnit implements Unit {
	private final Column column;
	private final BoxMap boxMap;
	
	public ColumnUnit(Column c, BoxMap boxMap) {
		this.column = c;
		this.boxMap= boxMap;
	}
	
	public Set<Box> boxes() {
		Set<Box> result = new HashSet<>();
		Row.ROW_CHARS.chars().forEach(r -> result.add(boxMap.get((char) r, column.getChar())));
		return result;
	}
	
	public Column getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return String.format("ColumnUnit: %s", column.toString());
	}
}
