package com.matt.sudoku.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.Box;
import com.matt.sudoku.commons.domain.BoxMap;
import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.domain.KillerUnit;
import com.matt.sudoku.ui.event.EventMulticaster;
import com.matt.sudoku.ui.event.KillerUnitTotalEnteredEvent;
import com.matt.sudoku.xml.XmlKillerUnit;
import com.matt.sudoku.xml.XmlKillerUnitContainer;

@Component
public class KillerSudokuMainFrame extends JFrame implements ApplicationListener<KillerUnitTotalEnteredEvent> {

	private final EventMulticaster eventMulticaster;
	private final UnitManager unitManager;
	private final BoxMap boxMap;
	private final Function<KillerUnit, XmlKillerUnit> xmlToKillerUnitTransformer;
	private final Function<XmlKillerUnit, KillerUnit> killerToXmlUnitTransformer;
	private Map<String, JToggleButton> buttons;
	private JButton quitButton;
	private JButton saveButton;
	private JButton solveButton;
	private JButton loadButton;
	
	@Autowired
    public KillerSudokuMainFrame(EventMulticaster eventMulticaster, UnitManager unitManager, BoxMap boxMap, 
    		Function<KillerUnit, XmlKillerUnit> xmlToKillerUnitTransformer,
    		Function<XmlKillerUnit, KillerUnit> killerToXmlUnitTransformer) {
		this.eventMulticaster = eventMulticaster;
		this.unitManager = unitManager;
		this.boxMap = boxMap;
		this.xmlToKillerUnitTransformer = xmlToKillerUnitTransformer;
		this.killerToXmlUnitTransformer = killerToXmlUnitTransformer;
        initUI();
    }

    private void initUI() {

        loadButton = new JButton("Load");
        loadButton.addActionListener((ActionEvent e) -> {
        	try {
        		File file = new File("killerSudoku.xml");
        		JAXBContext jaxbContext = JAXBContext.newInstance(XmlKillerUnitContainer.class);

        		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        		XmlKillerUnitContainer killerUnitContainer = (XmlKillerUnitContainer) jaxbUnmarshaller.unmarshal(file);
        	    
        	    for (KillerUnit killerUnit : killerUnitContainer.getUnits().stream().map(killerToXmlUnitTransformer).collect(Collectors.toList())) {
        	    	Set<String> boxCoordSet = killerUnit.boxes().stream().map(b -> b.toString()).collect(Collectors.toSet());
    				eventMulticaster.publishKillerUnitEntered(loadButton, boxCoordSet, killerUnit.getTotal());
        	    }
        	} catch (JAXBException ex) {
        		throw new RuntimeException(ex);
			}
        });
        saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) -> {
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
        });
        solveButton = new JButton("Solve");
        quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        buttons = new HashMap<>();
        IntStream.range('A', 'I'+1).forEach(r ->
        	IntStream.range(1, 10).forEach(c -> {
            	String key = String.format("%s%s", (char)r, c);
        		buttons.put(key, new JToggleButton(key ) );
        	})
        );
        
        createLayout(loadButton, saveButton, solveButton, quitButton, buttons);

        setTitle("Quit button");
        setSize(900, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JButton load, JButton save, JButton solve, JButton quit, Map<String, JToggleButton> cellButtonMap) {

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

    	List<Character> keyStack = new ArrayList<>();
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						String totalStr = keyStack.stream()
								.filter(c -> c >= '0' && c <= '9')
								.map(c -> String.valueOf(c)).collect(Collectors.joining());
						keyStack.clear();
						
						Set<String> coords = buttons.entrySet().stream()
								.filter(es -> es.getValue().isEnabled() && es.getValue().isSelected())
								.map(es -> es.getKey())
								.collect(Collectors.toSet());
						
						if (totalStr.isEmpty() == false) {
							eventMulticaster.publishKillerUnitEntered(e.getSource(), coords, Integer.parseInt(totalStr));
						}
					} else {
						keyStack.add(e.getKeyChar());
					}
				}
				return false;
			}
		});
        
        JToolBar jToolBar = new JToolBar();
        pane.add(jToolBar, BorderLayout.PAGE_START);
        jToolBar.add(load);
        jToolBar.add(save);
        jToolBar.add(solve);
        jToolBar.add(quit);
        
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(3, 3));
        pane.add(main, BorderLayout.CENTER);
        
        for (int ro=0;ro<9;ro=ro+3) {
        	for (int co=0;co<9;co=co+3) {
		        JPanel subPanel = new JPanel();
		        subPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		        subPanel.setLayout(new GridLayout(3, 3));
		        for (char ri=(char)('A'+ro);ri<='C'+ro;ri++) {
		        	for (char ci=(char)('1'+co);ci<='3'+co;ci++) {
		        		String key = String.format("%s%s", ri, ci);
		        		subPanel.add(cellButtonMap.get(key));
		        	}
		        }
		        main.add(subPanel);
        	}
        }
    }

	@Override
	public void onApplicationEvent(KillerUnitTotalEnteredEvent event) {
		// create a reduced map (only those selected) of the buttons keyed by coordinates
		Map<String, JToggleButton> selectedButtonMapByCoord = buttons.entrySet().stream()
				.filter(e -> event.getBoxCoordinates().contains(e.getKey()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		// change the button cell borders to a killer dashed border
		selectedButtonMapByCoord.entrySet().stream()
				.forEach(e -> e.getValue().setBorder(createBorder(selectedButtonMapByCoord, e.getKey())));
		// set selected.
		selectedButtonMapByCoord.values().stream().forEach(b -> b.setSelected(true));
		// disable the buttons
		selectedButtonMapByCoord.values().stream().forEach(b -> b.setEnabled(false));
		// clear the text
		selectedButtonMapByCoord.values().stream().forEach(b -> b.setText(""));
		// write the total in the top left button.
		String topLeftButtonKey = selectedButtonMapByCoord.keySet().stream().min((s1, s2) -> s1.compareTo(s2)).get();
		selectedButtonMapByCoord.get(topLeftButtonKey).setText(String.valueOf(event.getKillerUnitTotal()));
		
		Box[] boxArray = boxMap.getBoxArray(selectedButtonMapByCoord.keySet().stream().toArray(String[]::new));
		KillerUnit killerUnit = new KillerUnit(boxArray, event.getKillerUnitTotal());
		
		unitManager.addUnit(killerUnit);
	}

	private Border createBorder(Map<String, JToggleButton> selectedButtonMapByCoord, String coord) {
		Border border = DashedBorder.createDashedBorder(Color.darkGray, 2, 2, 2, false, 
				isAboveInMap(coord, selectedButtonMapByCoord), 
				isBelowInMap(coord, selectedButtonMapByCoord), 
				isLeftInMap(coord, selectedButtonMapByCoord), 
				isRightInMap(coord, selectedButtonMapByCoord));
		return border;
	}

	private static boolean isAboveInMap(String key, Map<String, JToggleButton> thisUnitMap) {
		char row = key.charAt(0);
		char col = key.charAt(1);
		return thisUnitMap.containsKey(String.valueOf(new char[]{(char) (row-1), col}));
	}

	private static boolean isBelowInMap(String key, Map<String, JToggleButton> thisUnitMap) {
		char row = key.charAt(0);
		char col = key.charAt(1);
		return thisUnitMap.containsKey(String.valueOf(new char[]{(char)(row+1), col}));
	}

	private static boolean isLeftInMap(String key, Map<String, JToggleButton> thisUnitMap) {
		char row = key.charAt(0);
		char col = key.charAt(1);
		return thisUnitMap.containsKey(String.valueOf(new char[]{row, (char)(col-1)}));
	}

	private static boolean isRightInMap(String key, Map<String, JToggleButton> thisUnitMap) {
		char row = key.charAt(0);
		char col = key.charAt(1);
		return thisUnitMap.containsKey(String.valueOf(new char[]{row, (char)(col+1)}));
	}
}