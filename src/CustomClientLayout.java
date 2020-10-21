import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

///CUSTOM LAYOUT FOR CLIENT GUI
public class CustomClientLayout extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 246884657413758513L;
	
	
	public CustomClientLayout() {
		super("CustomClientLayout");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init2();
	}


	private void init() {
		// TODO Auto-generated method stub
		this.getContentPane().setLayout(new GridBagLayout());
		
		JPanel panel1 = new JPanel(new BorderLayout());
        panel1.setBorder(BorderFactory.createEtchedBorder());
        JLabel label1 =  new JLabel("Panel 1");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(label1,BorderLayout.CENTER);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(BorderFactory.createEtchedBorder());
        JLabel label2 =  new JLabel("Panel 2");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(label2,BorderLayout.CENTER);

        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setBorder(BorderFactory.createEtchedBorder());
        JLabel label3 =  new JLabel("Panel 3");
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        panel3.add(label3,BorderLayout.CENTER);

        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.setBorder(BorderFactory.createEtchedBorder());
        JLabel label4 =  new JLabel("Panel 4");
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        panel4.add(label4,BorderLayout.CENTER);

        //Here goes the interesting code
        //new GridBagConstraints(columnNumber, rowNumber, columnSpan, rowSpan, columnWeigth, rowWeigth, alignment, fillType, insets, padX, pady)
        this.getContentPane().add(panel1,  new GridBagConstraints(0, 0, 1, 2, 0.3, 0.6, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        this.getContentPane().add(panel2,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.8, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        this.getContentPane().add(panel3,  new GridBagConstraints(2, 0, 1, 2, 0.3, 0.6, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));
        //next row
        this.getContentPane().add(panel4,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.2, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2,
                2, 2), 0, 0));

        this.setPreferredSize(new Dimension(1300, 800));
        this.pack();
		
	}
	
	
	private void init2() {
//		JFrame frame = new JFrame("Demo");
//		frame.setPreferredSize(new Dimension(1300, 800));
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
//		frame.setResizable(false);
//		frame.setVisible(true);
		
		GridLayout splitTop = new GridLayout(1,3);
		GridLayout lookPrint = new GridLayout(5, 5);
//		GridLayout commandsReturn=new GridLayout(0,1);
//		GridLayout commands=new GridLayout(3,3);
//		//GridLayout chatbot=new GridLayout(1,1);

		// new panels
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY);						// setting color
		topPanel.setPreferredSize(new Dimension(1300, 150));				// dimension of panel
		this.getContentPane().add(topPanel, BorderLayout.PAGE_START);	// where to place in in the frame
//		JLabel label3 =  new JLabel("topPanel");
//        label3.setHorizontalAlignment(SwingConstants.CENTER);
//        topPanel.add(label3,BorderLayout.CENTER);
        topPanel.setLayout(splitTop);
        for (int i = 0; i < 3; i++)		//looping through the labels and addint them to the panel 
		{   
			JLabel label12 =  new JLabel("topPanel"+Integer.toString(i));
			label12.setHorizontalAlignment(SwingConstants.CENTER);
			topPanel.add(label12,BorderLayout.CENTER);
			topPanel.add(label12);
		}

		JPanel midPanelC = new JPanel();
		midPanelC.setBackground(Color.BLUE);
		midPanelC.setPreferredSize(new Dimension(600, 500));
//		JLabel label4 =  new JLabel("midPanelC");
//		label4.setHorizontalAlignment(SwingConstants.CENTER);
//        midPanelC.add(label4,BorderLayout.CENTER);
		
		midPanelC.setLayout(lookPrint);
		for (int i = 0; i < 25; i++)		//looping through the labels and addint them to the panel 
		{   
			JLabel label12 =  new JLabel("midPanelC"+Integer.toString(i));
			label12.setHorizontalAlignment(SwingConstants.CENTER);
	        midPanelC.add(label12,BorderLayout.CENTER);
			midPanelC.add(label12);
		}

		JPanel midPanelL = new JPanel();
		midPanelL.setBackground(Color.LIGHT_GRAY);
		midPanelL.setPreferredSize(new Dimension(350, 500));
//		midPanelL.setLayout(commands);
		JLabel label5 =  new JLabel("midPanelL");
		label5.setHorizontalAlignment(SwingConstants.CENTER);
		midPanelL.add(label5,BorderLayout.CENTER);


		JPanel midPanelR = new JPanel();
		midPanelR.setBackground(Color.white);
		midPanelR.setPreferredSize(new Dimension(350, 500));
		JLabel label6 =  new JLabel("midPanelR");
		label6.setHorizontalAlignment(SwingConstants.CENTER);
		midPanelR.add(label6,BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setPreferredSize(new Dimension(1300, 150));
		JLabel label7 =  new JLabel("bottomPanel");
		label7.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(label7,BorderLayout.CENTER);

		// add panel to frame
		this.getContentPane().add(midPanelC, BorderLayout.CENTER);
		this.getContentPane().add(midPanelL, BorderLayout.LINE_START);
		this.getContentPane().add(midPanelR, BorderLayout.LINE_END);
		this.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

        this.setPreferredSize(new Dimension(1300, 800));
        this.pack();
	}
    public static void main(String[] args) {
//    	CustomClientLayout frame = new CustomClientLayout();
////    	System.out.println(frame.getContentPane());
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

    	   ArrayList<char[]> rowList = new ArrayList<char[]>();

    	    rowList.add(new char[] { 'c', '2', '3' });
    	    rowList.add(new char[] { '4', '5', '6' });
//    	    rowList.add(new int[] { 7, 8 });

    	    for (char[] row : rowList) {
//    	    	Arrays.toString(row).a
    	    	System.out.println("Row = " + Arrays.toString(row));

    	    
    	    }
    	    System.out.println(rowList.get(0)[1]);
    }
    
	

}
