package com.matt.sudoku.killer.domain;

import java.util.Set;

import com.matt.sudoku.commons.domain.Unit;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class UnitArea<T extends Unit> {
	
	private Set<T> units;
	
	public boolean add(T unit) {
		return units.add(unit);
	}

	public int getTotal() {
		return units.size() * 45;
	}
}
