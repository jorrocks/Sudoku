package com.matt.sudoku.killer.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.api.Condition;
import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.commons.domain.RowUnit;
import com.matt.sudoku.commons.domain.SquareUnit;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.killer.domain.UnitArea;

public class UnitAreaCombinationManagerTest {

	private List<Row> rows;
	private List<Column> columns;
	private BoxMap boxMap;
	private Set<RowUnit> rowUnits;
	private Set<SquareUnit> squareUnits;
	private UnitManager unitManager;

	@Before
	public void setup() {
		rows = Row.create("ABCDEFGHI");
		columns = Column.create("123456789");
		boxMap = new BoxMap(Box.create(rows,  columns));
		rowUnits = rows.stream().map(r -> new RowUnit(r, boxMap)).collect(Collectors.toSet());
		squareUnits = new HashSet<>();
		squareUnits.add(new SquareUnit(boxMap.get("A1"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("A4"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("A7"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("D1"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("D4"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("D7"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("G1"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("G4"), boxMap));
		squareUnits.add(new SquareUnit(boxMap.get("G7"), boxMap));
		
		unitManager = new UnitManager(new ArrayList<>(rowUnits));
		squareUnits.forEach(su -> unitManager.addUnit(su));
	}

	@Test
	public void rowUnit_GetBoundaryNeighboursTest() {
		UnitAreaCombinationManager manager = new UnitAreaCombinationManager(unitManager);
		
		UnitArea unitArea1 = new UnitArea(RowUnit.class);
		unitArea1.add(getRowUnit('A'));
		Set<Unit> boundaryNeighbours1 = manager.getBoundaryNeighbours(unitArea1);
		Java6Assertions.assertThat(boundaryNeighbours1).hasSize(1).contains(getRowUnit('B'));
		
		UnitArea unitArea2 = new UnitArea(RowUnit.class);
		unitArea2.add(getRowUnit('D'));
		unitArea2.add(getRowUnit('E'));
		unitArea2.add(getRowUnit('F'));
		Set<Unit> boundaryNeighbours2 = manager.getBoundaryNeighbours(unitArea2);
		Java6Assertions.assertThat(boundaryNeighbours2).hasSize(2).contains(getRowUnit('C'), getRowUnit('G'));
	}

	@Test
	public void squareUnit_GetBoundaryNeighboursTest() {
		UnitAreaCombinationManager manager = new UnitAreaCombinationManager(unitManager);
		
		UnitArea unitArea1 = new UnitArea(SquareUnit.class);
		unitArea1.add(getSquareUnit('A', '1'));
		Set<Unit> boundaryNeighbours1 = manager.getBoundaryNeighbours(unitArea1);
		Java6Assertions.assertThat(boundaryNeighbours1).hasSize(2).contains(getSquareUnit('D', '1'), getSquareUnit('A', '4'));
		
		UnitArea unitArea2 = new UnitArea(SquareUnit.class);
		unitArea2.add(getSquareUnit('D', '4'));
		unitArea2.add(getSquareUnit('D', '1'));
		Set<Unit> boundaryNeighbours2 = manager.getBoundaryNeighbours(unitArea2);
		Java6Assertions.assertThat(boundaryNeighbours2)
			.hasSize(5)
			.contains(getSquareUnit('A', '1'), 
					  getSquareUnit('A', '4'),
					  getSquareUnit('G', '1'),
					  getSquareUnit('G', '4'),
					  getSquareUnit('D', '7')
					  );
	}
	
	
	@Test
	public void rowUnit_getUnitAreaCombinationsTest() {
		UnitAreaCombinationManager manager = new UnitAreaCombinationManager(unitManager);

		Set<UnitArea> unitAreas = manager.getUnitAreaCombinations(RowUnit.class);
		Java6Assertions.assertThat(unitAreas)
			.isNotNull().hasSize(45)
			.haveExactly(1, new RowUnitAreaCondition(getRowUnit('A')))
			.haveExactly(1, new RowUnitAreaCondition(getRowUnit('A'), getRowUnit('B')))
			.haveExactly(1, new RowUnitAreaCondition(getRowUnit('A'), getRowUnit('B'), getRowUnit('C')))
			.haveExactly(1, new RowUnitAreaCondition(getRowUnit('G')))
			.doNotHave(new RowUnitAreaCondition(getRowUnit('A'), getRowUnit('C')))
			;
		
	}

	private RowUnit getRowUnit(char row) {
		Optional<RowUnit> result = unitManager.getUnit(row, '1', RowUnit.class);
		if (result.isPresent()) return result.get();
		else throw new RuntimeException("Bad row : "+row);
	}

	private SquareUnit getSquareUnit(char row, char column) {
		Optional<SquareUnit> result = unitManager.getUnit(row, column, SquareUnit.class);
		if (result.isPresent()) return result.get();
		else throw new RuntimeException(String.format("Bad row, column : %s,%s", row, column));
	}
	
	private class RowUnitAreaCondition extends Condition<UnitArea> {
		private List<RowUnit> rowUnits;
		public RowUnitAreaCondition(RowUnit... rowUnits) {
			this.rowUnits = Arrays.asList(rowUnits);
		}

		@Override
		public boolean matches(UnitArea value) {
			if (value.getUnits().size() != rowUnits.size()) return false;
			return (value.getUnits().containsAll(rowUnits));
		}
		
	}
}
