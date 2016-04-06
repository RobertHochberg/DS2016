package ds2016;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeGUI extends TicTacToe implements ActionListener, KeyListener {
	JFrame boardGUI;

	// This semaphore blocks the main thread until the human makes his move.
	Semaphore holdForHuman;
	int moveGUI;

	// These buttons hold the X and O characters
	JButton[][] tttButton;

	// This label goes at the top, saying whose turn it is
	JLabel statusInfoLabel;

	// This label goes at the bottom, saying who won the game
	JLabel winnerInfoLabel;

	public TicTacToeGUI(){
		// Build the buttons
		tttButton = new JButton[3][3];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				System.out.println("i, " + i + "j, " + j);
				tttButton[i][j] = new JButton(" ");
				tttButton[i][j].addActionListener(this);
			}
		tttButton[0][0].addKeyListener(this);


		// Set up the GUI
		boardGUI = new JFrame();
		boardGUI.setLayout(new BorderLayout());
		boardGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardGUI.setPreferredSize(new Dimension(300, 300));
		boardGUI.setLocation(0,  0);

		// Top label
		statusInfoLabel = new JLabel("Waiting for game to start.");
		boardGUI.add(statusInfoLabel, BorderLayout.NORTH);

		// Bottom label
		winnerInfoLabel = new JLabel("The winner will be???");
		boardGUI.add(winnerInfoLabel, BorderLayout.SOUTH);

		// Panel for buttons
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 3));
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				p.add(tttButton[i][j]);
			}
		boardGUI.add(p, BorderLayout.CENTER);

		boardGUI.pack();
		boardGUI.setVisible(true);

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
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				tttButton[i][j].setText("" + board[3*i + j + 1]);
			}

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
	}



	/**
	 * Handles button pushes. Figures out the (i, j) coordinates of the button that
	 * was pushed, and computes the appropriate value for "moveGUI". 
	 * 
	 * Then it releases a permit to the holdForHuman semaphore, so that the 
	 * humanMove method can continue.
	 */
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		// Find which button this was. Not necessarily the best way.
		int i=0, j=0;
		out:
			for(i = 0; i < 3; i++){
				for(j = 0; j < 3; j++){
					if(tttButton[i][j] == b) // Same object in memory
						break out;
				}
			}
		moveGUI = 3*i + j + 1;
		holdForHuman.release();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		moveGUI = (int)(e.getKeyChar() - '0');
		System.out.println("The key is " + moveGUI);
		holdForHuman.release();
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		moveGUI = (int)(e.getKeyChar() - '0');
		System.out.println("The key is " + moveGUI);
		holdForHuman.release();
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
