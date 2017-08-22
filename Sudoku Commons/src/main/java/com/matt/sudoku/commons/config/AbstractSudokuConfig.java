package com.matt.sudoku.commons.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.ColumnUnit;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.commons.domain.RowUnit;
import com.matt.sudoku.commons.domain.SquareUnit;
import com.matt.sudoku.commons.domain.Unit;
import com.matt.sudoku.commons.domain.UnitManager;

public abstract class AbstractSudokuConfig {

	@Bean
	public List<Row> rows() {
		return Row.create("ABCDEFGHI");
	}
	
	@Bean
	public List<Column> columns() {
		return Column.create("123456789");
	}
	
	@Bean
	public List<Box> boxes(List<Row> rows, List<Column> columns) {
		return Box.create(rows, columns);
	}
	
	@Bean
	public List<RowUnit> rowUnits(List<Row> rows, BoxMap boxMap) {
		List<RowUnit> result = new ArrayList<>();
		rows.stream().forEach(r -> result.add(new RowUnit(r, boxMap)));
		return result;
	}
	
	@Bean
	public List<ColumnUnit> columnUnits(List<Column> columns, BoxMap boxMap) {
		List<ColumnUnit> result = new ArrayList<>();
		columns.stream().forEach(r -> result.add(new ColumnUnit(r, boxMap)));
		return result;
	}
	
	@Bean
	public List<SquareUnit> squareUnits(BoxMap boxMap) {
		List<SquareUnit> result = new ArrayList<>();
		result.add(new SquareUnit(boxMap.get("A1"), boxMap));
		result.add(new SquareUnit(boxMap.get("A4"), boxMap));
		result.add(new SquareUnit(boxMap.get("A7"), boxMap));
		result.add(new SquareUnit(boxMap.get("D1"), boxMap));
		result.add(new SquareUnit(boxMap.get("D4"), boxMap));
		result.add(new SquareUnit(boxMap.get("D7"), boxMap));
		result.add(new SquareUnit(boxMap.get("G1"), boxMap));
		result.add(new SquareUnit(boxMap.get("G4"), boxMap));
		result.add(new SquareUnit(boxMap.get("G7"), boxMap));
		return result;
	}
	
	@Bean
	public BoxMap boxMap(List<Box> boxes) {
		return new BoxMap(boxes);
	}
	
	@Bean
	public UnitManager unitManager(List<Unit> units) {
		return new UnitManager(units);
	}
}
