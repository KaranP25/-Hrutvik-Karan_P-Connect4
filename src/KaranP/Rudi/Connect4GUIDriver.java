package KaranP.Rudi;

import javax.swing.*;

public class Connect4GUIDriver {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Presentation Tracker");		
		frame.setSize(900,900);
		frame.getContentPane().add(new Connect4Panel());
		frame.pack();
		frame.setVisible(true);
		frame.setFocusable(false);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
