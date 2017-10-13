package com.matt.sudoku.killer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.domain.UnitManager;
import com.matt.sudoku.commons.factory.SudokuGridManager;
import com.matt.sudoku.commons.strategy.event.ActionFactory;
import com.matt.sudoku.commons.strategy.event.ActionQueue;
import com.matt.sudoku.commons.strategy.event.StrategyAction;
import com.matt.sudoku.commons.strategy.event.StrategyEvent;
import com.matt.sudoku.killer.domain.KillerUnit;
import com.matt.sudoku.killer.strategy.action.LookForUniqueCombinationOnUnit;

@Component
public class SolveActionListener implements ActionListener {
	private static final Logger log = LoggerFactory.getLogger(SolveActionListener.class);

	private final ActionQueue queue;
	private final UnitManager unitManager;
	private final SudokuGridManager gridManager;
	private final ActionFactory actionFactory;

	@Autowired
	public SolveActionListener(ActionQueue queue, UnitManager unitManager, SudokuGridManager gridManager, ActionFactory actionFactory) {
		super();
		this.queue = queue;
		this.unitManager = unitManager;
		this.gridManager = gridManager;
		this.actionFactory = actionFactory;
	}

	@Override
	public void actionPerformed(ActionEvent uiEvent) {
		Set<KillerUnit> killerUnits = unitManager.getUnits(KillerUnit.class);
		killerUnits.forEach(u->queue.add(new LookForUniqueCombinationOnUnit(u)));

		StrategyAction action;
		while ((action = queue.pop()) != null) {
			List<StrategyEvent> events = action.execute(gridManager);
			events.forEach(newEvent->queue.addAll(actionFactory.buildActions(newEvent)));
		}
		
		log.info(gridManager.getGrid().print());
	}

}
