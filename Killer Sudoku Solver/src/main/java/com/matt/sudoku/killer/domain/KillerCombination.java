package com.matt.sudoku.killer.domain;

import java.util.Set;

public class KillerCombination {
	private final KillerUnit killerUnit;
	private final Set<Integer> values;
	
	public KillerCombination(KillerUnit killerUnit, Set<Integer> values) {
		super();
		this.killerUnit = killerUnit;
		this.values = values;
	}
	
	public KillerUnit getKillerUnit() {
		return killerUnit;
	}
	
	public Set<Integer> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return "KillerCombination [killerUnit=" + killerUnit + ", values=" + values + "]";
	}
	
	
}
