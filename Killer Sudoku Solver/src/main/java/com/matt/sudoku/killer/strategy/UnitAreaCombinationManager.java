package com.matt.sudoku.killer.strategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.killer.domain.UnitArea;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnitAreaCombinationManager {
	private final UnitManager unitManager;
	private Map<Class<? extends Unit>, Set<UnitArea>> cachedUnitAreaCombinations = new HashMap<>();
	
	public Set<UnitArea> getUnitAreaCombinations(Class<? extends Unit> clazz) {
		if (!cachedUnitAreaCombinations.containsKey(clazz)) {
			intialiseUnitAreaCombinations(clazz);
		}
		
		return cachedUnitAreaCombinations.get(clazz);
	}

	private void intialiseUnitAreaCombinations(Class<? extends Unit> clazz) {
		Set<? extends Unit> units = unitManager.getUnits(clazz);
		Set<UnitArea> results = new HashSet<>();
		for (Unit unit : units) {
			UnitArea unitArea = new UnitArea(clazz);
			unitArea.add(unit);
			recurse(results, unitArea);
		}
	}

	private void recurse(Set<UnitArea> results, UnitArea unitArea) {
		if (results.contains(unitArea)) return;
		results.add(unitArea);
		Set<Unit> neighbours = getBoundaryNeighbours(unitArea);
		for (Unit neighbour : neighbours) {
			UnitArea newUnitArea = null; //TODO
			recurse(results, newUnitArea);
		}
	}

	public Set<Unit> getBoundaryNeighbours(UnitArea unitArea) {
		Set<? extends Unit> allUnits = unitManager.getUnits(unitArea.getUnitType());
		Set<Unit> unitAreaUnits = unitArea.getUnits();
		return allUnits.stream()
				.filter(u -> unitAreaUnits.stream().anyMatch(uau -> uau.isNeighbour(u)) 
						&& unitAreaUnits.contains(u)==false)
				.collect(Collectors.toSet());
	}

}
