package com.matt.sudoku.commons.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.matt.sudoku.commons.domain.BoxValue;

public class BoxValue {
	private List<Integer> values;
	
	public BoxValue() {
		values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
	}
	
	public BoxValue(Integer value) {
		solve(value);
	}
	
	public boolean isSolved() {
		return values.size() == 1;
	}
	
	public List<Integer> get() {
		return values;
	}
	
	public boolean contains(Integer value) {
		return values.contains(value);
	}
	
	public boolean isSuperSetOf(BoxValue subSet) {
		return this.values.containsAll(subSet.values);
	}
	
	public Integer getSolved() {
		if (isSolved()) return values.get(0);
		throw new RuntimeException("Box not solved.");
	}
	
	public void solve(Integer value) {
		values = new ArrayList<>(Arrays.asList(value));
	}
	
	public boolean reduce(Integer... i) {
		if (isSolved()) return false;
		return values.removeAll(Arrays.asList(i));
	}
	
	@Override
	public String toString() {
		return isSolved() ? String.valueOf(getSolved()) : ".";
	}
	
	public String toDetailedString() {
		StringBuilder sb = new StringBuilder();
		values.stream().forEach(sb::append);
		return sb.toString();
	}
}
