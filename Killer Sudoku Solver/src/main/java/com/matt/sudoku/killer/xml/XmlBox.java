package com.matt.sudoku.killer.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XmlBox {
	private String coordinates;

	public XmlBox() {
	}

	public String getCoordinates() {
		return coordinates;
	}

	@XmlAttribute
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
}
