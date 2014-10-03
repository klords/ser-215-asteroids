package main;

import javax.swing.JFrame;

public class Asteroids {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Asteroids!");
		frame.setContentPane(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
