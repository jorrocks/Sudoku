package com.matt.sudoku.commons.strategy.event;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
public class ActionQueue {
	private SortedSet<StrategyAction> queue = new TreeSet<>();
	
	public void add(StrategyAction action) {
		queue.add(action);
	}
	
	public StrategyAction pop() {
		if (queue.isEmpty()) return null;
		else {
			StrategyAction element = queue.first();
			queue.remove(element);
			return element;
		}
	}
}
