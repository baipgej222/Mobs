package pw.owen.mobs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import pw.owen.mobs.run.Main;


public class ErrorReport {
	private static List<String> es = new ArrayList<String>();
	private static int i = 0;
	private static File cfg = null;
	public static void report(String str) {
		es.add(str);
		i++;
		if (i > 20) {
		test();
			i = 0;
		}
	}



	public static void update() throws IOException {
		if (!Main.isAutoErrorReporting())
			return;
		test();
		if (es.size() == 0)
			return;
		Send.sendConsole("��ʼ�ϱ�������Ϣ");
		Send.sendConsole("���п��ܻ�������Ĳ�����˽");
		Send.sendConsole("�ұ�֤����Щ������Ϣ���Ų����֮������ɾ�����Ҳ�����й¶");
		Send.sendConsole("��ʼ�ϱ��ռ��Ĵ�����Ϣ...");
		String str = "";
		str += "Mobs�汾:" + Main.getV() + "\r\n";
		str += "����˰汾:" + Bukkit.getVersion() + "\r\n";
		str += "Bukkit�˰汾:" + Bukkit.getBukkitVersion() + "\r\n";
		// str += "��������ַ:" + Bukkit.getServer().getIp() + ":"
		// + Bukkit.getServer().getPort() + "\r\n";
		str += "ϵͳ��Ϣ:\r\n";
		Properties props=System.getProperties();
		
		str += "        ����ϵͳ����:" + props.getProperty("os.name") + "\r\n";
		str += "        ����ϵͳ����:" + props.getProperty("os.arch") + "\r\n";
		str += "        ����ϵͳ�汾:" + props.getProperty("os.version") + "\r\n";
		str += "        Java ������е��ڴ�����:" + Runtime.getRuntime().totalMemory()
				+ "\r\n";
		str += "        Java �������ͼʹ�õ�����ڴ���:" + Runtime.getRuntime().maxMemory()
				+ "\r\n";
		str += "        Java ������еĿ����ڴ���:" + Runtime.getRuntime().freeMemory()
				+ "\r\n";
		str += "        Java ����ʱ�����汾:" + props.getProperty("java.version")
				+ "\r\n";
		str += "        Java ����ʱ������Ӧ��:" + props.getProperty("java.vendor")
				+ "\r\n";
		str += "        Java ��װĿ¼:" + props.getProperty("java.home") + "\r\n";
		str += "        Java ������淶�汾:"
				+ props.getProperty("java.vm.specification.version") + "\r\n";
		str += "        Java ������淶��Ӧ��:"
				+ props.getProperty("java.vm.specification.vendor") + "\r\n";
		str += "        Java ������淶����:"
				+ props.getProperty("java.vm.specification.name") + "\r\n";
		str += "        Java �����ʵ�ְ汾:" + props.getProperty("java.vm.version")
				+ "\r\n";
		str += "        Java �����ʵ�ֹ�Ӧ��:" + props.getProperty("java.vm.vendor")
				+ "\r\n";
		str += "        Java �����ʵ������:" + props.getProperty("java.vm.name")
				+ "\r\n";
		str += "        Java ����ʱ�����淶�汾:"
				+ props.getProperty("java.specification.version") + "\r\n";
		str += "        Java ����ʱ�����淶��Ӧ��:"
				+ props.getProperty("java.specification.vendor") + "\r\n";
		str += "        Java ����ʱ�����淶����:"
				+ props.getProperty("java.specification.name") + "\r\n";

		String sst = "";
		Plugin[] ps = Bukkit.getPluginManager().getPlugins();
		for (int i = 0; i < ps.length; i++) {
			if (i != 0)
				sst += "\r\n";

			sst += "        " + ps[i].getName();

		}
		str += "����б�:\r\n";
		str += sst + "\r\n";
		;
		str += "�����ļ�:\r\n";
		if(cfg!=null)
 {
			FileReader fr = new FileReader(cfg);
			int r = 0;
			List<Integer> cs = new ArrayList<Integer>();
			while ((r = fr.read()) != -1)
				cs.add(r);
			char[] ccs = new char[cs.size()];
			for (int ii = 0; ii < cs.size(); ii++) {
				ccs[ii] = (char) (int) cs.get(ii);
			}

			str += new String(ccs);
		} else
			str += "---��---";

		str += "\r\n========================";

		for (int l = 0; l < es.size(); l++) {
			if (l != 0)
				str += "\r\n*******************************";

			str += "\r\n" + es.get(l);
		}
		update(str);
		File file = new File(new File(Main.getMain().getDataFolder()
				.getAbsolutePath()), "report.txt");
		if (!file.exists())
			file.createNewFile();

		try (FileWriter fw = new FileWriter(file)) {
		fw.write(str);
		fw.close();

		}
		Send.sendConsole("�ϴ��ɹ�,�����Ե����Ŀ¼��/Mobs/report.txt�鿴���һ���ϴ�����Ϣ");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
	}

	private static void update(String str) throws UnsupportedEncodingException {
		str = new String(str.getBytes(), "GBK");
		String[] strs = str.split("\n");
		String add = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			add = addr.getHostName();
		} catch (UnknownHostException e) {
			add = "UnknownHostException";
		}
		String name = "Mobs_" + add + "_" + System.currentTimeMillis();

		String h = "type=error_report_add&name=" + name + "&value=";
		int one = 1024 * 512;
		if (str.length() > one) {
			int ls = (int) (str.length() / one);
			List<String> sts = new ArrayList<String>();
			for (int ii = 0; ii < ls; ii++)
				sts.add(str.substring(ii * one, (ii + 1) * one));
			if (str.length() % one != 0)
				sts.add(str.substring(ls * one, str.length()));

			for (int ii = 0; ii < sts.size(); ii++)
				sendPost("http://www.rpgmc.cn/error_report.do",
						h + URLEncoder.encode(sts.get(ii), "GBK"));

		} else
			sendPost("http://www.rpgmc.cn/error_report.do",
					h + URLEncoder.encode(str, "GBK"));

	}

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����ʵ�ʵ�����
			connection.connect();
			// ��ȡ������Ӧͷ�ֶ�
			Map<String, List<String>> map = connection.getHeaderFields();
			// �������е���Ӧͷ�ֶ�
			// for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
			// }
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر�������
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static void test() {
		es = new ArrayList<String>(new HashSet<String>(es));

	}

	public static void setCfg(File c) {
		cfg = c;

	}
}
