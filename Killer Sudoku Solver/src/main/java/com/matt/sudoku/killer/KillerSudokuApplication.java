package com.matt.sudoku.killer;

import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.matt.sudoku.killer.ui.KillerSudokuMainFrame;

@SpringBootApplication
public class KillerSudokuApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(KillerSudokuApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            KillerSudokuMainFrame ex = ctx.getBean(KillerSudokuMainFrame.class);
            ex.setVisible(true);
        });
	}
}
