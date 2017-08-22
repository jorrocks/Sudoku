package com.matt.sudoku.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Box implements Serializable {
	
	private static final long serialVersionUID = 6131268185120865771L;

	public static List<Box> create(List<Row> rows, List<Column> columns) {
		List<Box> result = new ArrayList<>();
		rows.stream().forEach(r -> columns.stream().forEach(c -> result.add(new Box(r, c))));
		return result;
	}
	
	private final Column column;
	
	private final Row row;
	
	public Box(Row r, Column c) {
		this.row = r;
		this.column = c;
	}
	
	public Row getRow() {
		return row;
	}
	
	public Column getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return String.format("%s%s", row.toString(), column.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Box == false) return false;
		Box otherBox = (Box)obj;
		return row.equals(otherBox.row) && column.equals(otherBox.column);
	}
}
