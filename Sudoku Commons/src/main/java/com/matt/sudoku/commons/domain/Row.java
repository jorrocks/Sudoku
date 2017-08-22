package com.matt.sudoku.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.matt.sudoku.commons.domain.Row;

public class Row implements Serializable {
	private static final long serialVersionUID = -6698860466626119670L;
	public static final String ROW_CHARS = "ABCDEFGHI";
	
	public static List<Row> create(String rowString) {
		List<Row> result = new ArrayList<>();
		rowString.chars().forEach(c -> result.add(new Row((char) c)));
		return result;
	}

	private final char c;
	
	public Row(char c) {
		this.c = c;
	}
	
	@Override
	public String toString() {
		return String.valueOf(c);
	}

	public char getChar() {
		return c;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Row == false) return false;
		return ((Row)obj).c == c;
	}
}
