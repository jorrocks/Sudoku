package com.matt.sudoku.xml.transform;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.xml.XmlBox;

@Component
public class XmlToKillerBoxTransformer implements Function<Box, XmlBox> {

	@Override
	public XmlBox apply(Box box) {
		XmlBox result = new XmlBox();
		result.setCoordinates(box.toString());
		return result;
	}
}
