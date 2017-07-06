/**
*	@author armag
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

class TileGame {
	PaintPanel pp;
	Image img;
	int iw, ih;
	JFrame jfrm;
	TileGame() {

		jfrm = new JFrame("The Tiles Game");
		pp = new PaintPanel(jfrm);
		pp.setPreferredSize(new Dimension(pp.iw, pp.ih));
		jfrm.add(pp, BorderLayout.CENTER);
		JPanel pInf = new JPanel(new FlowLayout());
		JButton bNewI = new JButton("New Image");
		bNewI.addActionListener((ae)->{
			pp.setRandImage();
			pp.setPreferredSize(new Dimension(pp.iw, pp.ih));
			jfrm.pack();
			pp.repaint();

		});
		pInf.add(bNewI);
		JButton bNewG = new JButton("New Game");
		bNewG.addActionListener((ae)->{

			pp.setRandImage();
			pp.setPreferredSize(new Dimension(pp.iw, pp.ih));
			jfrm.pack();
			pp.shuffleIt();
			pp.repaint();
		});
		pInf.add(bNewG);
		JButton bShuf = new JButton("Shuffle");
		bShuf.addActionListener((ae)->{pp.shuffleIt();pp.repaint();});
		pInf.add(bShuf);
		JButton bN = new JButton("Change # of tiles");
		bN.addActionListener((ae)->{
			class ChangeDialog extends JDialog {
				JTextField entry;
				ChangeDialog() {
					super(jfrm, "Change Number of Tiles in a row and column" , true);
					setLayout(new FlowLayout());
					setLocationRelativeTo(null);
					setSize(400, 150);
					JPanel jt = new JPanel();
					jt.add(new JLabel("<html><body><p style='width: 300px;'>Enter any value greater than 1.<br>Click on 'Set' to change number of tiles.</p></body></html>"));
					add(jt);
					entry = new JTextField("00" + pp.tc);
					add(entry);
					JButton bSet;
					JPanel jpb = new JPanel(new FlowLayout());
					jpb.add(bSet = new JButton("Set"));
					bSet.addActionListener((ae)->{
						pp.tc = Integer.parseInt(entry.getText());
						pp.initOrd();
						pp.setRandImage();
						pp.setPreferredSize(new Dimension(pp.iw, pp.ih));
						jfrm.pack();
						pp.shuffleIt();
						pp.repaint();
						dispose();
					});
					JButton bCan;
					jpb.add(bCan = new JButton("Cancel"));
					bCan.addActionListener((ae)->{dispose();});
					add(jpb);

				}
				
			}
			new ChangeDialog().setVisible(true);

		}); 
		pInf.add(bN);
		pInf.setPreferredSize(new Dimension(MyConstants.maxL, 40));
		jfrm.add(pInf, BorderLayout.SOUTH);
		jfrm.pack();
		jfrm.setLocationRelativeTo(null);
		jfrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jfrm.setVisible(true);
		jfrm.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(null, "Your game is incomplete. All progress made will be lost. Do you wish to close it?","Confirm Unsaved Progress",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

		       if(result == JOptionPane.YES_OPTION)
		               System.exit(0);
		       
			}
		});
	}
        
        public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(()->{new TileGame();});
		} catch(InterruptedException | InvocationTargetException e) {}
	}
}
