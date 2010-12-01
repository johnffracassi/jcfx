package au.com.barstard.blokey;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageTest {

	public static void main(String[] args) {
		ImageTest test = new ImageTest();
		test.run();
	}

	public void run() {
		String imageLocation = "/images/blokey/cricket-ball.jpg";
		URL imgURL = getClass().getResource(imageLocation);
		ImageIcon image = new ImageIcon(imgURL, "description");

		ImagePanel panel = new ImagePanel(image.getImage());

		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
