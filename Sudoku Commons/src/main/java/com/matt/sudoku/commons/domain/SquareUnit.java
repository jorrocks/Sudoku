package com.matt.sudoku.commons.domain;

import java.io.Serializable;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.CustomUnit;

public class SquareUnit extends CustomUnit implements Serializable {
	private static final long serialVersionUID = -8634297359676765831L;

	public SquareUnit(Box topLeft, BoxMap boxMap) {
		super(inferBoxArrayFromTopLeft(topLeft, boxMap));
	}
	
	private static Box[] inferBoxArrayFromTopLeft(Box topLeft, BoxMap boxMap) {
		Box[] boxes = new Box[9];
		final char tlRow = topLeft.getRow().getChar();
		final char tlColumn = topLeft.getColumn().getChar();
		for (int r=0 ; r<3 ; r++) {
			for (int c=0 ; c<3 ; c++) {
				char aRow = (char) (tlRow + r);
				char aColumn = (char) (tlColumn + c);
				boxes[3*r + c] = boxMap.get(aRow, aColumn);
			}
		}
		return boxes;
	}

	@Override
	public String toString() {
		return String.format("SquareUnit: %s...", boxes.stream().findFirst().orElseThrow(() -> new RuntimeException("SquareUnit contained no boxes.")));
	}
	
	@Override
	public boolean isNeighbour(Unit unit) {
		if (unit == null || !(unit instanceof SquareUnit)) return false;
		SquareUnit other = (SquareUnit)unit;
		Box aBox = this.boxes.iterator().next();
		return other.boxes.stream().anyMatch(ob -> isThreeBoxesAway(aBox, ob));
	}

	private static boolean isThreeBoxesAway(Box a, Box b) {
		if (a.getRow().getChar() == b.getRow().getChar() 
				&& Math.abs(a.getColumn().getChar() - b.getColumn().getChar()) == 3) {
			return true;
		}
		if (a.getColumn().getChar() == b.getColumn().getChar() 
				&& Math.abs(a.getRow().getChar() - b.getRow().getChar()) == 3) {
			return true;
		}
		return false;
	}
}
