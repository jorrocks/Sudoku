package com.matt.sudoku.commons.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ColumnUnit implements Unit {
	@Getter
	private final Column column;
	private final BoxMap boxMap;
	
	public Set<Box> boxes() {
		Set<Box> result = new HashSet<>();
		Row.ROW_CHARS.chars().forEach(r -> result.add(boxMap.get((char) r, column.getChar())));
		return result;
	}
	
	@Override
	public boolean isNeighbour(Unit unit) {
		if (unit == null || !(unit instanceof ColumnUnit)) return false;
		ColumnUnit other = (ColumnUnit)unit;
		return Math.abs(this.column.getChar() - other.column.getChar()) == 1;
	}
}
