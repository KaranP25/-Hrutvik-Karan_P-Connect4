package KaranP.Rudi;

import javax.swing.*;

/**
 * This class is the driver which contains the JFrame and JPanel (SetUpPanel) with it's dimensions 
 * @author hrutvik and Karan 
 *
 */
public class ConnectFourGUIDriver {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Connect 4");		
		frame.setSize(900,900);
		frame.getContentPane().add(new SetUpPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setFocusable(false);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
