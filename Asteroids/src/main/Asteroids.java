package main;

import javax.swing.JFrame;

import main.GamePanel;

public class Asteroids {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Asteroids!");
		frame.add(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}

}

