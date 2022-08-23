package view;

import javax.swing.JFrame;

public class RunGame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Game Mario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new View());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
