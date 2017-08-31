package com.matt.sudoku.xml.transform;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.domain.KillerUnit;
import com.matt.sudoku.xml.XmlBox;
import com.matt.sudoku.xml.XmlKillerUnit;

@Component
public class KillerToXmlUnitTransformer implements Function<XmlKillerUnit, KillerUnit> {

	@Autowired
	private Function<XmlBox, Box> killerToXmlBoxTransformer;

	@Override
	public KillerUnit apply(XmlKillerUnit unit) {
		Box[] boxArray = unit.getBoxes().stream().map(killerToXmlBoxTransformer).toArray(Box[]::new);
		KillerUnit result = new KillerUnit(boxArray, unit.getTotal());
		return result;
	}

}
