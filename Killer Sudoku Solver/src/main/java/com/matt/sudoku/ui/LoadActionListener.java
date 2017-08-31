package com.matt.sudoku.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.domain.KillerUnit;
import com.matt.sudoku.ui.event.EventMulticaster;
import com.matt.sudoku.xml.XmlKillerUnit;
import com.matt.sudoku.xml.XmlKillerUnitContainer;

@Component
public class LoadActionListener implements ActionListener {
	private final Function<XmlKillerUnit, KillerUnit> killerToXmlUnitTransformer;
	private final EventMulticaster eventMulticaster;
	
	@Autowired
	public LoadActionListener(Function<XmlKillerUnit, KillerUnit> killerToXmlUnitTransformer,
			EventMulticaster eventMulticaster) {
		super();
		this.killerToXmlUnitTransformer = killerToXmlUnitTransformer;
		this.eventMulticaster = eventMulticaster;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	try {
    		File file = new File("killerSudoku.xml");
    		JAXBContext jaxbContext = JAXBContext.newInstance(XmlKillerUnitContainer.class);

    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		XmlKillerUnitContainer killerUnitContainer = (XmlKillerUnitContainer) jaxbUnmarshaller.unmarshal(file);
    	    
    	    for (KillerUnit killerUnit : killerUnitContainer.getUnits().stream().map(killerToXmlUnitTransformer).collect(Collectors.toList())) {
    	    	Set<String> boxCoordSet = killerUnit.boxes().stream().map(b -> b.toString()).collect(Collectors.toSet());
				eventMulticaster.publishKillerUnitEntered(e.getSource(), boxCoordSet, killerUnit.getTotal());
    	    }
    	} catch (JAXBException ex) {
    		throw new RuntimeException(ex);
		}
	}
}
