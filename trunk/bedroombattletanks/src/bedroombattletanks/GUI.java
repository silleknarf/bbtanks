package bedroombattletanks;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jgame.platform.JGEngine;

//import statements
//Check if window closes automatically. Otherwise add suitable code
class GUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	JButton duel = new JButton("Duel");
	JButton CTF = new JButton("Capture the flag");
	JButton controls = new JButton("Controls");
	JButton quit = new JButton("Quit");
	GameInfo localInfo;
	JGEngine localEng;
	JLabel jlbHelloWorld = new JLabel("Hello World");
	
	


	
		GUI(GameInfo gameInfo, JGEngine eng) {
			localInfo = gameInfo;
			localEng = eng;
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(200, 400);
			setLocationRelativeTo(null);
			setVisible(true);
	        setLayout(new GridLayout(4,1));
			add(duel);
			add(CTF);
			add(controls);
			add(quit);
			
			duel.setActionCommand("duel");
			duel.addActionListener(this);
			CTF.setActionCommand("CTF");
			CTF.addActionListener(this);
			controls.setActionCommand("controls");
			controls.addActionListener(this);
			quit.setActionCommand("quit");
			quit.addActionListener(this);
			
		}
		public void actionPerformed(ActionEvent e) {
		    if ("duel".equals(e.getActionCommand())) {
		    	new Duel(localInfo);
				dispose();
				localEng.setBGImage("wooden-floor");
				localInfo.guiDone = true;
		    }
		    if ("CTF".equals(e.getActionCommand())) {
		    	new CTF(localInfo, localEng);
		    	dispose();
				localEng.setBGImage("wooden-floor");
		    	localInfo.guiDone = true;
		    }
		    if ("controls".equals(e.getActionCommand())) {
		    	new ControlsGUI(localEng);			
		    }
		    if ("quit".equals(e.getActionCommand())) {
		    	System.exit(0);
		    }
		}
}
