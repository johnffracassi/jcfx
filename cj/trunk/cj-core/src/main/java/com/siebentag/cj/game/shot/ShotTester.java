package com.siebentag.cj.game.shot;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.siebentag.cj.game.event.ObjectEvent;
import com.siebentag.cj.queue.Event;
import com.siebentag.cj.queue.EventListener;
import com.siebentag.cj.util.format.Formatter;
import com.siebentag.cj.util.math.Point3D;
import com.siebentag.gui.VerticalFlowLayout;

@SuppressWarnings("serial")
public class ShotTester extends JFrame implements MouseMotionListener, ActionListener, EventListener
{
	private final Logger log = Logger.getLogger(ShotTester.class);

	private ZonePanel pnlZone = new ZonePanel();
	private JLabel lblTitle = new JLabel();
	private JLabel lblLocation = new JLabel();
	private JComboBox cboShot;
	private JTextArea lblInfo = new JTextArea();
	private Shots shots;
	private BuilderPanel pnlBuilder;
	
	public static void main(String[] args) 
	{
		ShotTester st = new ShotTester();
		st.setVisible(true);
	}
	
	private ShotTester()
	{
		reload();
		build();
		
		setShot(shots.getShotList().get(0));
		
		pnlZone.addMouseMotionListener(this);
		setSize(600, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void build()
	{
		setLayout(new VerticalFlowLayout(3));

		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(Shot shot : shots.getShotList())
			model.addElement(shot);
		cboShot = new JComboBox(model);
		add(cboShot);
		cboShot.addActionListener(this);
		
		add(pnlZone);
		add(lblLocation);
		
		JPanel pnlHolder = new JPanel(new BorderLayout());
		pnlBuilder = new BuilderPanel();
		pnlHolder.add(lblInfo, BorderLayout.WEST);
		pnlHolder.add(pnlBuilder, BorderLayout.CENTER);
		
		lblInfo.setBorder(BorderFactory.createEtchedBorder());
		add(pnlHolder);
		
		lblInfo.setPreferredSize(new Dimension(350, 200));
		
		pnlZone.addMouseListener(pnlBuilder);
	}
	
	public Class<?>[] register() 
	{
		return new Class[] {
			ObjectEvent.class
		};
	}
	
	@SuppressWarnings("unchecked")
    public void event(Event gameEvent)
	{
		if(gameEvent instanceof ObjectEvent)
		{
			ObjectEvent<Point3D> ev = (ObjectEvent<Point3D>)gameEvent;
			Point3D loc = ev.getObj();
			double scale = 200.0;
			double x = (scale + loc.getX() * scale);
			double y = (2 * scale - loc.getZ() * scale);
			pnlZone.ballLoc = new Point((int)x, (int)y);
			log.debug("setting ball loc to " + Formatter.formatPoint(x,y) + " / " + loc);
			
			repaint();
			pnlZone.repaint();
		}
	}
	
	private void setShot(Shot shot)
	{
		lblTitle.setText(shot.getName());
		pnlZone.setShot(shot);
	}
	
	private void reload()
	{
		shots = getShots();

//		DefaultComboBoxModel model = new DefaultComboBoxModel();
//		for(Shot shot : shots.getShotList())
//			model.addElement(shot);
//		cboShot.setModel(model);
	}
	
	private Shots getShots()
	{
		ShotsDigester sd = new ShotsDigester();
		sd.loadShots();
		return sd.getShots();
	}

	private Shot getShot()
	{
		return (Shot)cboShot.getSelectedItem();
	}
	
	private void analyse(double x, double y)
	{
		StringBuffer buf = new StringBuffer();
		
		List<Zone> zones = getShot().getZones(x, y);
			
		if(zones.size() > 0)
		{
			for(Zone zone : zones)
			{
				buf.append(zone.toString() + "\n");
			}
		}
		
		lblInfo.setText(buf.toString());
	}
	
	public void mouseDragged(MouseEvent arg0) { }

	public void mouseMoved(MouseEvent ev) 
	{
		double x = (ev.getX() - 200) / 200.0;
		double y = 2.0 + (-ev.getY() / 200.0);
		lblLocation.setText(Formatter.formatPoint(x, y) + "  " + Formatter.formatPoint(ev.getX(), ev.getY()));
		analyse(x, y);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if(ev.getSource() == cboShot)
		{
			setShot((Shot)cboShot.getSelectedItem());
		}
	}
	
	class BuilderPanel extends JPanel implements MouseListener
	{
		StringBuffer buf = new StringBuffer();
		
		public BuilderPanel()
		{
			JButton btnNewShot = new JButton("New Shot");
			JButton btnNewZone = new JButton("New Zone");
			JButton btnCopy = new JButton("Copy XML");
			JButton btnReload = new JButton("Reload");
			
			setLayout(new VerticalFlowLayout(2));
			add(btnNewShot);
			add(btnNewZone);
			add(btnCopy);
			add(btnReload);
			
			btnReload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					reload();
				}
			});
			
			btnNewShot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					newShot();
				}
			});
			
			btnNewZone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					endZone();
					newZone();
				}
			});
			
			btnCopy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					copyToClipboard();
				}
			});
			
			setBorder(BorderFactory.createEtchedBorder());
			setPreferredSize(new Dimension(200, 200));
		}
		
		private void newShot()
		{
			buf = new StringBuffer();
			buf.append("<shot name=\"\">\n");
			buf.append("\t<zones>\n");
			buf.append("\t\t<zone id=\"default-zone\" priority=\"1\" />\n");
			buf.append("\t\t<zone id=\"stumps\" priority=\"2\" />\n");
			buf.append("\t\t<zone id=\"body\" priority=\"3\" />\n");
			buf.append("\t\t<zone id=\"head\" priority=\"4\" />\n\n");
			newZone();
		}
	
		private void endZone()
		{
			buf.append("\t\t\t</shape>\n");
			buf.append("\t\t</zone>\n");
		}
		
		private void newZone()
		{
			buf.append("\t\t<zone name=\"\" priority=\"\">\n");
			buf.append("\t\t\t<consequence type=\"com.siebentag.cj.cricket.game.shot.Hit\" />\n");
			buf.append("\t\t\t<probability type=\"com.siebentag.cj.game.math.prob.Linear\">\n");
			buf.append("\t\t\t\t<param name=\"maximum\" value=\"20\" />\n");
			buf.append("\t\t\t\t<param name=\"minimum\" value=\"50\" />\n");
			buf.append("\t\t\t</probability>\n");
			buf.append("\t\t\t<shape>\n");
		}
		
		private void copyToClipboard()
		{
			String tail =   "\t\t\t</shape>\n" +
							"\t\t</zone>\n" +
							"\t</zones>\n" +
							"</shot>\n";
			
			System.out.println(buf.toString() + tail);
		}
		
		public void mouseClicked(MouseEvent ev) 
		{
			addPoint(Formatter.format((ev.getX() - 200) / 200.0) + "," + Formatter.format(2.0 + (-ev.getY()) / 200.0));
		}
	
		private void addPoint(String pt)
		{
			buf.append("\t\t\t\t<point>" + pt + "</point>\n");
		}
		
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) { }
		public void mousePressed(MouseEvent arg0) { }
		public void mouseReleased(MouseEvent arg0) { }
	}
}

@SuppressWarnings("serial")
class ZonePanel extends JPanel
{
	Shot shot = null;
	Point2D ballLoc = null;
	
	public ZonePanel()
	{
		setPreferredSize(new Dimension(400, 400));
		setBorder(BorderFactory.createEtchedBorder());
	}
	
	public void setShot(Shot shot)
	{
		this.shot = shot;
		repaint();
	}
	
	@Override public void paint(Graphics graphics) 
	{
		super.paint(graphics);
		
		Graphics2D g = (Graphics2D)graphics;
		
		if(shot != null)
		{
			for(Zone zone : shot.getZones().getZonesList())
			{
				if(!"default-zone".equalsIgnoreCase(zone.getId()))
				{
					try
					{
						Polygon p = zone.getShape().getPolygon();
						
						if(zone.getId() == null)
							g.setColor(Color.BLUE);
						else if(zone.getId().equalsIgnoreCase("stumps"))
							g.setColor(Color.GREEN);
						else
							g.setColor(Color.RED);
						
						g.fillPolygon(p);
						g.setColor(g.getColor().darker());
						g.drawPolygon(p);
					}
					catch(Exception ex)
					{
	//					ex.printStackTrace();
					}
				}
			}
		}

		Stroke s = g.getStroke();
		g.setColor(Color.ORANGE);
		g.setStroke(new BasicStroke(5));
		g.drawRect(150, 250, 100, 150);
		g.setStroke(s);
		
		if(ballLoc != null)
		{
			g.setColor(Color.BLACK);
			g.fillOval((int)ballLoc.getX() - 5, (int)ballLoc.getY() - 5, 10, 10);
		}
	}
}