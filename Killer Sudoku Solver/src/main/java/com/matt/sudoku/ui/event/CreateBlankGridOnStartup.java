package com.matt.sudoku.ui.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.matt.sudoku.commons.factory.SudokuGridManager;

@Component
public class CreateBlankGridOnStartup implements ApplicationListener<ApplicationReadyEvent> {
	private final SudokuGridManager gridManager;
	
	@Autowired
	public CreateBlankGridOnStartup(SudokuGridManager gridManager) {
		super();
		this.gridManager = gridManager;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		gridManager.buildGrid("........."+
				  "........."+
				  "........."+
				  "........."+
				  "........."+
				  "........."+
				  "........."+
				  "........."+
				  ".........");
	}

}
