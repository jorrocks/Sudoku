package com.matt.sudoku.killer.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.commons.domain.RowUnit;

public class UnitAreaTest {

	private List<Row> rows;
	private List<Column> columns;
	private BoxMap boxMap;

	@Before
	public void setup() {
		rows = Row.create("ABCDEFGHI");
		columns = Column.create("123456789");
		boxMap = new BoxMap(Box.create(rows,  columns));
	}

	@Test
	public void testEquals() {
		RowUnit rowUnitA = new RowUnit(rows.get(0), boxMap);
		RowUnit rowUnitB = new RowUnit(rows.get(1), boxMap);
		
		Set<RowUnit> rowUnitSet1 = new HashSet<>();
		rowUnitSet1.add(rowUnitA);
		rowUnitSet1.add(rowUnitB);
		Set<RowUnit> rowUnitSet2 = new HashSet<>();
		rowUnitSet2.add(rowUnitA);
		
		UnitArea a1 = new UnitArea(RowUnit.class);
		rowUnitSet1.forEach(u -> a1.add(u));
		UnitArea a2 = new UnitArea(RowUnit.class);
		rowUnitSet1.forEach(u -> a2.add(u));
		UnitArea a3 = new UnitArea(RowUnit.class);
		rowUnitSet2.forEach(u -> a3.add(u));
		
		Java6Assertions.assertThat(a1 == a2).isFalse();
		Java6Assertions.assertThat(a1).isEqualTo(a2);
		
		Java6Assertions.assertThat(a1 == a3).isFalse();
		Java6Assertions.assertThat(a1).isNotEqualTo(a3);
	}
}
