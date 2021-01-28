import java.util.*;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.io.*;

public class IChess extends JFrame {
	Board game = new Board();

	public static void main(String[] args) {
		IChess IChessMain = new IChess();

	}

	// -----Irfan-----
	public IChess() {
		// Define the size of the frame
		this.setSize(1000, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createGUI();
	}

	// -----Irfan / Fattah-----
	private void createGUI() {
		// Title of the frame
		this.setTitle("Myrmidon Chess");

		JMenuBar tableMenuBar = new JMenuBar();
		popMenuBar(tableMenuBar);
		this.setJMenuBar(tableMenuBar);

		JLabel p1 = new JLabel("Player 1");
		JLabel p2 = new JLabel("Player 2");

		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(220, 20, 60));
		panel1.setPreferredSize(new Dimension(50, 50));

		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.setPreferredSize(new Dimension(50, 50));

		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.setPreferredSize(new Dimension(50, 50));

		JPanel panel4 = new JPanel();
		panel4.setBackground(new Color(65, 105, 225));
		panel4.setPreferredSize(new Dimension(50, 50));

		JToolBar sideBar = new JToolBar();
		sideBar.setFloatable(false);
		this.add(sideBar, BorderLayout.WEST);

		sideBar.setOrientation(SwingConstants.VERTICAL);
		sideBar.add(p2);
		sideBar.add(panel1);
		sideBar.add(panel2);
		JButton forfeitButton = new JButton("Forfeit");
		sideBar.add(forfeitButton);
		sideBar.add(panel3);
		sideBar.add(panel4);
		sideBar.add(p1);
		// forfeit feature
		forfeitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				forfeitGame();
			}
		});
		Board bb = new Board();
		game = bb;
		this.add(bb);
		this.setVisible(true);
	}

	// -----Irfan-----
	private void popMenuBar(JMenuBar tableMenuBar) {
		tableMenuBar.add(CreateFileMenu());
		tableMenuBar.add(CreateOptionMenu());
		tableMenuBar.add(CreateHelpMenu());
	}

	// -----Irfan-----
	private JMenu CreateFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem newOption = new JMenuItem("New game");
		JMenuItem closeGame = new JMenuItem("Exit");
		newOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "New game loading..");
				newGame();
			}
		});
		closeGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(newOption);
		fileMenu.add(closeGame);
		return fileMenu;
	}

	// -----Irfan-----
	private JMenu CreateOptionMenu() {
		JMenu optionMenu = new JMenu("Option");
		JMenuItem saveOption = new JMenuItem("Save game");
		JMenuItem loadOption = new JMenuItem("Load game");

		saveOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Saving game...");
				saveGame();
			}
		});

		loadOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Loading game....");
				loadGame();
			}
		});

		optionMenu.add(saveOption);
		optionMenu.add(loadOption);
		return optionMenu;

	}

	// -----Irfan / Fattah-----
	private JMenu CreateHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem showGuide = new JMenuItem("How to play the game");
		JMenuItem aboutDev = new JMenuItem("About");
		showGuide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "|Welcome to Myrmidon Chess|\n\n"
						+ "- The game is played by 2 players, Player 1 & Player 2 on a 6X7 board.\n"
						+ "- On the board, there are 14 chess pieces all together with 7 on each side\n"
						+ "- Each players have control of the pieces on each side respectively\n"
						+ "- There are 4 different types of pieces out of the 14: \n"
						+ "   i  . PLUS     = Can move vertically or horizontally, up to 2 steps in any direction in a straight line.\n"
						+ "   ii . Triangle = Can move diagonally, both up and down, up to 2 steps in any direction in a straight line.\n"
						+ "   iii. Chevron  = Must move in an L shape, exactly two steps then 1 step right or left. (This is the only piece that can jump over other pieces.)\n"
						+ "   iv . Sun      = Can move only 1 step in any direction.\n"
						+ "- The game ends when the Sun is captured by the other side.\n"
						+ "* After 3 turns, a Plus will turn into a Triangle, a Triangle will turn into a Chevron, and a Chevron will turn into a Plus.\n"
						+ "\n~GOOD LUCK & HAVE FUN~");
			}
		});
		aboutDev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, ("DEVELOPERS\n" + "Faiq     - 1161102149\n"
						+ "Irfan    - 1161102437\n" + "Wana  - 1161102091\n" + "Fattah - 1161102372"));
			}
		});
		helpMenu.add(showGuide);
		helpMenu.add(aboutDev);
		return helpMenu;
	}

	// -----Irfan / Faiq-----
	private void saveGame() {
		Tile[] saveArray = game.getTileArray(); // copy tilearray to saveArray
		try { // Catch errors in I/O if necessary.
				// Open a file to write to, named SavedObj.sav.
			FileOutputStream saveFile = new FileOutputStream("SaveObj.txt");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// Now we do the save.
			for (int i = 0; i < 42; i++) { // if saveArray contains object, directly write to SaveObj.txt
				if (saveArray[i].getPieceImageName() != null) {
					save.writeObject(saveArray[i]);
				} else { // if saveArray is null, create dummy object to avoid NullPointerException
					Piece piece = new Piece();
					saveArray[i].setPiece(piece, "empty.png", 0);
					save.writeObject(saveArray[i]);

				}
			}

			// Close the file.
			save.close(); // This also closes saveFile.
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}

	// -----Irfan / Faiq-----
	private void loadGame() {
		Tile[] loadArray = new Tile[42];
		try {
			// Open file to read from, named SavedObj.sav.
			FileInputStream saveFile = new FileInputStream("SaveObj.txt");

			// Create an ObjectInputStream to get objects from save file.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			for (int i = 0; i < 42; i++) {
				loadArray[i] = (Tile) save.readObject(); // load contents of SaveObj.txt to loadArray
				game.setTileArray(i, loadArray); // pass loadArray to method setTileArray (line 430)
			}

			// Close the file.
			save.close(); // This also closes saveFile.
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}

	// -----Irfan-----
	private void newGame() {
		dispose();
		new IChess();
	}

	// -----Fattah-----
	private void forfeitGame() {
		if ((game.getTurn(game.getCount())) == "red") {
			int replyP1 = JOptionPane.showConfirmDialog(null, "The winnner is Player 1\nDo you want to play again?",
					"CONGRATULATIONS!!!", JOptionPane.YES_NO_OPTION);
			if (replyP1 == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "GOOD LUCK TO BOTH OF YOU! MAY THE BEST WIN :)");
				dispose();
				new IChess();
			} else {
				JOptionPane.showMessageDialog(null, "THANKS FOR PLAYING! GOODBYE :(");
				System.exit(0);
			}
		} else if ((game.getTurn(game.getCount())) == "blue") {
			int replyP2 = JOptionPane.showConfirmDialog(null, "The winnner is Player 2\nDo you want to play again?",
					"CONGRATULATIONS!!!", JOptionPane.YES_NO_OPTION);
			if (replyP2 == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "GOOD LUCK TO BOTH OF YOU! MAY THE BEST WIN :)");
				dispose();
				new IChess();
			} else {
				JOptionPane.showMessageDialog(null, "THANKS FOR PLAYING! GOODBYE :(");
				System.exit(0);
			}
		}
	}
}

class Board extends JPanel {
	// creating an array of 42 tile buttons
	Tile[] tilearray = new Tile[42];
	Piece piece = new Piece();

	int click1 = 80, click2, count = 1;
	boolean redMorph = false;
	String playerColor, turn;

	// -----Irfan / Faiq / Ewana-----
	Board() {
		super(new GridLayout(6, 7));
		int a = 1, b = 1;
		for (int i = 0; i < 42; i++) {
			if (b > 7) {
				a++;
				b = 1;
			}
			Point p = new Point(a, b);
			Tile tile_obj = new Tile(p);

			tile_obj.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					String search = tile_obj.getPieceImageName();
					if ((click1 == 80) && (search != null)) { // click1 = 80 is the condition we set to make sure that
																// this is the first click & search != null make sure
																// there is piece in the tile

						getTurn(count);

						playerColor = tile_obj.getPieceImageName();
						if (playerColor.toLowerCase().contains(turn.toLowerCase())) { // if player click on the correct
																						// tile with his piece inside
							firstClick(tile_obj.getPoint()); // the click will be registered as the first click, click1
																// = tile array number
						}

						else {
							if (turn.toLowerCase().contains("blue".toLowerCase())) { // if player click on other player
																						// piece when it is his turn,
																						// popup will appear to show
																						// current turn
								Popup.infoBox("Player 1 (Blue) Turn!", "Sorry");
							} else {
								Popup.infoBox("Player 2 (Red) Turn!", "Sorry");
							}
						}
					} else if (click1 < 80) { // this condition will only occur if click1 has been set to any tile array
												// number
						if (search != null) { // if click2 tile (where the piece will be moved) contains another piece
							if (search.toLowerCase().contains(turn.toLowerCase())) { // check if click2 tile contains
																						// own team's piece and,
								tileTeam(tile_obj.getPiece(), click1, click2); // deny movement, reset click1 = 80, do
																				// not update player turn
							} else if ((search.toLowerCase().contains("Sun".toLowerCase())) && (turn == "red")) {
								click2 = gettilearnum(tile_obj.getPoint());
								if (movePiece(click1, click2) == true) {
									tileEnemy(tile_obj.getPiece(), click1, click2);
									player2Wins();
								} else {
									System.out.println("INVALID MOVE");
								}
							} else if ((search.toLowerCase().contains("Sun".toLowerCase())) && (turn == "blue")) {
								click2 = gettilearnum(tile_obj.getPoint());
								if (movePiece(click1, click2) == true) {
									tileEnemy(tile_obj.getPiece(), click1, click2);
									player1Wins();
								} else {
									System.out.println("INVALID MOVE");
								}
							} else { // this condition will occur if click2 tile contains another piece but not from
										// the same team (move to capture opponent's piece)
								click2 = gettilearnum(tile_obj.getPoint());
								System.out.println("Movable : " + movePiece(click1, click2));
								if (movePiece(click1, click2) == true) {
									tileEnemy(tile_obj.getPiece(), click1, gettilearnum(tile_obj.getPoint())); // replace
																												// content
																												// of
																												// tilearray
																												// with
																												// own
																												// piece
									count++; // update player turn
									if ((count % 6 == 0) || (redMorph == true)) { // count%6 == 0 will happen if it is
																					// P1's(blue) turns to transform
										for (int i = 0; i < 42; i++) { // & redMorph == true will happen if it is
																		// P2's(red) turns to transform
											if ((tilearray[i].getMorph() == 1) && (tilearray[i].getPieceImageName()
													.contains(turn.toLowerCase()))) { // morph == 1 means object is a
																						// Plus
												plusToTriangle(tilearray[i].getPoint(), i, turn); // contains ensure
																									// only 1 player's
																									// piece will be
																									// transformed
											} // at a time
											else if ((tilearray[i].getMorph() == 2) && (tilearray[i].getPieceImageName()
													.contains(turn.toLowerCase()))) { // morph == 2 means object is a
																						// Triangle
												triangleToChevron(tilearray[i].getPoint(), i, turn);
											} else if ((tilearray[i].getMorph() == 3) && (tilearray[i]
													.getPieceImageName().contains(turn.toLowerCase()))) { // morph == 3
																											// means
																											// object is
																											// a Chevron
												chevronToPlus(tilearray[i].getPoint(), i, turn);
											}
										}
										if (count % 6 == 0) {
											redMorph = true; // P2(red) will be transformed straightly after P1
																// transform
										} else {
											redMorph = false; // redMorph = false after P2 has successfully transformed
										}
									}

								}

								else {
									System.out.println("INVALID MOVE");
								}
							}
							click1 = 80;
						} else { // this condition will occur if search == null (no other piece in the next tile)
							int click2 = gettilearnum(tile_obj.getPoint());
							System.out.println("Movable : " + movePiece(click1, click2));
							if (movePiece(click1, click2) == true) {
								tileEmpty(tile_obj.getPiece(), click1, click2); // check if click2 tile is empty
								count++; // next player's turn
								if ((count % 6 == 0) || (redMorph == true)) { // refer to transform's explanation above
									for (int i = 0; i < 42; i++) {
										if ((tilearray[i].getMorph() == 1)
												&& (tilearray[i].getPieceImageName().contains(turn.toLowerCase()))) {
											plusToTriangle(tilearray[i].getPoint(), i, turn);
										} else if ((tilearray[i].getMorph() == 2)
												&& (tilearray[i].getPieceImageName().contains(turn.toLowerCase()))) {
											triangleToChevron(tilearray[i].getPoint(), i, turn);
										} else if ((tilearray[i].getMorph() == 3)
												&& (tilearray[i].getPieceImageName().contains(turn.toLowerCase()))) {
											chevronToPlus(tilearray[i].getPoint(), i, turn);
										}
									}
									if (count % 6 == 0) {
										redMorph = true;
									} else {
										redMorph = false;
									}
								}
							}

							else {
								System.out.println("INVALID MOVE");
							}
							click1 = 80;

						}

					} else {
						System.out.println("No piece here");

					}
				}
			});

			tilearray[i] = (tile_obj);
			this.add(tilearray[i]);
			if (i % 2 == 0) {
				tilearray[i].setBackground(new Color(255, 250, 250));
			} else if (i % 2 == 1) {
				tilearray[i].setBackground(new Color(143, 188, 143));
			}
			b++;
		}

		arrange();

	}
	// -----Wana-----

	public Tile[] getTileArray() {
		return tilearray;
	}

	// -----Irfan / Faiq-----
	public void setTileArray(int i, Tile[] tile) {
		if ((tile[i].getPieceImageName().toLowerCase()).contains("empty".toLowerCase())) { // if tile[] contains dummy
																							// object, the object will
																							// be eliminated
			tilearray[i].setPiece(null, null, 0);
		} else {
			tilearray[i].setPiece(tile[i].getPiece(), tile[i].getPieceImageName(), tile[i].getMorph()); // modify all
																										// tilearray
																										// based on
																										// loaded file
		}

	}

	public int getCount() {
		return count;
	}

	//// -----Wana-----
	public int gettilearnum(Point p) { // a formula to convert Point to tilearray number
		int x = (int) p.getX();
		int y = (int) p.getY();
		int result = (x - 1) * 7 + (y - 1);
		return result;
	}

	// -----Faiq-----
	public String getTurn(int count) {
		if (count % 2 == 0) {
			turn = "red"; // even number = red
		} else {
			turn = "blue"; // odd number = blue
		}
		return turn; // return current turn based on count%2
	}

	// -----Wana-----
	private boolean movePiece(int click1, int click2) {
		boolean ret = true;
		if (tilearray[click1].getPiece().moveable(tilearray[click2].getPoint()) == true) {
			ret = true; // see if movement is valid according to Piece's ability
			if (tilearray[click1].getPiece().betTile(tilearray[click2].getPoint()) == true) {
				// check if theres something in between the current Point to the destination
				// Point
				// applies only for Triangle and Plus
				int check = gettilearnum(tilearray[click1].getPiece().betTilePoint(tilearray[click2].getPoint()));
				if (tilearray[check].getPieceImageName() != null) {
					ret = false;
					System.out.println("Piece in the way");
				} else if (tilearray[check].getPieceImageName() == null) {
					System.out.println("Youre good to go");
					ret = true;
				}
			}
		} else {
			ret = false;
		}

		return ret;

	}
	// -----Faiq-----

	private void firstClick(Point Point) {
		click1 = gettilearnum(Point);
		System.out.println("===================================================");
		System.out.println("current Point: " + click1);
	}

	// -----Faiq-----
	private void tileEmpty(Piece piece, int click1, int click2) { // check if tile is empty and validate movement
		this.piece = piece;
		System.out.println(piece);
		tilearray[click2].setPiece(tilearray[click1].getPiece(), tilearray[click1].getPieceImageName(),
				(tilearray[click1].getMorph()));
		tilearray[click1].setPiece(null, null, 0);
		System.out.println("===================================================");
		System.out.println("Movement successful");
	}

	// -----Faiq-----
	private void tileEnemy(Piece piece, int click1, int click2) { // check if tile contains enemy pieces and capture
																	// enemy
		tilearray[click2].setPiece(tilearray[click1].getPiece(), tilearray[click1].getPieceImageName(),
				(tilearray[click1].getMorph()));
		tilearray[click1].setPiece(null, null, 0);
		System.out.println("===================================================");
		System.out.println("Enemy captured");
	}

	// -----Faiq-----
	private void tileTeam(Piece piece, int click1, int click2) { // check if tile contains team piece and deny movement
		System.out.println("===================================================");
		System.out.println("There's another ally piece in this tile");
	}

	// -----Faiq-----
	private void plusToTriangle(Point p, int click2, String turn) { // create new object and load to tilearray based on
																	// transformation required
		int y = (int) p.getX();
		int x = (int) p.getY();
		if (turn == "red") {
			Piece triangleRedx = new Triangle(new Point(x, y));
			tilearray[click2].setPiece(triangleRedx, "triangle_red.png", 2);
		} else {
			Piece triangleBluex = new Triangle(new Point(x, y));
			tilearray[click2].setPiece(triangleBluex, "triangle_blue.png", 2);
		}
	}

	// -----Faiq-----
	private void triangleToChevron(Point p, int click2, String turn) { // create new object and load to tilearray based
																		// on transformation required
		int y = (int) p.getX();
		int x = (int) p.getY();
		if (turn == "red") {
			Piece chevronRedx = new Chevron(new Point(x, y));
			tilearray[click2].setPiece(chevronRedx, "chev_red.png", 3);
		} else {
			Piece chevronBluex = new Chevron(new Point(x, y));
			tilearray[click2].setPiece(chevronBluex, "chev_blue.png", 3);
		}
	}

	// -----Faiq-----
	private void chevronToPlus(Point p, int click2, String turn) { // create new object and load to tilearray based on
																	// transformation required
		int y = (int) p.getX();
		int x = (int) p.getY();
		if (turn == "red") {
			Piece plusRedx = new Plus(new Point(x, y));
			tilearray[click2].setPiece(plusRedx, "plus_red.png", 1);
		} else {
			Piece plusBluex = new Plus(new Point(x, y));
			tilearray[click2].setPiece(plusBluex, "plus_blue.png", 1);
		}
	}

	// -----Irfan-----
	private void arrange() {
		Piece plusRed1 = new Plus(new Point(1, 1));
		Piece plusRed2 = new Plus(new Point(7, 1));
		Piece plusBlue1 = new Plus(new Point(1, 6));
		Piece plusBlue2 = new Plus(new Point(7, 6));

		Piece triangleRed1 = new Triangle(new Point(2, 1));
		Piece triangleRed2 = new Triangle(new Point(6, 1));
		Piece triangleBlue1 = new Triangle(new Point(2, 6));
		Piece triangleBlue2 = new Triangle(new Point(6, 6));

		Piece chevRed1 = new Chevron(new Point(3, 1));
		Piece chevRed2 = new Chevron(new Point(5, 1));
		Piece chevBlue1 = new Chevron(new Point(3, 6));
		Piece chevBlue2 = new Chevron(new Point(5, 6));

		Piece sunRed = new Sun(new Point(4, 1));
		Piece sunBlue = new Sun(new Point(4, 6));

		tilearray[0].setPiece(plusRed1, "plus_red.png", 1);
		tilearray[6].setPiece(plusRed2, "plus_red.png", 1);
		tilearray[35].setPiece(plusBlue1, "plus_blue.png", 1);
		tilearray[41].setPiece(plusBlue2, "plus_blue.png", 1);

		tilearray[1].setPiece(triangleRed1, "triangle_red.png", 2);
		tilearray[5].setPiece(triangleRed2, "triangle_red.png", 2);
		tilearray[36].setPiece(triangleBlue1, "triangle_blue.png", 2);
		tilearray[40].setPiece(triangleBlue2, "triangle_blue.png", 2);

		tilearray[2].setPiece(chevRed1, "chev_red.png", 3);
		tilearray[4].setPiece(chevRed2, "chev_red.png", 3);
		tilearray[37].setPiece(chevBlue1, "chev_blue.png", 3);
		tilearray[39].setPiece(chevBlue2, "chev_blue.png", 3);

		tilearray[3].setPiece(sunRed, "sun_red.png", 20);
		tilearray[38].setPiece(sunBlue, "sun_blue.png", 20);
	}

	private void player1Wins() {
		JOptionPane.showMessageDialog(null, ("Congratulation Player 1!!! You win!"), "Thank You For Playing IChess",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	private void player2Wins() {
		JOptionPane.showMessageDialog(null, ("Congratulation Player 2!!! You win!"), "Thank You For Playing IChess",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}

//// -----Wana / Faiq-----
class Tile extends JButton implements Serializable {
	private Point Point;
	private String imageName;
	transient ImageIcon iconType;
	private int morph;

	private Piece piece = new Piece();

	Tile() {
	}

	Tile(Point Point) {
		this.Point = Point;
		this.piece = piece;
	}

	Tile(Point Point, Piece piece) {
		this.Point = Point;
		this.piece = piece;
	}

	public Piece getPiece() {
		return piece;
	}

	public Point getPoint() {
		return Point;
	}

	public void setPiece(Piece p, String g, int morph) {
		this.piece = p;
		this.imageName = g;
		this.morph = morph;
		ImageIcon iconType = new ImageIcon(imageName);
		setIcon(iconType);
		if (p != null) {
			p.setCurrentPoint(Point);
		}
	}

	public String getPieceImageName() {
		return imageName;
	}

	public int getMorph() {
		return morph;
	}

	public String toString() {
		return String.valueOf(Point.getX()) + "," + String.valueOf(Point.getY());
	}

}

class Popup {

	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}

// -----Wana-----
// a class for coordinate and location
class Point implements Serializable {
	int x, y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return "x: " + getX() + " ,y: " + getY();
	}
}