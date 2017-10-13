package com.matt.sudoku.commons.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
	
	@Override
	public String toString() {
		return isSolved() ? String.valueOf(getSolved()) : ".";
	}
	
	public String toDetailedString() {
		StringBuilder sb = new StringBuilder();
		values.stream().forEach(sb::append);
		return sb.toString();
	}
	
	@Deprecated
	public boolean reduce(Integer... i) {
		if (isSolved()) return false;
		return values.removeAll(Arrays.asList(i));
	}

	public Set<Integer> reduceOthers(Set<Integer> nonReducedValues) {
		if (isSolved()) return Collections.emptySet();
		Set<Integer> currentValues = new HashSet<>(values);
		values.retainAll(nonReducedValues);
		if (values.isEmpty()) throw new RuntimeException("BoxValue empty.  Unsolvable.");
		currentValues.removeAll(values);
		return currentValues;
	}

	public Set<Integer> reduceValues(Set<Integer> toReduceValues) {
		if (isSolved()) return Collections.emptySet();
		Set<Integer> results = toReduceValues.stream().filter(values::contains).collect(Collectors.toSet());
		values.removeAll(toReduceValues);
		return results;
	}
}
