package cn.rpgmc.ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.AutumnSkin;
import org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel;

public class UI extends JFrame {
	JButton b1;
	JButton b2;
	Runnable run = null;
	static UI ui = null;
	AcListener ac = null;

	public static void main(String args[]) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
		} catch (Exception e) {
			System.out.println("Substance Raven Graphite failed to initialize");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UI w = new UI();
				w.setVisible(true);
			}
		});

	}

	UI() {
		super("Mobs��������");
		ui = this;

		this.setLocationRelativeTo(null);

ac= new AcListener();
		SubstanceLookAndFeel.setSkin(new AutumnSkin());

		ui.setResizable(false);
		ui.setSize(300, 188);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setVisible(true);
		ui.setLayout(new FlowLayout());

		b1 = new JButton("����<������չ���������̳�.doc>");

		b2 = new JButton("������");
		b1.addActionListener(ac); // ����¼�����
		b2.addActionListener(ac); // ����¼�����
		b1.setSize(300, 300);
		ui.getContentPane().add(b1);
		ui.getContentPane().add(b2);

	}


}
