package com.matt.sudoku.killer.domain;

import java.util.HashSet;
import java.util.Set;

import com.matt.sudoku.commons.domain.Unit;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A Group of Units that are adjoining.
 * @author Matt
 *
 */
@Getter
@EqualsAndHashCode
public class UnitArea {
	
	private Set<Unit> units;
	private final Class<? extends Unit> unitType;

	public UnitArea(Class<? extends Unit> unitType) {
		this.unitType = unitType;
		units = new HashSet<>();
	}
	
	public boolean add(Unit unit) {
		return units.add(unit);
	}

	public int getTotal() {
		return units.size() * 45;
	}
}
