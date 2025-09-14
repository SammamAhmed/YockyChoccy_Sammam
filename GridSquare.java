import java.awt.Color;
import static java.awt.Color.green;
import javax.swing.*;

/*
 *  A GUI component
 *
 *  A simple extension of JPanel which records its
 *  coordinates in xcoord and ycoord, NOT in 'x' and 'y'.
 *  Why not? Because 'x' and 'y' are already attributes of
 *  the panel (super) class which say where to draw it in the window.
 *
 *  The game grid and allows the background colour to be set with ease.
 *
 *  @author mhatcher
 */
public class GridSquare extends JPanel
{
    public int xcoord, ycoord;  // location in the grid
	
	// constructor takes the x and y coordinates of this square
	public GridSquare(int xcoord, int ycoord)
	{
		super();
		this.setSize(50,50);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}

    public void setColor(int xcoord, int ycoord)
    {
        Color Chocolate = new Color(210, 105, 30);
        this.setBackground(Chocolate);
        if(xcoord==0 && ycoord==0){
            this.setBackground(green);
        }
    }



    public int getXcoord()              { return xcoord; }
    public int getYcoord()              { return ycoord; }
}
