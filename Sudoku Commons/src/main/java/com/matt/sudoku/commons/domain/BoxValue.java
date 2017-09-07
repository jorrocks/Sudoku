package com.matt.sudoku.commons.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.matt.sudoku.commons.domain.BoxValue;

public class BoxValue {
	private Set<Integer> values;
	
	public BoxValue() {
		values = IntStream.rangeClosed(1,  9).boxed().collect(Collectors.toCollection(HashSet::new));
	}
	
	public BoxValue(Integer value) {
		solve(value);
	}
	
	public boolean isSolved() {
		return values.size() == 1;
	}
	
	public Set<Integer> get() {
		return values;
	}
	
	public boolean contains(Integer value) {
		return values.contains(value);
	}
	
	public boolean isSuperSetOf(BoxValue subSet) {
		return this.values.containsAll(subSet.values);
	}
	
	public Integer getSolved() {
		if (isSolved()) return values.iterator().next();
		throw new RuntimeException("Box not solved.");
	}
	
	public void solve(Integer value) {
		values = new HashSet<>();
		values.add(value);
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

	public Set<Integer> reduceOthers(Set<Integer> nonReducedValues) {
		Set<Integer> currentValues = new HashSet<>(values);
		values.retainAll(nonReducedValues);
		if (values.isEmpty()) throw new RuntimeException("BoxValue empty.  Unsolvable.");
		currentValues.removeAll(values);
		return currentValues;
	}
}
