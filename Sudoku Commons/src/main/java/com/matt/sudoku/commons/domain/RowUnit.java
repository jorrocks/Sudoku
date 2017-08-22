package com.matt.sudoku.commons.domain;

import java.util.HashSet;
import java.util.Set;

public class RowUnit implements Unit {
	private final Row row;
	private final BoxMap boxMap;
	
	public RowUnit(Row r, BoxMap boxMap) {
		this.row = r;
		this.boxMap= boxMap;
	}
	
	public Set<Box> boxes() {
		Set<Box> result = new HashSet<>();
		Column.COLUMN_CHARS.chars().forEach(c -> result.add(boxMap.get(row.getChar(), (char) c)));
		return result;
	}
	
	public Row getRow() {
		return row;
	}
	
	@Override
	public String toString() {
		return String.format("RowUnit: %s", row.toString());
	}
}
