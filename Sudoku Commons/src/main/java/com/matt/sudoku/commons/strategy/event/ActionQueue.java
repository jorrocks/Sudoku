package com.matt.sudoku.commons.strategy.event;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;

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

	/**
	 * Event fired to signify that a value was reduced from a Box
	 */
	public void fireReducedEvent(Integer valueReduced, Box boxInUnit) {
		// TODO work out which actions need to be created
		// create them and add to the queue.
		//throw new RuntimeException("not impl");
	}
}
