package com.matt.sudoku.commons.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxMap {
	
	private final Map<String, Box> boxMapByCoord;
	
	public BoxMap(List<Box> boxes) {
		boxMapByCoord = new HashMap<>();
		for (Box b : boxes) {
			boxMapByCoord.put(coords(b.getRow(),  b.getColumn()), b);
		}
		
	}
	
	public Box get(String coord) {
		return boxMapByCoord.get(coord);
	}
	
	public Box get(char row, char column) {
		return get(String.format("%s%s", (char) row, (char) column));
	}
	
	public static String coords(Row row, Column column) {
		return String.format("%s%s", row.toString(), column.toString());
	}
	
	@Override
	public String toString() {
		return String.format("BoxMap[size:%s]", boxMapByCoord.size());
	}

	public Box[] getBoxArray(String... coords) {
		return Arrays.stream(coords).map(c->get(c)).toArray(Box[]::new);
	}
}
