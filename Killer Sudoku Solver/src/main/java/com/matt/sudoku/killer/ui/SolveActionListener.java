package com.matt.sudoku.killer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.event.ActionQueue;
import com.matt.sudoku.commons.strategy.event.StrategyAction;
import com.matt.sudoku.killer.domain.KillerUnit;
import com.matt.sudoku.killer.strategy.event.KillerCombinationAction;

@Component
public class SolveActionListener implements ActionListener {
	private final ActionQueue queue;
	private final UnitManager unitManager;
	private final SudokuGridManager gridManager;

	@Autowired
	public SolveActionListener(ActionQueue queue, UnitManager unitManager, SudokuGridManager gridManager) {
		super();
		this.queue = queue;
		this.unitManager = unitManager;
		this.gridManager = gridManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Set<KillerUnit> killerUnits = unitManager.getUnits(KillerUnit.class);
		killerUnits.forEach(u->queue.add(new KillerCombinationAction(u)));
		
		
		StrategyAction action;
		while ((action = queue.pop()) != null) {
			action.execute(gridManager, queue);
		}
	}

}
