package bedroombattletanks;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jgame.platform.JGEngine;

public class ControlsGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JGEngine localEng;
	JPanel fullPanel = new JPanel();
	
	JPanel controlPanel = new JPanel();
	JLabel up = new JLabel("Up", JLabel.CENTER);
	JLabel down = new JLabel("Down", JLabel.CENTER);
	JLabel left = new JLabel("Left", JLabel.CENTER);
	JLabel right = new JLabel("Right", JLabel.CENTER);
	JLabel fire = new JLabel("Primary Fire", JLabel.CENTER);
	JLabel secondaryFire = new JLabel("Secondary Fire", JLabel.CENTER);
	
	JPanel controlp1 = new JPanel();
	JLabel labelPlayer1 = new JLabel("Player 1", JLabel.CENTER);
	JPanel linksp1 = new JPanel();
	JButton upp1 = new JButton("W");
	JButton downp1 = new JButton("S");
	JButton leftp1 = new JButton("A");
	JButton rightp1 = new JButton("D");
	JButton firep1 = new JButton("Q");
	JButton secondaryFirep1 = new JButton("E");
	
	JLabel labelPlayer2 = new JLabel("Player 2", JLabel.CENTER);
	JPanel linksp2 = new JPanel();
	JPanel controlp2 = new JPanel();
	JButton upp2 = new JButton("Up Arrow");
	JButton downp2 = new JButton("Down Arrow");
	JButton leftp2 = new JButton("Left Arrow");
	JButton rightp2 = new JButton("Right Arrow");
	JButton firep2 = new JButton("M");
	JButton secondaryFirep2 = new JButton(",");
	
	JPanel buildControls() {
		upp1.setActionCommand("change");
		upp1.addActionListener(this);
		
		controlPanel.setLayout(new GridLayout(7,1));
		controlPanel.add(new JLabel(""));
		controlPanel.add(up);
		controlPanel.add(down);
		controlPanel.add(left);
		controlPanel.add(right);
		controlPanel.add(fire);
		controlPanel.add(secondaryFire);
		
		controlp1.add(labelPlayer1);
		controlp1.setLayout(new GridLayout(7,1));
		controlp1.add(upp1);
		controlp1.add(downp1);
		controlp1.add(leftp1);
		controlp1.add(rightp1);
		controlp1.add(firep1);
		controlp1.add(secondaryFirep1);
		
		controlp2.add(labelPlayer2);
		controlp2.setLayout(new GridLayout(7,1));
		controlp2.add(upp2);
		controlp2.add(downp2);
		controlp2.add(leftp2);
		controlp2.add(rightp2);
		controlp2.add(firep2);
		controlp2.add(secondaryFirep2);
		
		fullPanel.setLayout(new GridLayout(1,3));
		fullPanel.add(controlPanel);
		fullPanel.add(controlp1);
		fullPanel.add(controlp2);		
		return fullPanel;
	}
	ControlsGUI(JGEngine eng) {
		localEng = eng;
		setVisible(true);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	fullPanel = buildControls();
    	add(fullPanel);
    	setSize(500, 300);
    	setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent e) {
		/*
		if ("change".equals(e.getActionCommand())) {
			((JButton) e.getSource()).setText("");
	//		localEng.clearLastKey();
	//		do {
	//			try { Thread.sleep(100); }
	//			catch (InterruptedException e2) {}
				localEng.dbgPrint(Integer.toString(localEng.getLastKey()));
	//			if (localEng.getLastKey()!=0)
					((JButton) e.getSource()).setText(localEng.getKeyDesc(localEng.getLastKey()));
	//		} while (localEng.getLastKey()==0);
		}
		*/
	}
}
