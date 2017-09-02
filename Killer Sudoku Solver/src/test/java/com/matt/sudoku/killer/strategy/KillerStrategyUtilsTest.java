package com.matt.sudoku.killer.strategy;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.api.Condition;
import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.Column;
import com.matt.sudoku.commons.domain.Row;
import com.matt.sudoku.killer.domain.KillerCombination;
import com.matt.sudoku.killer.domain.KillerUnit;

public class KillerStrategyUtilsTest {

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
	public void testGetCombinations_1Box() {
		KillerUnit killerUnit = new KillerUnit(boxMap.getBoxArray("A1"), 7);
		KillerStrategyUtils utils = new KillerStrategyUtils();
		List<KillerCombination> combos = utils.getCombinations(killerUnit);
		Java6Assertions.assertThat(combos)
					.hasSize(1)
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 7));
		
		
	}
	
	@Test
	public void testGetCombinations_1Box_invalid() {
		KillerUnit killerUnit = new KillerUnit(boxMap.getBoxArray("A1"), 11);
		KillerStrategyUtils utils = new KillerStrategyUtils();
		List<KillerCombination> combos = utils.getCombinations(killerUnit);
		Java6Assertions.assertThat(combos).isNull();
	}
	
	@Test
	public void testGetCombinations_2Box() {
		KillerUnit killerUnit = new KillerUnit(boxMap.getBoxArray("A1", "A2"), 7);
		KillerStrategyUtils utils = new KillerStrategyUtils();
		List<KillerCombination> combos = utils.getCombinations(killerUnit);
		Java6Assertions.assertThat(combos)
					.hasSize(3)
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 1, 6))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 2, 5))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 3, 4));
	}
	
	@Test
	public void testGetCombinations_3Box() {
		KillerUnit killerUnit = new KillerUnit(boxMap.getBoxArray("A1", "A2", "A3"), 16);
		KillerStrategyUtils utils = new KillerStrategyUtils();
		List<KillerCombination> combos = utils.getCombinations(killerUnit);
		Java6Assertions.assertThat(combos)
					.hasSize(8)
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 1, 6, 9))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 1, 7, 8))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 2, 5, 9))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 2, 6, 8))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 3, 4, 9))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 3, 5, 8))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 3, 6, 7))
					.haveAtLeastOne(new KillerCombinationCondition(killerUnit, 4, 5, 7));
	}
	
	class KillerCombinationCondition extends Condition<KillerCombination> {
		private final KillerUnit killerUnit;
		private final List<Integer> values;
		
		public KillerCombinationCondition(KillerUnit killerUnit, Integer... values) {
			super();
			this.killerUnit = killerUnit;
			this.values = Arrays.asList(values);
		}

		@Override
		public boolean matches(KillerCombination value) {
			if (!value.getKillerUnit().equals(killerUnit)) return false;
			if (value.getValues().size() != values.size()) return false;
			return value.getValues().containsAll(values);
		}
	}


}
