package game.pong.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.pong.core.Animation;

public class GamePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5084525618016053617L;
	
	private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
	
	int height, width;
	int initXPosBot, initYPosBot;
	int stickHeight = 60;
	int stickWidth = 20;
	
	int ballX, ballY;
	int ballH = 20, ballW = 20;

	int stickSpeed = 3;
	int velX = 3; 
	int velY = 3;
	
	int playerX, playerY;
	
	int botScore = 0;
	int playerScore = 0;
	
	Timer ballAnimation = null;
	Animation playerAnimationUp = null;
	Animation playerAnimationDown = null;
	
	
	Animation botAnimationUp = null;
	Animation botAnimationDown = null;
	
	JButton startButton = new JButton("Start");
	Ellipse2D ball = null;
	
	boolean start = true;

	public GamePanel() {
		
		addKeyListener(new GameKeyListner());
		
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);		
		
		startButton.addActionListener(new GameActionListner(this));
		startButton.setActionCommand("start");
		add(startButton);
		
		setVisible(true);
		setBackground(Color.black);
	}
	
	public void paintComponent(Graphics graphics) {
		height = this.getHeight();
		width = this.getWidth();
		
		if (start) {
			initXPosBot = 0;
			initYPosBot = (height / 2) - (stickHeight / 2) ;
		}
		
		if (start) {
			playerX = width - stickWidth;
			playerY = (height / 2) - (stickHeight / 2) ;
		}
		
		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		graphics2d.setColor(Color.gray);
		
		Rectangle2D bot = new Rectangle(initXPosBot, initYPosBot, stickWidth, stickHeight);
		graphics2d.fill(bot);
		
		Rectangle2D player = new Rectangle(playerX, playerY, stickWidth, stickHeight);
		graphics2d.fill(player);
		
		if (start) {
			ballX = width/ 2 - ballW / 2;
			ballY = height / 2 - ballH / 2;
			start = false;
		}
		
		ball = new Ellipse2D.Double(ballX, ballY, ballW, ballH);
		graphics2d.fill(ball);
		
		if (botAnimationUp != null) {
			botAnimationUp = new Animation(5) {

				@Override
				public void action() {
					if (initYPosBot > 0) {
						initYPosBot = initYPosBot - stickSpeed;
					}
				}
				
			};
		}
		
		if (botAnimationDown != null) {
			botAnimationDown = new Animation(5) {

				@Override
				public void action() {
					if (initYPosBot < height - stickHeight) {
						initYPosBot = initYPosBot + stickSpeed;
					}
				}
				
			};
		}
	}
	
	
	/**
	 * Moving the ball
	 */
	private void moveBall() {
		
		if ((ballY >= playerY && ballY <= playerY + stickHeight) && (ballX >= width - stickWidth - ballW)) {
			velX = -3;
		} else if (ballX >= width - ballW) {
			botScore++;
			velX = -3;
			log.info("Bot Score: "+ botScore);
		} 
		
		if ((ballY >= initYPosBot && ballY <= initYPosBot + stickHeight) && (ballX <= stickWidth)) {
			velX = 3;
		} else if (ballX <= 0) {
			playerScore++;
			velX = 3;
			log.info("Player Score: "+ playerScore);
		}
		
		if (ballY >= height - ballH) {
			velY = -3;
		} 
		
		if (ballY <= 0) {
			velY = 3;
		}
		
		
		ballX = ballX + velX;
		ballY = ballY + velY;
		
		if (ballY < height - stickHeight) {
			initYPosBot = ballY;
		}
	}
	
	private void moveStickDown() {
		if (playerY < height - stickHeight) {
			playerY = playerY + stickSpeed;
		}
	}
	
	private void moveStickUp() {
		if (playerY > 0) {
			playerY = playerY - stickSpeed;
		}
	}
	
	public class GameKeyListner implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_DOWN :
				if (playerAnimationDown == null) {
					playerAnimationDown = new Animation(5) {
						
						@Override
						public void action() {
							moveStickDown();
							repaint();
						}
					}; 
				}
				playerAnimationDown.start();
				break;
				
			case KeyEvent.VK_UP :
				if (playerAnimationUp == null) {
					playerAnimationUp = new Animation(5) {
						
						@Override
						public void action() {
							moveStickUp();
							repaint();
						}
					};
				}
				playerAnimationUp.start();
				break;
			
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
					
			case KeyEvent.VK_DOWN :
				if (playerAnimationDown != null) {
					playerAnimationDown.stop();
				}
				break;
			case KeyEvent.VK_UP :
				if (playerAnimationUp != null) {
					playerAnimationUp.stop();
				}
				break;
			}
		}
		
	}
	
	
	public class GameActionListner implements ActionListener {
		
		JPanel jPanel;
		
		public GameActionListner(JPanel jPanel) {
			this.jPanel = jPanel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			log.info("Inside actionPerformed: "+ e.getActionCommand());
			
			if (ballAnimation == null) {
				ballAnimation = new Timer(20, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						moveBall();
						repaint();
					}
				}); 		
			}
			
			if (e.getActionCommand().equals("start")) {
				startButton.setText("Pause");
				startButton.setActionCommand("pause");
				log.info("Starting animation ");
				ballAnimation.start();
				jPanel.requestFocus();
			}
			
			if (e.getActionCommand().equals("pause")) {
				startButton.setText("Start");
				startButton.setActionCommand("start");
				log.info("stopping animation ");
				ballAnimation.stop();
				jPanel.requestFocus();
			}
			
			
		}
	}
	
}
