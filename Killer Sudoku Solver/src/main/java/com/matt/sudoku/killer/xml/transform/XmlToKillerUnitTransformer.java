package com.matt.sudoku.killer.xml.transform;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.killer.domain.KillerUnit;
import com.matt.sudoku.killer.xml.XmlBox;
import com.matt.sudoku.killer.xml.XmlKillerUnit;

@Component
public class XmlToKillerUnitTransformer implements Function<KillerUnit, XmlKillerUnit> {

	private final Function<Box, XmlBox> xmlToKillerBoxTransformer;
	
	@Autowired
	public XmlToKillerUnitTransformer(Function<Box, XmlBox> xmlToKillerBoxTransformer) {
		this.xmlToKillerBoxTransformer = xmlToKillerBoxTransformer;
	}


	@Override
	public XmlKillerUnit apply(KillerUnit killerUnit) {
		XmlKillerUnit result = new XmlKillerUnit();
		result.setTotal(killerUnit.getTotal());
		Set<XmlBox> xmlBoxes = killerUnit.boxes().stream().map(xmlToKillerBoxTransformer).collect(Collectors.toSet());
		result.setBoxes(xmlBoxes);
		return result;
	}
}
