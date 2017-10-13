package com.matt.sudoku.commons.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString(callSuper=true)
public class RowUnit implements Unit {
	@Getter
	private final Row row;
	private final BoxMap boxMap;
	
	public Set<Box> boxes() {
		Set<Box> result = new HashSet<>();
		Column.COLUMN_CHARS.chars().forEach(c -> result.add(boxMap.get(row.getChar(), (char) c)));
		return result;
	}
	
	@Override
	public boolean isNeighbour(Unit unit) {
		if (unit == null || !(unit instanceof RowUnit)) return false;
		RowUnit other = (RowUnit)unit;
		return Math.abs(this.row.getChar() - other.row.getChar()) == 1;
	}
}
