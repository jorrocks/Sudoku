package com.matt.sudoku.killer.xml;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlKillerUnitContainer {
	private Set<XmlKillerUnit> units;

	public Set<XmlKillerUnit> getUnits() {
		return units;
	}

	@XmlElement
	public void setUnits(Set<XmlKillerUnit> units) {
		this.units = units;
	}
}
