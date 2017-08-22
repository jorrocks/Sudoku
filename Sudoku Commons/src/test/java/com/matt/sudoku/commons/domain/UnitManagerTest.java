package com.matt.sudoku.commons.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;

public class UnitManagerTest {
	private List<Row> rows;
	private List<Column> columns;
	private BoxMap boxMap;
	private RowUnit rUnitA;
	private RowUnit rUnitI;
	private ColumnUnit cUnit1;
	private ColumnUnit cUnit9;
	private SquareUnit sUnitTopLeft;
	private SquareUnit sUnitMiddle;
	private UnitManager unitManager;

	@Before
	public void setup() {
		rows = Row.create("ABCDEFGHI");
		columns = Column.create("123456789");
		boxMap = new BoxMap(Box.create(rows,  columns));
		
		sUnitTopLeft = new SquareUnit(boxMap.get("A1"), boxMap);
		sUnitMiddle = new SquareUnit(boxMap.get("D4"), boxMap);
		rUnitA = new RowUnit(rows.get(0), boxMap);
		rUnitI = new RowUnit(rows.get(8), boxMap);
		cUnit1 = new ColumnUnit(columns.get(0), boxMap);
		cUnit9 = new ColumnUnit(columns.get(8), boxMap);
		List<Unit> units = Arrays.asList(sUnitTopLeft, sUnitMiddle, rUnitA, rUnitI, cUnit1, cUnit9);
		unitManager = new UnitManager(units);		
	}
	
	@Test
	public void getIntersectingBoxesTest() {
		
		Set<Box> boxes = unitManager.getIntersectingBoxes(sUnitTopLeft, rUnitA);
		Java6Assertions.assertThat(boxes).hasSize(3).contains(boxMap.getBoxArray("A1", "A2", "A3"));
		
	}
	
	@Test
	public void getUnitsTest() {
		Set<RowUnit> rowUnits = unitManager.getUnits(RowUnit.class);
		Java6Assertions.assertThat(rowUnits).hasSize(2).contains(rUnitA, rUnitI);
	}
	
	@Test
	public void getAllUnitsTest() {
		Set<Unit> allUnits = unitManager.getAllUnits();
		Java6Assertions.assertThat(allUnits).hasSize(6).contains(sUnitTopLeft, sUnitMiddle, rUnitA, rUnitI, cUnit1, cUnit9);
	}
	
	@Test
	public void getIntersectingUnitsTest() {
		Set<Unit> units = unitManager.getIntersectingUnits(sUnitTopLeft);
		Java6Assertions.assertThat(units).hasSize(2).contains(rUnitA, cUnit1);
		
		units = unitManager.getIntersectingUnits(sUnitMiddle);
		Java6Assertions.assertThat(units).hasSize(0);
	}
	
	@Test
	public void getBoxesConnectedThroughUnitsTest() {
		Set<Box> boxes = unitManager.getBoxesConnectedThroughABoxesUnits(boxMap.get("A1"));
		Java6Assertions.assertThat(boxes).hasSize(20).contains(boxMap.getBoxArray(
				      "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", 
				"B1", "B2", "B3", "C1", "C2", "C3", 
				"D1", "E1", "F1", "G1", "H1", "I1"));
	}
}
