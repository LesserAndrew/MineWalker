import java.awt.Color;
import javax.swing.JButton;
@SuppressWarnings("serial")
public class MineFieldButton extends JButton {
	private final Color[] COLORS = {Color.white, Color.green, Color.yellow, Color.orange, Color.red, Color.black, Color.cyan, Color.magenta, Color.gray};
	private boolean isMine = false;
	private boolean isStart = false;
	private boolean isEnd = false;
	private boolean isPath = false;
	private boolean isCurrent = false;
	private boolean sticky = false;
	public MineFieldButton() {
		setBackground(COLORS[0]);
	}
	public void movement(int mines) {
		if(isStart || isEnd) {
			
		}
		else {
			switch(mines) {
			case 1:
				setBackground(COLORS[2]);
				break;
			case 2:
				setBackground(COLORS[3]);
				break;
			case 3:
				setBackground(COLORS[4]);
				break;
			default:
				setBackground(COLORS[1]);
			}
		}
	}
	public Color[] colors() {
		return COLORS;
	}
	public void setMine() {
		isMine = true;
		setBackground(COLORS[5]);
	}
	public boolean isMine() {
		return isMine;
	}
	public void setStart() {
		isStart = true;
		setBackground(COLORS[6]);
	}
	public boolean isStart() {
		return isStart;
	}
	public void setEnd() {
		isEnd = true;
		setBackground(COLORS[7]);
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setPath() {
		isPath=true;
		setBackground(COLORS[8]);
	}
	public boolean isPath() {
		return isPath;
	}
	public void setCurrent() {
		if(isCurrent) {
			isCurrent = false;
			setText("");
		}
		else {
			isCurrent = true;
			setText("X");
		}
	}
	public boolean isCurrent() {
		return isCurrent;
	}
	public void reset() {
		setBackground(COLORS[0]);
		isMine = false;
		isPath = false;
		isCurrent = false;
		setText("");
	}
	public void setSticky() {
		sticky = true;
	}
	public boolean isSticky() {
		return sticky;
	}
}
