package com.matt.sudoku.xml;

import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XmlKillerUnit {
	private int total;
	private Set<XmlBox> boxes;
	
	public XmlKillerUnit() {
		super();
	}
	
	public Set<XmlBox> getBoxes() {
		return boxes;
	}

	@XmlElement
	public void setBoxes(Set<XmlBox> boxes) {
		this.boxes = boxes;
	}

	public int getTotal() {
		return total;
	}

	@XmlAttribute
	public void setTotal(int total) {
		this.total = total;
	}
	
}
