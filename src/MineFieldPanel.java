import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class MineFieldPanel extends JPanel {
	MineFieldButton[][] tiles;
	Color[] pathTiles;
	private int size;
	private ActionListener listener;
	private int score;
	private int lives;
	private boolean win;
	public MineFieldPanel(ActionListener listener, int size) {
		win = false;
		score = size*25;
		lives = (int)Math.floor((double)size/2.0);
		this.size = size;
		this.listener = listener;
		tiles = new MineFieldButton[size][size];
		setLayout(new GridLayout(size, size, 0, 0));
		setPreferredSize(new Dimension(500, 500));
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				tiles[i][j] = new MineFieldButton();
				tiles[i][j].addActionListener(listener);
				add(tiles[i][j]);
			}
		}
		generatePath(size);
		tiles[0][size-1].setEnd();
		tiles[size-1][0].setStart();
		tiles[size-1][0].setCurrent();
		setMines(size);
		changeMines();
		changePath();
		tiles[0][size-1].setEnd();
	}
	public void reset(int size) {
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				remove(tiles[i][j]);
			}
		}
		win = false;
		score = size*25;
		lives = (int)Math.floor((double)size/2.0);
		this.size = size;
		tiles = new MineFieldButton[size][size];
		setLayout(new GridLayout(size, size, 0, 0));
		setPreferredSize(new Dimension(500, 500));
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				tiles[i][j] = new MineFieldButton();
				tiles[i][j].addActionListener(listener);
				add(tiles[i][j]);
			}
		}
		generatePath(size);
		tiles[0][size-1].setEnd();
		tiles[size-1][0].setStart();
		tiles[size-1][0].setCurrent();
		setMines(size);
		changeMines();
		changePath();
		tiles[0][size-1].setEnd();
	}
	public void generatePath(int size) {
		int x = size-1;
		int y = 0;
		int length = 0;
		RandomWalk random = new RandomWalk(size);
		while(x != 0 && y != size-1) {
			random.step();
			x = random.getCurrentPoint().x;
			y = random.getCurrentPoint().y;
			tiles[x][y].setPath();
			length++;
		}
		if(x != 0) {
			while(x != 0) {
				x--;
				tiles[x][y].setPath();
				length++;
			}
		}
		if(y != size-1) {
			while(y != size-1) {
				y++;
				tiles[x][y].setPath();
				length++;
			}
		}
		pathTiles = new Color[length];
	}
	public void setMines(int size) {
		Random rand = new Random();
		int percent = 25;
		int randX;
		int randY;
		for(int i = 0; i<(int)Math.round((size*size)*((double)percent/100.0)); i++) {
			randY = rand.nextInt(size);
			randX = rand.nextInt(size);
			if(tiles[randX][randY].isPath() || tiles[randX][randY].isEnd() || tiles[randX][randY].isStart() || tiles[randX][randY].isMine()) {
				i--;
			}
			else {
				tiles[randX][randY].setMine();
			}
		}
	}
	public void movement(MineFieldButton button) {
		int x = 0;
		int y = 0;
		int mines = 0;
		boolean moveable = false;
		for (int i = 0 ; i < tiles.length; i++) {
		    for(int j = 0 ; j < tiles[i].length; j++) {
		         if ( tiles[i][j] == button) {
		        	 x = i;
		        	 y = j;
		             break;
		         }
		    }
		}
		if(x != 0) {
			if(tiles[x-1][y].isCurrent()) {
				moveable = true;
				tiles[x-1][y].setCurrent();
				if(tiles[x][y].isMine()) {
					tiles[x-1][y].setCurrent();
				}
			}
		}
		if(x != size-1) {
			if(tiles[x+1][y].isCurrent()) {
				moveable = true;
				tiles[x+1][y].setCurrent();
				if(tiles[x][y].isMine()) {
					tiles[x+1][y].setCurrent();
				}
			}
		}
		if(y != 0) {
			if(tiles[x][y-1].isCurrent()) {
				moveable = true;
				tiles[x][y-1].setCurrent();
				if(tiles[x][y].isMine()) {
					tiles[x][y-1].setCurrent();
				}
			}
		}
		if(y != size-1) {
			if(tiles[x][y+1].isCurrent()) {
				moveable = true;
				tiles[x][y+1].setCurrent();
				if(tiles[x][y].isMine()) {
					tiles[x][y+1].setCurrent();
				}
			}
		}
		if(moveable) {
			if(x != 0) {
				if(tiles[x-1][y].isMine()) {
					mines++;
				}
			}
			if(x != size-1) {
				if(tiles[x+1][y].isMine()) {
					mines++;
				}
			}
			if(y != 0) {
				if(tiles[x][y-1].isMine()) {
					mines++;
				}
			}
			if(y != size-1) {
				if(tiles[x][y+1].isMine()) {
					mines++;
				}
			}
			if(button.isMine() && button.isSticky() != true) {
				lives--;
				score -= 15;
				button.setSticky();
				button.setBackground(button.colors()[5]);
			}
			else if(button.getBackground() == button.colors()[8]) {
				changePath();
				button.movement(mines);
				changePath();
				button.setCurrent();
				score -= 5;
			}
			else if(button.isMine() && button.isSticky()) {
				
			}
			else {
				button.movement(mines);
				button.setCurrent();
				score -= 5;
			}
			if(button.isEnd()) {
				win = true;
			}
		}
	}
	public void changeMines() {
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				if(tiles[i][j].isMine()) {
					if(tiles[i][j].getBackground() == tiles[i][j].colors()[0]) {
						tiles[i][j].setBackground(tiles[i][j].colors()[5]);
					}
					else {
						if(tiles[i][j].isSticky() == false) {
							tiles[i][j].setBackground(tiles[i][j].colors()[0]);
						}
					}
				}
			}
		}
	}
	public void changePath() {
		int num = 0;
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles[0].length; j++) {
				if(tiles[i][j].isPath()) {
					if(tiles[i][j].getBackground() != tiles[i][j].colors()[8]) {
						if(tiles[i][j].isStart()) {
							
						}
						else {
							pathTiles[num] = tiles[i][j].getBackground();
							num++;
						}
						tiles[i][j].setBackground(tiles[i][j].colors()[8]);
					}
					else {
						if(tiles[i][j].isStart()) {
							
						}
						else {
							if(pathTiles[num] == null) {
								tiles[i][j].setBackground(tiles[i][j].colors()[0]);
							}
							else {
								tiles[i][j].setBackground(pathTiles[num]);
								num++;
							}
						}
					}
				}
			}
		}
		tiles[size-1][0].setStart();
		tiles[0][size-1].setEnd();
	}
	public Color[] getColors() {
		return tiles[0][0].colors();
	}
	public int getScore() {
		if(score < 0) {
			score = 0;
		}
		return score;
	}
	public int getLives() {
		return lives;
	}
	public boolean isWin() {
		return win;
	}
}
