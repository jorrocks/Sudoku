package com.matt.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Java6Assertions;
import org.junit.Test;

public class LambdaTests {
	@Test
	public void setCollectIntersectionTest() {
		Set<Integer> set1 = new HashSet<>();
		set1.addAll(Arrays.asList(1, 2, 3));
		Set<Integer> set2 = new HashSet<>();
		set2.addAll(Arrays.asList(1, 2, 3, 4));
		Set<Integer> set3 = new HashSet<>();
		set3.addAll(Arrays.asList(1, 2));
		Set<Integer> set4 = new HashSet<>();
		set4.addAll(Arrays.asList(1, 2, 4));
		
		List<Set<Integer>> listOfSets = Arrays.asList(set1, set2, set3, set4);
		
		Set<Integer> intersection = listOfSets.stream().skip(1)
			    .collect(()->new HashSet<>(listOfSets.get(0)), Set::retainAll, Set::retainAll);
		
		Java6Assertions.assertThat(intersection).hasSize(2).contains(1, 2);
	}
}
