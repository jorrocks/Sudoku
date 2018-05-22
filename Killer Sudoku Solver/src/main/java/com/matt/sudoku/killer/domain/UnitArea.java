package com.matt.sudoku.killer.domain;

import java.util.HashSet;
import java.util.Set;

import com.matt.sudoku.commons.domain.Unit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A Group of Units that are adjoining.
 * @author Matt
 *
 */
@Getter
@EqualsAndHashCode
@ToString
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

	public UnitArea cloneWithUnitIncrement(Unit neighbour) {
		if (units.contains(neighbour)) throw new RuntimeException("neighbour is already in the Unit area.");
		UnitArea newUnitArea = new UnitArea(unitType);
		newUnitArea.units.addAll(units);
		newUnitArea.add(neighbour);
		return newUnitArea;
	}
}
