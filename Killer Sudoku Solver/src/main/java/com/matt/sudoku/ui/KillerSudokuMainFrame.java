package com.matt.sudoku.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.ui.event.EventMulticaster;
import com.matt.sudoku.ui.event.KillerUnitTotalEnteredEvent;

@Component
public class KillerSudokuMainFrame extends JFrame implements ApplicationListener<KillerUnitTotalEnteredEvent> {

	private final EventMulticaster eventMulticaster;
	private final UnitManager unitManager;
	private Map<String, JToggleButton> buttons;
	private JButton quitButton;
	private JButton saveButton;
	private JButton solveButton;
	private JButton loadButton;
	
	@Autowired
    public KillerSudokuMainFrame(EventMulticaster eventMulticaster, UnitManager unitManager) {
		this.eventMulticaster = eventMulticaster;
		this.unitManager = unitManager;
        initUI();
    }

    private void initUI() {

        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        solveButton = new JButton("Solve");
        quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        buttons = new HashMap<>();
        IntStream.range('A', 'I'+1).forEach(c ->
        	IntStream.range(1, 10).forEach(r -> {
            	String key = String.format("%s%s", r, (char)c);
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
						if (totalStr.isEmpty() == false) {
							eventMulticaster.publishKillerUnitEntered((JToggleButton) e.getSource(), Integer.parseInt(totalStr));
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
		        for (char ri=(char)('1'+ro);ri<='3'+ro;ri++) {
		        	for (char ci=(char)('A'+co);ci<='C'+co;ci++) {
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
		Map<String, JToggleButton> thisUnitMap = buttons.entrySet().stream()
				.filter(e -> e.getValue().isEnabled() && e.getValue().isSelected())
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		thisUnitMap.entrySet().stream().forEach(e -> e.getValue().setBorder(DashedBorder.createDashedBorder(Color.darkGray, 2, 2, 2, false, 
				isAboveInMap(e.getKey(), thisUnitMap), isBelowInMap(e.getKey(), thisUnitMap), isLeftInMap(e.getKey(), thisUnitMap), isRightInMap(e.getKey(), thisUnitMap))));
		thisUnitMap.values().stream().forEach(b -> b.setEnabled(false));
		thisUnitMap.values().stream().forEach(b -> b.setText(""));
		String topLeftButtonKey = thisUnitMap.keySet().stream().min((s1, s2) -> s1.compareTo(s2)).get();
		thisUnitMap.get(topLeftButtonKey).setText(String.valueOf(event.getKillerUnitTotal()));
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