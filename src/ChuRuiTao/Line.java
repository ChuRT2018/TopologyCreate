package ChuRuiTao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;



public class Line {//implements Comparable<Line>
    
	private double sx = 0; // the x coordinates of start dot 
    private double sy = 0; // the y coordinates of start dot
    private double ex = 0; // the x coordinates of end dot
    private double ey = 0; // the y coordinated of end dot
    
    private Dot StartDot;// = new Dot(0,0,0);  //the start dot
    private Dot EndDot;// = new Dot(0, 0,0);   //the end dot
    double Angle = 0.0; // the angle one line respect the x axis
    int index = 0;
    
    Polygon rightPoly = new Polygon(); // the right polygon of this arc
    Polygon leftPoly = new Polygon();  //the left polygon of this arc
    
    public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getSx() {
		return sx;
	}
	public double getSy() {
		return sy;
	}
	public double getEx() {
		return ex;
	}
	public double getEy() {
		return ey;
	}
	public Dot getStartDot() {
		return StartDot;
	}
    public Dot getEndDot() {
		return EndDot;
	}
    
    //方向方面的考虑，两个方向变量，一个标识水平方向，另一个标识竖直方向
    
    Line(){
    	this.rightPoly = null;
    	this.leftPoly = null;
    }
	/**
     * the constructor of Line with dots
     * @param StartDot
     * @param EndDot
     */
    Line(Dot StartDot,Dot EndDot,int index){
    	this.StartDot = StartDot;
    	this.EndDot = EndDot;
    	this.sx = StartDot.getX();
    	this.sy = StartDot.getY();
    	this.ex = EndDot.getX();
    	this.ey = EndDot.getY();
    	this.Angle = Math.atan2((ey - sy),(ex - sx)); 
//    	if((ey - sy) > 0 && (ex - sx) < 0) Angle += Math.PI;
//    	if((ey - sy) < 0 && (ex - sx) < 0) Angle += Math.PI;
//    	if((ey - sy) < 0 && (ex - sx) > 0) Angle += Math.PI * 2;
//    	if((ex-sx) < 0) Angle += Math.PI;
//    	if((ex - sx) > 0 && (ey - sy) < 0) Angle += Math.PI * 2;
    	if(Angle < 0) Angle += Math.PI * 2;
    	this.rightPoly = null;
    	this.leftPoly = null;
    	this.index = index;
    }

    /**
     * this function to detecte the direction of lines
     * @param line
     */
//	@Override
//	public int compareTo(Line o) {
//		// TODO Auto-generated method stub
//		return (int) (this.Angle - o.Angle);
//	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " | "+this.getIndex()+" | ";
		//return " index: "+this.getIndex()+" ";
	}
	
	public boolean equals(Line other) {
		// TODO Auto-generated method stub
        if(this.getStartDot().equals(other.getStartDot()) && this.getEndDot().equals(other.getEndDot()))
        	return true;
        else {
			return false;
		}
	}
    
	public void draw(Graphics g,Color color){
		Color c = g.getColor();		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3.0f));
		g2.drawLine((int)sx, (int)sy, (int)ex, (int)ey);
		g.setColor(Color.black);
		g.drawString("" + this.index, (int)(this.sx+this.ex) / 2 - 10 , (int)(this.sy+this.ey) / 2 - 10);
		g2.setColor(Color.red);
		drawAL((int)(this.sx+this.ex) / 2,
			   (int)(this.sy+this.ey) / 2,
			   (int)((this.sx+this.ex) / 2 + 10 * Math.cos(Angle) ),
			   (int)((this.sy+this.ey) / 2 + 10 * Math.sin(Angle)), //画图坐标系y轴向下
			   g2);
		g.setColor(c);
	}
	
	public static void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2)  
    {  
  
        double H = 10; // 箭头高度  
        double L = 5; // 底边的一半  
        int x3 = 0;  
        int y3 = 0;  
        int x4 = 0;  
        int y4 = 0;  
        double awrad = Math.atan(L / H); // 箭头角度  
        double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度  
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);  
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);  
        double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点  
        double y_3 = ey - arrXY_1[1];  
        double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点  
        double y_4 = ey - arrXY_2[1];  
  
        Double X3 = new Double(x_3);  
        x3 = X3.intValue();  
        Double Y3 = new Double(y_3);  
        y3 = Y3.intValue();  
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// 画线
		g2.drawLine(sx, sy, ex, ey);
		//
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		// 实心箭头
		g2.fill(triangle);
		// //非实心箭头
		// //g2.draw(triangle);

	}
	
	 public static double[] rotateVec(int px, int py, double ang,  
	            boolean isChLen, double newLen) {  
	  
	        double mathstr[] = new double[2];  
	        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度  
	        double vx = px * Math.cos(ang) - py * Math.sin(ang);  
	        double vy = px * Math.sin(ang) + py * Math.cos(ang);  
	        if (isChLen) {  
	            double d = Math.sqrt(vx * vx + vy * vy);  
	            vx = vx / d * newLen;  
	            vy = vy / d * newLen;  	            
	        } 
	        mathstr[0] = vx;  
            mathstr[1] = vy;  
	        return mathstr;  
	    }
	public double getAngle(Dot dot) {
		if(dot.equals(StartDot)) 
			return Angle;
		else
			return Math.atan2(sy-ey, sx - ex) < 0 ?Math.atan2(sy-ey, sx - ex)+2*Math.PI:Math.atan2(sy-ey, sx - ex);
	}  
}
