package ChuRuiTao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Polygon {
     //int Edges = 0;
     
	 Collection<Line> Lines = new ArrayList<Line>();
     Collection<Dot> dots = new ArrayList<>(); 
     
     public Polygon(){
    	 
     }
     
     public Polygon(Collection<Line> lines) {
		// TODO Auto-generated constructor stub
	   this.Lines = lines;
	   //this.Edges = lines.size();
     }
     

     /**
      * this function used to find the lines attch to a dot.
      * @param poly
      */
	public void findLines(Polygon poly) {
		Line line = new Line();
		for (Iterator<Line> i = poly.Lines.iterator(); i.hasNext();) {
			line = i.next();
			if (!line.getStartDot().lines.contains(line))
				line.getStartDot().lines.add(line);
			if (!line.getEndDot().lines.contains(line))
				line.getEndDot().lines.add(line);
		}
	}
     /**
      * first: find all the lines in the polygon and which dot it attach to
      * then: find all the dots in the polygon and order the lines
      * @param poly
      */
	public void lineInDotInOrder(Polygon poly) {
		poly.findLines(poly);
		Line line = new Line();
		for (Iterator<Line> i = poly.Lines.iterator(); i.hasNext();) {
			line = i.next();
			if (!poly.dots.contains(line.getStartDot()))
				poly.dots.add(line.getStartDot());
			if (!poly.dots.contains(line.getEndDot()))
				poly.dots.add(line.getEndDot());
		}
		Dot dot = new Dot();
		for (Iterator<Dot> i = poly.dots.iterator(); i.hasNext();) {			
			dot = i.next();
			dot.linesInOrder(dot.lines,dot);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n" + this.Lines;
	}
     
}
