import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class InitialGui {

	public InitialGui() {
		init();
	}
	
	public void init() {
		JFrame frame = new JFrame("Front Page");
		frame.setPreferredSize(new Dimension(1000,500));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setLocation(0, 0);
        
        JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);						// setting color
		frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
		
		JPanel midPanelC = new JPanel();
		midPanelC.setBackground(Color.WHITE);


		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.BLUE);


		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.WHITE);
	    
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.YELLOW);

		// add panel to frame
		frame.getContentPane().add(midPanelC, BorderLayout.CENTER);
//		frame.getContentPane().add(midPanelL, BorderLayout.LINE_START);
//		frame.getContentPane().add(midPanelR, BorderLayout.LINE_END);
//		frame.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		
		
		
		JButton startGame = new JButton("Create Game");
		startGame.setPreferredSize(new Dimension(100,100));
		midPanelC.add(startGame);
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				topPanel.setToolTipText("");
				new Run();
			}
		});
		
		
		frame.pack();
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		InitialGui g = new InitialGui();
		
		

	}

}
