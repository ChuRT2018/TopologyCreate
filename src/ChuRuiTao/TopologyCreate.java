package ChuRuiTao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

public class TopologyCreate extends JFrame {

	private JFrame frmTopologycreate;
	JPanel panel_1 = new JPanel();
	JTable table_1;
	JScrollPane js = new JScrollPane();
	DoubleClick dc = new DoubleClick();
	Image offScreenImage = null;// ���ػ��屳��
	
	private int x, y;
	private Collection<Dot> dots = new ArrayList<>();
	private Collection<Line> lines = new ArrayList<>();
	// private Collection<Polygon> polygons = new ArrayList<>();
	private Polygon polygon;
	private Collection<Dot> twoDotInLine = new ArrayList<>();
	private int indexDot = 0;
	private int indexLine = 0;
	private boolean isCompletePolygon = false;

	// SingleClick sc = new SingleClick();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopologyCreate window = new TopologyCreate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TopologyCreate() {

		initialize();
       
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("TopologyCreate");
		setFont(new Font("Monaco", Font.PLAIN, 14));
		getContentPane().setFont(new Font("Monaco", Font.PLAIN, 14));
		setBounds(200, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);		
		toolBar.setRollover(true);
		JButton btnNewButton = new JButton("CreateTopology");
		btnNewButton.setFont(new Font("Monaco", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			// ////////////////////////��������///////////////////////////////////////////
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent e) {
				if(!isCompletePolygon){
					JOptionPane.showMessageDialog(null, "����CompletePolygon�Ապ϶����!", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					TopologyBuilder topologyBuilder = new TopologyBuilder(
							polygon = new Polygon(lines));

					int LineCount = topologyBuilder.LineArray.length;
					String[][] row = new String[LineCount][3];
					String[] column = { "����", "������", "�Ҷ����" };
					int RowIndex = 0;
					for (Iterator<Line> i = lines.iterator(); i.hasNext();) {
						Line lineToPrint = i.next();
						row[RowIndex][0] = lineToPrint.toString();
						row[RowIndex][1] = lineToPrint.leftPoly.toString();
						row[RowIndex][2] = lineToPrint.rightPoly.toString();
						RowIndex++;
					}
				
					table_1 = new JTable(row, column);
					js = new JScrollPane(table_1);
					js.setPreferredSize(new Dimension(100, 150));					
					getContentPane().add(js, BorderLayout.SOUTH);
					validate();
					repaint();
				}

			}
		});
		toolBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("DeleteLastNode");
		btnNewButton_1.addActionListener(new ActionListener() {
			//////////////////////////////////ɾ�������///////////////////////////////////////////////////////////////
			public void actionPerformed(ActionEvent e) {
				if (dots.isEmpty()) {
					JOptionPane.showMessageDialog(null, "���е��Ѿ�ɾ��!", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Graphics g = panel_1.getGraphics();
					((ArrayList<Dot>) dots).get(dots.size() - 1).draw(g,
							Color.white);
					dots.remove(((ArrayList<Dot>) dots).get(dots.size() - 1));
					indexDot--;
					if (indexDot <= 0)
						indexDot = 0;

					if (!lines.isEmpty()) {
						((ArrayList<Line>) lines).get(lines.size() - 1).draw(g,
								Color.white);
						lines.remove(((ArrayList<Line>) lines).get(lines.size() - 1));
						indexLine--;
						if (indexLine <= 0)
							indexLine = 0;
						/////�������˶���Σ���ɾ�����һ���ڵ���������������/////
						if (!lines.isEmpty() && isCompletePolygon) {
							((ArrayList<Line>) lines).get(lines.size() - 1)
									.draw(g, Color.white);
							lines.remove(((ArrayList<Line>) lines).get(lines
									.size() - 1));
							indexLine--;
							if (indexLine <= 0)
								indexLine = 0;
							isCompletePolygon = false;
						}
					}
				}
				repaint();
			}
		});
		btnNewButton_1.setBackground(Color.GRAY);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton eventButton = (JButton) e.getSource();
				eventButton.setBackground(Color.CYAN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JButton eventButton = (JButton) e.getSource();
				eventButton.setBackground(Color.gray);
			}
		});

		JButton btnCompletepolygon = new JButton("CompletePolygon");
		btnCompletepolygon.addActionListener(new ActionListener() {
			/////////���Ӷ���ε����һ����//////////
			public void actionPerformed(ActionEvent e) {
				if (dots.size() < 3) {
					JOptionPane.showMessageDialog(null, "������Ҫ������ſ��Թ��ɶ����!",
							"����", JOptionPane.ERROR_MESSAGE);
				} else {
					lines.add(new Line(
							((ArrayList<Dot>) dots).get(dots.size() - 1),
							((ArrayList<Dot>) dots).get(0), indexLine += 1));
					repaint();
					isCompletePolygon = true;
				}

			}
		});
		btnCompletepolygon.setFont(new Font("Monaco", Font.PLAIN, 12));
		toolBar.add(btnCompletepolygon);
		btnNewButton_1.setFont(new Font("Monaco", Font.PLAIN, 12));
		toolBar.add(btnNewButton_1);

		JButton btnDeleteall = new JButton("DeleteAll");
		btnDeleteall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dots.removeAll(dots);
				indexDot = 0;
				lines.removeAll(lines);
				indexLine = 0;
				isCompletePolygon = false;
				twoDotInLine.removeAll(twoDotInLine);
				remove(js);
				validate();
				repaint();
			}
		});
		btnDeleteall.setBackground(Color.GRAY);
		btnDeleteall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton eventButton = (JButton) e.getSource();
				eventButton.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JButton eventButton = (JButton) e.getSource();
				eventButton.setBackground(Color.gray);
			}
		});
		btnDeleteall.setFont(new Font("Monaco", Font.PLAIN, 12));
		toolBar.add(btnDeleteall);

		getContentPane().setFont(new Font("Monaco", Font.PLAIN, 14));
		setVisible(true);
		

		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		
 		addMouseListener(dc);

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		for (int i = 0; i < dots.size(); i++) {
			((ArrayList<Dot>) dots).get(i).draw(g, Color.red);
		}

		for (int i = 0; i < lines.size(); i++) {
			((ArrayList<Line>) lines).get(i).draw(g, Color.LIGHT_GRAY);
		}

	}

	// /////////////////�ڲ��࣬���ڴ���������¼�////////////////////////////////////
	public class DoubleClick extends MouseAdapter {
		private boolean flag = false; // �����ж��Ƿ��Ѿ�ִ��˫���¼� ,ÿִ��һ���¼��㽫����Ϊtrue
		private int clickNum = 0; // �����ж��Ƿ��ִ��˫���¼�, ÿִ��һ���¼��㽫����Ϊ0
		private MouseEvent me = null;
		Timer timer = new Timer(); // ����һ����ʱ��

		public void mouseClicked(MouseEvent e) { // ��дMouseAdapter��mouseClicked����
			me = e; // �����¼�Դ ����
			this.flag = false; // ÿ�ε����꣬��ʼ��˫���¼�ִ�б�־Ϊfalse

			if (this.clickNum == 1) // ��clickNum==1ʱִ��˫���¼�
			{
				this.mouseDoubleClicked(me);// ִ��˫���¼�
				this.clickNum = 0; // ִ����˫���¼��󣬳�ʼ��˫���¼�ִ�б�־Ϊ0
				this.flag = true; // ˫���¼���ִ��,�¼���־Ϊtrue
				return;
			}

			// clickNum!=1ʱִ������Ĵ���
			// ��ʱ����ʼִ��,��ʱ0.3���ȷ���Ƿ�ִ�е����¼�
			timer.schedule(new MyTimerTask(), new Date(), 300);// ��ϵͳ��ǰʱ�俪ʼ��ÿ��0.3s�ظ�ִ��һ��run()����
		}

		class MyTimerTask extends TimerTask // ��ʱ��������
		{
			private int n = 0;// ��¼��ʱ��ִ�д���

			public void run() {
				if (flag) {// ���˫���¼��Ѿ�ִ��,��ôֱ��ȡ������ִ��
					n = 0;
					clickNum = 0;
					this.cancel(); // ȡ���˼�ʱ������
					return;
				}
				if (n == 1) {// ��ʱ���ȴ�0.3���,˫���¼���δ����,ִ�е����¼�
					mouseSingleClicked(me);// ִ�е����¼�
					flag = true;
					clickNum = 0;
					n = 0;
					this.cancel(); // ȡ���˼�ʱ������
					return;
				}
				clickNum++;
				n++;
			}
		}

		public void mouseSingleClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(null, "��˫��", "����",
					JOptionPane.INFORMATION_MESSAGE);
			//System.out.println("Single Clicked!");
		}

		public void mouseDoubleClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			boolean GotIt = false;
			// //////////////////////ͨ��˫�������㲢����///////////////////////////
			if (!isCompletePolygon) {
				indexDot += 1;
				Dot dot = new Dot(x, y, indexDot);
				dots.add(dot);

				if (dots.size() >= 2) {
					indexLine += 1;
					Line line = new Line(((ArrayList<Dot>) dots).get(dots
							.size() - 2), ((ArrayList<Dot>) dots).get(dots
							.size() - 1), indexLine);
					lines.add(line);
				}
				// /////////////////////ͨ��˫���Ѳ����ĵ�������/////////////////////
			} else {
				for (Iterator<Dot> i = dots.iterator(); i.hasNext();) {
					Dot dot = (Dot) i.next();
					if (Math.abs((int) dot.getX() - x) < 16
							&& Math.abs((int) dot.getY() - y) < 16) {
						if(!twoDotInLine.contains(dot))
						{
							twoDotInLine.add(dot);
							GotIt = true;
							break;
						}
						else{
							JOptionPane.showMessageDialog(null, "�ظ����", "����",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				if (!GotIt) {
					JOptionPane.showMessageDialog(null, "�뾫ȷ��ע", "����",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (twoDotInLine.size() > 1) {
					lines.add(new Line(((ArrayList<Dot>) twoDotInLine).get(0),
							((ArrayList<Dot>) twoDotInLine).get(1), indexLine+=1));
					twoDotInLine.removeAll(twoDotInLine);
				}

			}
			repaint();
		}
	}
}
