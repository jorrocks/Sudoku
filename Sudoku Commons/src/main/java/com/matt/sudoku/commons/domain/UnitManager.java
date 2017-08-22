package com.matt.sudoku.commons.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class UnitManager implements Serializable {
	private static final long serialVersionUID = 2295158487017517579L;
	private Map<Class<?>, Set<Unit>> unitsByType;
	private Map<Box, Set<Unit>> unitsByBox;
	
	@Autowired
	public UnitManager(List<Unit> units) {
		unitsByType = new HashMap<>();
		unitsByBox = new HashMap<>();
		units.stream().forEach(u -> addUnit(u));
	}
	
	public void addUnit(Unit unit) {
		Set<Unit> setByType = unitsByType.get(unit.getClass());
		if (setByType == null) {
			setByType = new HashSet<>();
			unitsByType.put(unit.getClass(), setByType);
		}
		setByType.add(unit);
		for (Box box : unit.boxes()) {
			Set<Unit> setByBox = unitsByBox.get(box);
			if (setByBox == null) {
				setByBox = new HashSet<>();
				unitsByBox.put(box, setByBox);
			}
			setByBox.add(unit);			
		}
	}
	
	public Set<Unit> getAllUnits() {
		return unitsByType.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
	}
	
	public <U> Set<U> getUnits(Class<U> clazz) {
		Set<Unit> allUnits = unitsByType.get(clazz);
		return (Set<U>)(Set<?>)allUnits;
	}

	public Set<Box> getIntersectingBoxes(Unit unit, Unit otherUnit) {
		HashSet<Box> boxes1 = new HashSet<>(unit.boxes());
		boxes1.retainAll(otherUnit.boxes());
		return boxes1;
	}
	
	public Set<Unit> getIntersectingUnits(Unit unit) {
		Set<Unit> resultSet = new HashSet<>();
		for (Box box : unit.boxes()) {
			resultSet.addAll(unitsByBox.get(box));
		}
		resultSet.remove(unit);
		return resultSet;
	}

	public Set<Unit> getUnitsThroughBox(Box box) {
		return unitsByBox.get(box);
	}
	
	public Set<Box> getBoxesConnectedThroughABoxesUnits(Box box) {
		Set<Unit> unitsThroughBox = getUnitsThroughBox(box);
		Set<Box> unitBoxes = new HashSet<>();
		unitsThroughBox.stream().map(u -> u.boxes()).reduce(unitBoxes, (s1, s2) -> {s1.addAll(s2); return s1;});
		unitBoxes.remove(box);
		return unitBoxes;
	}
}
