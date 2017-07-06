/**
*	@author armag
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

final class PaintPanel extends JPanel {


	/**
	* @param tc the number or tiles in a row (and column)
	* @param ord array to store postion of tiles
	*/
	int tc = 2;
	Image img; 
	int iw, ih, th, tw;
	int ord[];
	Image im[];
	JFrame jfrm;
	PaintPanel(JFrame jfrm) {
		this.jfrm = jfrm;
		setBorder(BorderFactory.createLineBorder(Color.black, 3));
		initOrd();
		setRandImage();
		shuffleIt();
		addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				int mX = me.getX();
				int mY = me.getY();
				int tX = mX/tw;
				int tY = mY/th;
				/*
					if tile is movable

				*/	
				if((tX+1)<tc) {
					if(ord[tY*tc+tX+1]==(tc*tc-1)) {
						
						ord[tY*tc+tX+1] = ord[tY*tc+tX]; 
						ord[tY*tc+tX] = tc*tc-1;
						repaint();
						check();
						return;
					}


				}
				if(tX>0) {
					if(ord[tY*tc+tX-1]==(tc*tc-1)) {
						
						ord[tY*tc+tX-1] = ord[tY*tc+tX]; 
						ord[tY*tc+tX] = tc*tc-1;
						repaint();
						check();
						return;
					}


				}
				if((tY+1)<tc) {
					if(ord[(tY+1)*tc+tX]==(tc*tc-1)) {

						ord[(tY+1)*tc+tX] = ord[tY*tc+tX]; 
						ord[tY*tc+tX] = tc*tc-1;
						repaint();
						check();
						return;
					}


				}
				if(tY > 0) {
					if(ord[(tY-1)*tc+tX]==(tc*tc-1)) {

						ord[(tY-1)*tc+tX] = ord[tY*tc+tX]; 
						ord[tY*tc+tX] = tc*tc-1;
						repaint();
						check();
						return;
					}
				}
				
			}
		});
		
	}

	public void initOrd() {
		ord = new int[(tc+1)*(tc+1)];
		im = new Image[tc*tc];

		for(int y=0; y<tc; y++) {
			for(int x=0; x<tc; x++) {
				int i = y*tc + x;
				ord[i] = i;
			}
		}
	}

	private void extractTiles() {
		try { 
			MediaTracker t = new MediaTracker(this);
			iw = img.getWidth(null);
			ih = img.getHeight(null);
			tw = iw / tc;
			th = ih / tc;
			for(int y=0; y<tc; y++) {
				for(int x=0; x<tc; x++) {
					int i = y*tc + x;
					im[i] = createImage(new FilteredImageSource(img.getSource(), 
						new CropImageFilter(x*tw, y*th, x*tw + tw, y*th + th)));
					t.addImage(im[i], i+1);
				}
			}
			t.waitForAll();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void setRandImage() {
		Image timg;
		try {

			File file = new File("img");
			String[] imageNames = file.list(); 
			Random rand = new Random();
			int index = rand.nextInt(imageNames.length);
			System.out.println("img/" + imageNames[index]);

			timg = ImageIO.read(new File("img/" + imageNames[index]));
			iw = timg.getWidth(null);
			ih = timg.getHeight(null);
			System.out.println(iw + " " + ih);
			if(iw>ih)
				timg = timg.getScaledInstance(MyConstants.maxL, -1, Image.SCALE_DEFAULT);
			else
				timg = timg.getScaledInstance(-1, MyConstants.maxL, Image.SCALE_DEFAULT);

			this.img = timg;

		} catch(Exception e) {
			System.out.println(e);
		}
		extractTiles();


	}
	private void check() {
		if(sameCount()==tc*tc) {

		/*
			Dialog box with message after completion of game.
		*/
		class SolvedDialog extends JDialog {
			SolvedDialog() {
				super(jfrm, "Game Completed" , true);
				setLayout(new FlowLayout());
				setLocationRelativeTo(null);
				setSize(400, 150);
				JPanel jt = new JPanel();
				jt.add(new JLabel("<html><body><p style='width: 300px;'>Hurray! You completed the puzzle game successfully.<br>Click on 'Return to Arena' to play a 'New Game' or re-'Shuffle'.</p></body></html>"));
				add(jt);
				JButton bquit;
				JPanel jpb = new JPanel(new FlowLayout());
				jpb.add(bquit = new JButton("Quit Game"));
				bquit.addActionListener((ae)->{System.exit(0);});
				JButton bnew;
				jpb.add(bnew = new JButton("Return to Arena"));
				bnew.addActionListener((ae)->{dispose();});
				add(jpb);

			}
		}

		new SolvedDialog().setVisible(true);
		}
	}
	private int sameCount() {
		int same = 0;
		for(int i=0; i<tc*tc; i++) {
			if(ord[i] == i) {
				same++;
			}
		}
		return same;
	}
	public void shuffleIt() {
		for(int i=0; i<2*tc*tc; i++) {
			int si = (int) (Math.random()*(tc*tc));
			int di = (int) (Math.random()*(tc*tc));
			int tmp = ord[si];
			ord[si] = ord[di];
			ord[di] = tmp;
		}
		for(int i=0; i<tc*tc; i++) {
			if(ord[i] == (tc*tc-1)) {
				ord[i] = ord[tc*tc-1]; 
				ord[tc*tc-1] = tc*tc-1;
			}
		}
		if(sameCount()==tc*tc)
			shuffleIt();

	}

        @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int y=0; y<tc; y++) {
			for(int x=0; x<tc; x++) {
				int i = y*tc+x;
				if(ord[i]==(tc*tc-1)) {
					g.setColor(Color.black);
					g.fillRect(x*tw, y*th, tw, th);
				} else 
					g.drawImage(im[ord[i]], x*tw, y*th, null);
			}
		}
	}
	
}
