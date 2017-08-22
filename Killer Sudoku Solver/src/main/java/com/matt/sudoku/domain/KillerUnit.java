package com.matt.sudoku.domain;

import java.io.Serializable;

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


}
