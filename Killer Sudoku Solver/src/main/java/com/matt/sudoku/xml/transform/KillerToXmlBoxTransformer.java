package com.matt.sudoku.xml.transform;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.xml.XmlBox;

@Component
public class KillerToXmlBoxTransformer implements Function<XmlBox, Box> {

	private final BoxMap boxMap;

	@Autowired
	public KillerToXmlBoxTransformer(BoxMap boxMap) {
		this.boxMap = boxMap;
	}

	@Override
	public Box apply(XmlBox xmlBox) {
		return boxMap.get(xmlBox.getCoordinates());
	}

}
