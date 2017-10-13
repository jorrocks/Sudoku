package com.matt.sudoku.commons.domain;

import java.util.Set;

public interface Unit {
	public Set<Box> boxes();
	public boolean isNeighbour(Unit unit);
}
