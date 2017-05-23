package ChuRuiTao;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Dot{
    private double x = 0;   
	private double y = 0;
    private int index = 0; //from 1 
    Collection<Line> lines = new ArrayList<>();
    
    public Dot(){
    	
    }
    
	public Dot(double x,double y,int index) {
		// TODO Auto-generated constructor stub
	  this.x = x;
	  this.y = y;
	  this.index = index;
    }
	
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Collection<Line> getLines() {
		return lines;
	}

	public void setLines(Collection<Line> lines) {
		this.lines = lines;
	}
	/**
	 * order the lines in this dot by angle from 0 to 360 in arc
	 * @param lines
	 */
	public void linesInOrder(Collection<Line> lines,final Dot dot){ 
		Collections.sort((List<Line>)lines, new Comparator<Line>() {
            @Override
            public int compare(Line t,Line o){
                if (t.getAngle(dot) < o.getAngle(dot))
                    return -1;
                else if (t.getAngle(dot) > o.getAngle(dot))
                    return 1;
                else
                	return 0;

            }

        });
		
//		Collections.sort((List<Line>) lines);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\nx: " + x + " y: " + y + " index: " + index ;
	}
	
	
	public boolean equals(Dot other) {
		// TODO Auto-generated method stub
		if((this.getX() == other.getX()) && (this.getY() == other.getY()))
			return true;
		else {
			return false;
		}
	}

	public Line findNextLine(Line LastLine){
		int i = 0;
		Line[] LineArray = this.lines.toArray(new Line[0]);
		for(;i<LineArray.length;i++){
			if(LineArray[i].equals(LastLine)) break;
		}
		if(i == LineArray.length -1) return LineArray[0];
		else return LineArray[i+1]; 
	}
	
	public void draw(Graphics g,Color color){
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval((int)this.getX()-4, (int)this.getY()-4, 8, 8);
		g.setColor(Color.BLUE);
		g.drawString("" + this.index, (int)this.x - 10 , (int)this.y - 10);
		g.setColor(c);
	}
	
	
}

