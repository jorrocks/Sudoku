package com.matt.sudoku.killer.domain;

import java.io.Serializable;
import java.util.stream.Collectors;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.CustomUnit;

public class KillerUnit extends CustomUnit implements Serializable {
	private static final long serialVersionUID = 1601136597127939745L;
	
	private final int total;

	public KillerUnit(Box[] boxArray, int total) {
		super(boxArray);
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return String.format("KillerUnit [total=%s, boxes=%s]", total, String.join(",", boxes.stream().map(b->b.toString()).collect(Collectors.toList())));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((boxes == null) ? 0 : boxes.hashCode());
		result = prime * result + total;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KillerUnit other = (KillerUnit) obj;
		if (boxes == null) {
			if (other.boxes != null)
				return false;
		} else if (!boxes.equals(other.boxes))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
}
