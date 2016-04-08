package KaranP.Rudi;

import javax.swing.*;

/**
 * This class is the driver which contains the JFrame and initiates the main menu JPanel(SetUpPanel)
 * with it's dimensions 
 * @author Hrutvik & Karan
 * @version 1.0
 */
public class ConnectFourDriver {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Connect 4");	
		frame.getContentPane().add(new SetUpGUIPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setFocusable(false);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
