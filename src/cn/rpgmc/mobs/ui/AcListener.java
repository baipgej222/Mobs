package cn.rpgmc.mobs.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import cn.rpgmc.mobs.utils.ResourceManager;

public class AcListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(UI.ui.b1)) {// �������̳ܽ�
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

		} else if (e.getSource().equals(UI.ui.b2)) {// �鿴����ҳ

			try {
				java.awt.Desktop.getDesktop().browse(
						new java.net.URI(
								"http://www.mcbbs.net/thread-494926-1-1.html"));
			} catch (Exception e1) {

			}
		} else if (e.getSource().equals(UI.ui.b3)) {// �鿴��Դҳ

			try {
				java.awt.Desktop.getDesktop().browse(
						new java.net.URI(
"https://github.com/owen199748/Mobs"));
			} catch (Exception e1) {

			}
		} else if (e.getSource().equals(UI.ui.b4)) {// ����
			try {
				java.awt.Desktop.getDesktop().browse(
						new java.net.URI("http://www.rpgmc.cn/pay.jsp"));
			} catch (Exception e1) {

			}

		} else if (e.getSource().equals(UI.ui.b5)) {// ������
			update();

		}

	}

	private void update() {

		try {
			URL url = new URL("http://www.rpgmc.cn/Mobs/ver.info");
			InputStreamReader is = new InputStreamReader(
					((HttpURLConnection) url.openConnection()).getInputStream());
			int i = 0;
			List<Byte> bs = new ArrayList<Byte>();
			while ((i = is.read()) != -1) {
				bs.add((byte) i);
			}
			byte[] bytes = new byte[bs.size()];
			for (int r = 0; r < bytes.length; r++)
				bytes[r] = bs.get(r);

			String str = new String(bytes, is.getEncoding());
			InputStream pis = ClassLoader.getSystemResource("plugin.yml")
					.openStream();
			bs.clear();
			while ((i = pis.read()) != -1) {
				bs.add((byte) i);
			}
			bytes = new byte[bs.size()];
			for (int r = 0; r < bytes.length; r++)
				bytes[r] = bs.get(r);
			String[] strs = new String(bytes).split("\n");
			String str2 = "";
			for(int r=0;r<strs.length;r++){
				String s = strs[r].trim();
				if (s.length() > 8)
					if (s.substring(0, 8).equalsIgnoreCase("version:"))
						str2 = s.substring(8).trim();

			}

			if (str2.equalsIgnoreCase(str)) {
				JOptionPane.showMessageDialog(null, "����ǰ(" + str2
						+ ")�����°汾!", "���¼��",
 JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane
						.showMessageDialog(null, "�����°汾(" + str
								+ "),��򿪷���ҳ��鿴��ϸ��Ϣ!",
						"���¼��",
						JOptionPane.QUESTION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getClass().getName(),
					"��ȡ�汾��Ϣʧ��",
 JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}

	}

}
