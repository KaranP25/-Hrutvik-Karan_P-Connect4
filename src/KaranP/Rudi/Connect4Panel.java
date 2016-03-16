package KaranP.Rudi;

import javax.swing.*;
import java.awt.*;

public class Connect4Panel extends JPanel{
	int ROWS = 9, COLUMNS = 2, HGAP = 10 , VGAP = 10;
	
	public Connect4Panel(){
		setPreferredSize(new Dimension(800, 800));
		setBackground(new Color(0, 0, 0));

		setLayout(new GridLayout(ROWS,COLUMNS,HGAP,VGAP));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}
}
