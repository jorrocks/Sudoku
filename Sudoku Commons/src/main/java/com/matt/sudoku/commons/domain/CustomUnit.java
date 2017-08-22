package com.matt.sudoku.commons.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUnit implements Unit, Serializable {
	private static final long serialVersionUID = 2743362547918175576L;
	protected final Set<Box> boxes;
	
	public CustomUnit(Box[] boxArray) {
		this.boxes = new HashSet<>(Arrays.asList(boxArray));
	}
	
	public Set<Box> boxes() {
		return boxes;
	}
	
	@Override
	public String toString() {
		return String.format("CustomUnit: %s", String.join(",", boxes.stream().map(b->b.toString()).collect(Collectors.toList())));
	}
}
