package com.matt.sudoku.killer.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.matt.sudoku.killer.domain.KillerCombination;
import com.matt.sudoku.killer.domain.KillerUnit;

public class KillerStrategyUtils {
	public List<KillerCombination> getCombinations(KillerUnit killerUnit) {
		List<SortedSet<Integer>> comboList = findCombinations(killerUnit.getTotal(), killerUnit.boxes().size(), new TreeSet<>(IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet())));
		if (comboList == null) return null;
		return comboList.stream().map(s->new KillerCombination(killerUnit, s)).collect(Collectors.toList());
	}
	
	private List<SortedSet<Integer>> findCombinations(Integer total, Integer size, SortedSet<Integer> available) {
		if (size == 1) {
			if (available.contains(total)) {
				SortedSet<Integer> singleSet = new TreeSet<>();
				singleSet.add(total);
				List<SortedSet<Integer>> result = new ArrayList<>();
				result.add(singleSet);
				return result;
			} else return null;
		} else {
			List<SortedSet<Integer>> result = new ArrayList<>();
			for (Integer element : available) {
				if (element < total) {
					Integer nextTotal = total - element;
					Integer nextSize = size - 1;
					if (nextTotal <= element) break;
					SortedSet<Integer> tailSet = available.tailSet(element + 1);
					if (tailSet.size() >= nextSize) {
						List<SortedSet<Integer>> nextResult = findCombinations(nextTotal, nextSize, tailSet);
						if (nextResult != null) {
							nextResult.forEach(ss->{
								ss.add(element);
								result.add(ss);
							});
						}
					}
				}
			}
			return result.isEmpty() ? null : result;
		}
	}
}
