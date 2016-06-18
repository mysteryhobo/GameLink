package org.gamelink.game;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KalahDisplay {
	/** The width of the JFrame that will display the gameBoard */
		protected final int FRAMEWIDTH = 550;

		/** The height of the JFrame that will display the gameBoard */
		protected final int FRAMEHEIGHT = 160;

		/** The frame on which the gameBoard will be displayed */
		protected JFrame displayFrame;

		public KalahDisplay(int pNum) {
			displayFrame = new JFrame("Player: " + pNum);
			displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			displayFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
			displayFrame.setVisible(true);
		}

		public JFrame getJFrame(){
			return displayFrame;
		}
}