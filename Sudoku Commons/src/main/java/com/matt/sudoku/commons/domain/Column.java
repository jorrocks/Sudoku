package com.matt.sudoku.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.matt.sudoku.commons.domain.Column;

public class Column implements Serializable {
	private static final long serialVersionUID = -2126445875981836835L;
	public static final String COLUMN_CHARS = "123456789";
	
	public static List<Column> create(String columnString) {
		List<Column> result = new ArrayList<>();
		columnString.chars().forEach(c -> result.add(new Column((char) c)));
		return result;
	}

	private final char c;
	public Column(char c) {
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
		if (obj instanceof Column == false) return false;
		return ((Column)obj).c == c;
	}
}
