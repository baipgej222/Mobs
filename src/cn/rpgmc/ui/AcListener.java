package cn.rpgmc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cn.rpgmc.bean.utils.ResourceManager;

public class AcListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(UI.ui.b1)) {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(1);
			int state = jfc.showOpenDialog(null);

			if (state == 1) {
				return;// �����򷵻�
			} else {
				File f = jfc.getSelectedFile();// fΪѡ�񵽵��ļ�

				File fff = new File(f, "������չ���������̳�.docx");
				ResourceManager.copyOf(
						ResourceManager.getResource("������չ���������̳�.docx"), fff);

			}

		}

	}

}
