package com.matt.sudoku.killer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.killer.domain.KillerUnit;
import com.matt.sudoku.killer.xml.XmlKillerUnit;
import com.matt.sudoku.killer.xml.XmlKillerUnitContainer;

@Component
public class SaveActionListener implements ActionListener {
	private final Function<KillerUnit, XmlKillerUnit> xmlToKillerUnitTransformer;
	private final UnitManager unitManager;

	@Autowired
	public SaveActionListener(Function<KillerUnit, XmlKillerUnit> xmlToKillerUnitTransformer, UnitManager unitManager) {
		super();
		this.xmlToKillerUnitTransformer = xmlToKillerUnitTransformer;
		this.unitManager = unitManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	try {
    		XmlKillerUnitContainer killerUnitContainer = new XmlKillerUnitContainer();
    		Set<XmlKillerUnit> xmlUnits = unitManager.getUnits(KillerUnit.class).stream().map(xmlToKillerUnitTransformer).collect(Collectors.toSet());
			killerUnitContainer.setUnits(xmlUnits);
    		
    		File file = new File("killerSudoku.xml");
    		JAXBContext jaxbContext = JAXBContext.newInstance(XmlKillerUnitContainer.class);
    		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

    		// output pretty printed
    		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    		jaxbMarshaller.marshal(killerUnitContainer, file);
    		jaxbMarshaller.marshal(killerUnitContainer, System.out);
    		
    	} catch (JAXBException ex) {
    		throw new RuntimeException(ex);
    	}
	}
}
