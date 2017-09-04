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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boxes == null) ? 0 : boxes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomUnit other = (CustomUnit) obj;
		if (boxes == null) {
			if (other.boxes != null)
				return false;
		} else if (!boxes.equals(other.boxes))
			return false;
		return true;
	}
}
