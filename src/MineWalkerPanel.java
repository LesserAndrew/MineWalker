import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
@SuppressWarnings("serial")
public class MineWalkerPanel extends JPanel {
	private MineFieldPanel panel;
	private JButton resetButton;
	private JButton minesButton;
	private JButton pathButton;
	private JSlider sizeSlider;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel scorePanel;
	private JButton mine0;
	private JButton mine1;
	private JButton mine2;
	private JButton mine3;
	private JButton mine;
	private JButton empty;
	private JButton start;
	private JButton goal;
	private JLabel lives;
	private JLabel score;
	private JLabel cheat;
	private int size = 10;
	JFrame frame = new JFrame();
	public MineWalkerPanel() {
		setLayout(new BorderLayout());
		mine0 = new JButton();
		mine1 = new JButton();
		mine2 = new JButton();
		mine3 = new JButton();
		mine = new JButton();
		empty = new JButton();
		start = new JButton();
		goal = new JButton();
		lives = new JLabel();
		score = new JLabel();
		cheat = new JLabel();
		buttonPanel = new JPanel();
		colorPanel = new JPanel();
		scorePanel = new JPanel();
		panel = new MineFieldPanel(new MinePanelListener(), size);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ResetButtonListener());
		minesButton = new JButton("Show Mines");
		minesButton.addActionListener(new MinesButtonListener());
		pathButton = new JButton("Show Path");
		pathButton.addActionListener(new PathButtonListener());
		sizeSlider = new JSlider(5, 20, size);
		sizeSlider.addChangeListener(new SizeSliderListener());
		sizeSlider.setPaintLabels(true);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintTicks(true);
		colorPanel.setLayout(new GridLayout(0, 1, 0, 4));
		colorPanel.add(mine0);
		colorPanel.add(mine1);
		colorPanel.add(mine2);
		colorPanel.add(mine3);
		colorPanel.add(mine);
		colorPanel.add(empty);
		colorPanel.add(start);
		colorPanel.add(goal);
		add(colorPanel, "West");
		add(panel, "Center");
		buttonPanel.add(resetButton);
		buttonPanel.add(minesButton);
		buttonPanel.add(pathButton);
		buttonPanel.add(sizeSlider);
		add(buttonPanel, "South");
		Color[] colors = panel.getColors();
		mine0.setBackground(colors[1]);
		mine1.setBackground(colors[2]);
		mine2.setBackground(colors[3]);
		mine3.setBackground(colors[4]);
		mine.setBackground(colors[5]);
		mine.setForeground(Color.white);
		start.setBackground(colors[6]);
		goal.setBackground(colors[7]);
		mine0.setText("0 mines nearby");
		mine1.setText("1 mine nearby");
		mine2.setText("2 mines nearby");
		mine3.setText("3 mines nearby");
		mine.setText("Mine");
		empty.setOpaque(false);
		empty.setContentAreaFilled(false);
		empty.setBorderPainted(false);
		start.setText("Start");
		goal.setText("Finish");
		scorePanel.setLayout(new GridLayout(3, 1));
		scorePanel.add(score);
		scorePanel.add(lives);
		scorePanel.add(cheat);
		add(scorePanel, "East");
		lives.setText("Lives: " + panel.getLives());
		score.setText("Score: " + panel.getScore());
		cheat.setText("No cheats");
	}
	private class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			panel.reset(size);
			panel.revalidate();
			panel.repaint();
			minesButton.setText("Show Mines");
			pathButton.setText("Show Path");
			lives.setText("Lives: " + panel.getLives());
			score.setText("Score: " + panel.getScore());
			cheat.setText("No cheats");
			resetButton.setText("Reset");
		}
	}
	private class MinesButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			panel.changeMines();
			if(minesButton.getText() == "Show Mines") {
				minesButton.setText("Hide Mines");
				if(panel.isWin() == false && panel.getLives() != 0) {
					cheat.setText("Cheats used");
				}
			}
			else {
				minesButton.setText("Show Mines");
			}
		}
	}
	private class PathButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			panel.changePath();
			if(pathButton.getText() == "Show Path") {
				pathButton.setText("Hide Path");
				if(panel.isWin() == false && panel.getLives() != 0) {
					cheat.setText("Cheats used");
				}
			}
			else {
				pathButton.setText("Show Path");
			}
		}
	}
	private class MinePanelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(panel.getLives() != 0 && panel.isWin() == false) {
				MineFieldButton clicked = (MineFieldButton)e.getSource();
				panel.movement(clicked);
				lives.setText("Lives: " + panel.getLives());
				score.setText("Score: " + panel.getScore());
				if(panel.getLives() == 0) {
					resetButton.setText("New Game");
					if(cheat.getText() == "Cheats used") {
						JOptionPane.showMessageDialog(frame, "You lost! Which is surpising because you're a cheater.");
					}
					else {
						JOptionPane.showMessageDialog(frame, "You lost!");
					}
					if(minesButton.getText() == "Show Mines") {
						minesButton.setText("Hide Mines");
						panel.changeMines();
					}
					
				}
				if(panel.isWin()) {
					resetButton.setText("New Game");
					if(cheat.getText() == "Cheats used") {
						JOptionPane.showMessageDialog(frame, "You win! Now try to win without using cheats!");
					}
					else {
						JOptionPane.showMessageDialog(frame, "You win! Your final score was " + panel.getScore() + " and your lives were " + panel.getLives() + ".");
					}
					if(minesButton.getText() == "Show Mines") {
						minesButton.setText("Hide Mines");
						panel.changeMines();
					}
				}
			}
		}
	}
	private class SizeSliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			size = sizeSlider.getValue();
		}
	}
}
