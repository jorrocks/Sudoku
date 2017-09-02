package com.matt.sudoku.killer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.IntersectingUnits;

@Component
public class PrintActionListener implements ActionListener {
	private static final Logger log = LoggerFactory.getLogger(PrintActionListener.class);
	private final SudokuGridManager gridManager;
	
	public PrintActionListener(SudokuGridManager gridManager) {
		super();
		this.gridManager = gridManager;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		log.info(gridManager.getGrid().print());
	}

}
