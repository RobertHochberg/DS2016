package ds2016;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeGUI2 extends TicTacToe {
	JFrame boardGUI;

	// This semaphore blocks the main thread until the human makes his move.
	Semaphore holdForHuman;
	int moveGUI;

	// These buttons hold the X and O characters
	Token[] tokens;

	// This label goes at the top, saying whose turn it is
	JLabel statusInfoLabel;

	// This label goes at the bottom, saying who won the game
	JLabel winnerInfoLabel;

	// The panel where we draw everything
	JPanel p;

	protected Image boardImage;
	private Image xImage;
	private Image oImage;

	public TicTacToeGUI2(){
		// Set up the GUI
		boardGUI = new JFrame();
		boardGUI.setLayout(new BorderLayout());
		boardGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardGUI.setPreferredSize(new Dimension(300, 500));
		boardGUI.setLocation(0,  0);

		// Read the board and token images
		try {
			boardImage = ImageIO.read(new File("/Users/wpa/Dropbox/DiscreteStructures/workspace/Github/DS2016/TTTboard.png"));
			xImage     = ImageIO.read(new File("/Users/wpa/Dropbox/DiscreteStructures/workspace/Github/DS2016/xImage.png"));
			oImage     = ImageIO.read(new File("/Users/wpa/Dropbox/DiscreteStructures/workspace/Github/DS2016/oImage.png"));
		} catch (IOException e) {
			System.err.println("Could not open files");
		}

		// Top label
		statusInfoLabel = new JLabel("Waiting for game to start.");
		boardGUI.add(statusInfoLabel, BorderLayout.NORTH);

		// Bottom label
		winnerInfoLabel = new JLabel("The winner will be???");
		boardGUI.add(winnerInfoLabel, BorderLayout.SOUTH);

		// Panel for board
		p = new JPanel(){
			public void paint(Graphics g){
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(boardImage, 0, 0, 300, 400, null);
				for(int i = 0; i < 9; i++){
					tokens[i].setLocation(tokens[i].xLoc, tokens[i].yLoc);
				}
			}
		};
		// Build the tokens
		tokens = new Token[9];
		for(int i = 0; i < 9; i++){
			tokens[i] = new Token(i <= 4 ? "X" : "O", 300, 10 + i * 30);
			tokens[i].addMouseListener(tokens[i]);
			tokens[i].addMouseMotionListener(tokens[i]);
			tokens[i].setVisible(true);
			tokens[i].setPreferredSize(new Dimension(25, 25));
			p.add(tokens[i]);
		}
		boardGUI.add(p, BorderLayout.CENTER);

		boardGUI.pack();
		boardGUI.setVisible(true);
		p.repaint();

		holdForHuman = new Semaphore(0);
	}


	/**
	 * @override
	 * Overrides the humanMove method in TicTacToe
	 * Waits to acquire a permit from the holdForHuman semaphore.
	 * When it gets it, it processes the "moveGUI" value that was
	 * set by the "actionPerformed" method
	 */
	void getHumanMove(){
		System.out.print("Waiting for player input");
		System.out.print("Player " + whoseTurn + ", Please click a square: ");
		try {
			holdForHuman.acquire();
		} catch (InterruptedException e) {
			System.err.println("Killed in the middle of the Human move");
			e.printStackTrace();
		}
		int move = moveGUI; // This got set when the human moved

		if(whoseTurn == 1)
			board[move] = playerOneCharacter;
		else
			board[move] = playerTwoCharacter;

		// update whose turn it is
		whoseTurn = 3 - whoseTurn;
	}

	/**
	 * @override
	 * This method overrides our text-drawing method, instead updating the GUI
	 */
	public void drawBoard(){
		if(isGameOver()){
			int w = whoWon();
			if(w == 1)
				winnerInfoLabel.setText("Player 1 is the winner!!!");
			if(w == 2)
				winnerInfoLabel.setText("Player 2 is the winner!!!");
			if(w == 0)
				winnerInfoLabel.setText("DRAW");
			else
				statusInfoLabel.setText("Game over.");
		} else {
			statusInfoLabel.setText("Still Playing.");
		}
		boardGUI.repaint();
	}

	public class Token extends JPanel implements MouseListener, MouseMotionListener{
		private static final long serialVersionUID = 7881479123974L;
		String Character; 	// X or O
		int xLoc, yLoc;			// Location of this image
		int startX, startY; // starting location of token
		int startMouseX, startMouseY;	// starting location of drag

		public Token(String c, int x, int y){
			Character = c;
			this.xLoc = x;
			this.yLoc = y;
		}

		public void mouseDragged(MouseEvent e) {
			xLoc = startX + e.getX() - startMouseX;
			yLoc = startY + e.getY() - startMouseY;
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {


		}

		public void mousePressed(MouseEvent e) {
			startMouseX = e.getX();
			startMouseY = e.getY();
			startX = xLoc;
			startY = yLoc;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void paint(Graphics g){
			if(this.Character == "X")
				g.drawImage(xImage, 0, 0, null);
			else
				g.drawImage(oImage, 0, 0, null);
		}
	}
}
