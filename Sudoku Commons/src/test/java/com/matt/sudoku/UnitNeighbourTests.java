package com.matt.sudoku;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.ColumnUnit;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.commons.domain.RowUnit;
import com.matt.sudoku.commons.domain.SquareUnit;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;

public class UnitNeighbourTests {
	private List<Row> rows;
	private List<Column> columns;
	private BoxMap boxMap;
	private RowUnit rUnitA;
	private RowUnit rUnitB;
	private RowUnit rUnitC;
	private RowUnit rUnitD;
	private RowUnit rUnitI;
	private ColumnUnit cUnit1;
	private ColumnUnit cUnit9;
	private SquareUnit sUnitTopLeft;
	private SquareUnit sUnitTopMiddle;
	private SquareUnit sUnitMiddle;
	private UnitManager unitManager;

	@Before
	public void setup() {
		rows = Row.create("ABCDEFGHI");
		columns = Column.create("123456789");
		boxMap = new BoxMap(Box.create(rows,  columns));
		
		sUnitTopLeft = new SquareUnit(boxMap.get("A1"), boxMap);
		sUnitTopMiddle = new SquareUnit(boxMap.get("A4"), boxMap);
		sUnitMiddle = new SquareUnit(boxMap.get("D4"), boxMap);
		rUnitA = new RowUnit(rows.get(0), boxMap);
		rUnitB = new RowUnit(rows.get(1), boxMap);
		rUnitC = new RowUnit(rows.get(2), boxMap);
		rUnitD = new RowUnit(rows.get(3), boxMap);
		rUnitI = new RowUnit(rows.get(8), boxMap);
		cUnit1 = new ColumnUnit(columns.get(0), boxMap);
		cUnit9 = new ColumnUnit(columns.get(8), boxMap);
		List<Unit> units = Arrays.asList(sUnitTopLeft, sUnitMiddle, rUnitA, rUnitI, cUnit1, cUnit9);
		unitManager = new UnitManager(units);		
	}

	@Test
	public void rowNeighbourTest() {
		Java6Assertions.assertThat(rUnitA.isNeighbour(rUnitB)).isTrue();
		Java6Assertions.assertThat(rUnitB.isNeighbour(rUnitA)).isTrue();
		
		Java6Assertions.assertThat(rUnitA.isNeighbour(rUnitA)).isFalse();
		
		Java6Assertions.assertThat(rUnitA.isNeighbour(rUnitC)).isFalse();
	}

	@Test
	public void squareNeighbourTest() {
		Java6Assertions.assertThat(sUnitTopLeft.isNeighbour(sUnitTopMiddle)).isTrue();
		Java6Assertions.assertThat(sUnitTopMiddle.isNeighbour(sUnitTopLeft)).isTrue();
		
		Java6Assertions.assertThat(sUnitTopLeft.isNeighbour(sUnitTopLeft)).isFalse();
		
		Java6Assertions.assertThat(sUnitTopLeft.isNeighbour(sUnitMiddle)).isFalse();
	}
}
