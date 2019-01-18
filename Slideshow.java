package org.rogatio.slideshow;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * The Class Slideshow.
 */
public class Slideshow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3514007385985931081L;
	
	/** The Constant TIMEFRAME_IN_SECONDS. */
	static final int TIMEFRAME_IN_SECONDS = 5;

	/**
	 * Instantiates a new slideshow.
	 */
	public Slideshow() {
		PicturePanel pp = new PicturePanel();
		add(pp);
		
		// on ESC key close frame
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); 
        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ 
           private static final long serialVersionUID = 250834673044684738L;

			public void actionPerformed(ActionEvent e)
            {
                close();
            }
        });
        
        // on close window the close method is called
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) 
            {
                close();
            }
        });
	}
	
	/**
	 * Close.
	 */
	private void close() {
		System.exit(0);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Slideshow frame = new Slideshow();
		frame.setTitle("Display");
		frame.setLocationRelativeTo(null);
		frame.setMaximized(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);

	}

	/**
	 * The Class PicturePanel.
	 */
	class PicturePanel extends JPanel {
		
		/** The counter. */
		private int counter = 0;
		
		/** The image. */
		private ImageIcon[] image = new ImageIcon[3];
		
		/** The picture. */
		JLabel picture = new JLabel();
		
		/**
		 * Instantiates a new picture panel.
		 */
		public PicturePanel() {
			Timer timer = new Timer(1000 * TIMEFRAME_IN_SECONDS, new TimerListener());
			timer.start();
			add(picture);
			this.setBackground(Color.decode("#000000"));
			
		}

		/**
		 * The listener interface for receiving timer events.
		 * The class that is interested in processing a timer
		 * event implements this interface, and the object created
		 * with that class is registered with a component using the
		 * component's <code>addTimerListener<code> method. When
		 * the timer event occurs, that object's appropriate
		 * method is invoked.
		 *
		 * @see TimerEvent
		 */
		class TimerListener implements ActionListener {
			
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				repaint();
				updateUI();
				update(getGraphics());
				counter++;
				if (counter >= image.length) {
					counter = 0;
				}

			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon img = new ImageIcon("slide" + counter + ".jpg");
			
			// get size of parent panel. add 1 to avoid zero size.
			int w = getParent().getWidth() + 1;
			int h = getParent().getHeight() + 1;
			
			// scale image
			image[counter] = scaleImage(img, w, h);
			picture.setIcon(image[counter]);
		}

		/**
		 * Scale image to given size
		 *
		 * @param icon the icon
		 * @param w the w
		 * @param h the h
		 * @return the image icon
		 */
		public ImageIcon scaleImage(ImageIcon icon, int w, int h) {
			int nw = icon.getIconWidth();
			int nh = icon.getIconHeight();

			if (icon.getIconWidth() > w) {
				nw = w;
				nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
			}

			if (nh > h) {
				nh = h;
				nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
			}

			return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
		}

	}

	/**
	 * Sets the JFrame maximized.
	 *
	 * @param maximized the boolean which maximize the jframe
	 */
	public void setMaximized(boolean maximized) {
		if (maximized) {
			DisplayMode mode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
			Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
			this.setMaximizedBounds(new Rectangle(mode.getWidth() - insets.right - insets.left,
					mode.getHeight() - insets.top - insets.bottom));
			this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			this.setExtendedState(JFrame.NORMAL);
		}
	}

}
