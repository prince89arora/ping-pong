package game.pong.frame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1309978925340226291L;

	public MainFrame() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		
		initPanel();
		
		setSize(700, 350);
		//setBackground(Color.BLACK);	
		setTitle("Ping Pong");
		setVisible(true);
	}

	private void initPanel() {
		this.add(new GamePanel(), BorderLayout.CENTER);
	} 
}
