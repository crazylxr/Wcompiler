package compiler;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 
 * @ClassName GramerAnalyzer
 * @author lxr 
 * @date 2016年10月22日 
 * @版本 V 1.0 
 * @description LL(1)预测分析法 语法分析
 *
 */
public class GramerAnalyzer extends JFrame implements ActionListener {

	private File file;
	private static final long serialVersionUID = 1L;
	private FileDialog openDia;// 定义打开对话框
	private int firstComplete[] = null;// 存储已判断过first的数据
	private char first[][] = null;// 存储最后first结果
	int followComplete[] = null;// 存储已判断过follow的数据
	char follow[][] = null;// 存储最后follow结果
	char select[][] = null;// 存储最后select结果
	int LL = 0;// 标记是否为LL（1）
	String vt_tou[] = null;// 储存Vt
	Object shuju[][] = null;// 存储表达式数据
	char yn_null[] = null;// 存储能否推出空
	
	Map<String, Integer> map ;
	private JTextField tf1;
	private JTextField tf2;
	private JPanel p1, p2, p3, p4, p5;
	private JTextArea t1, t2, t3, t4;
	private JButton b0;
	private JButton b1, b2, b3, b4;
	JLabel l;
	JLabel inputString, inputGramer, l2, outputFF, outputTable;
	JTable table;
	Statement sta;
	Connection conn;
	ResultSet rs;
	DefaultTableModel dtm;
	String Vn[] = null;
	Vector<String> P = null;

	GramerAnalyzer() {
		setTitle("LL（1）语法分析器");
		setLocation(100, 100);
		setSize(1400, 500);

		tf1 = new JTextField(10);
		tf2 = new JTextField(10);

		l = new JLabel("→");
		l2 = new JLabel(" ");

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p5.setLayout(new GridLayout(2, 2));

		t1 = new JTextArea(8, 50);
		t2 = new JTextArea(1, 25);
		t3 = new JTextArea(10, 50);
		t4 = new JTextArea(7, 50);
		t4.setText("输入符号串，并对此进行分析");

		t4.setEditable(false);
		t3.setEditable(false);

		b1 = new JButton(" 进行语法分析");
		b2 = new JButton("输入");
		b3 = new JButton("清空");
		b4 = new JButton("打开文件");

		table = new JTable();
		JScrollPane jp1 = new JScrollPane(t1);// 文法输入区域
		JScrollPane jp2 = new JScrollPane(t2);
		JScrollPane jp3 = new JScrollPane(t3);
		p1.add(b4);
		p1.add(b1);
		p1.add(jp1);
		p1.setBorder(new TitledBorder("输入文法"));

		p2.add(jp2);
		p2.add(b2);
		p2.add(b3);
		p2.add(t4);
		p2.setBorder(new TitledBorder("输入表达式"));

		p3.add(jp3);
		p3.setBorder(new TitledBorder("First、Folow、分析结果"));

		p4.add(new JScrollPane(table));
		p4.setBorder(new TitledBorder("预测分析表"));

		p5.add(p1);
		p5.add(p2);
		p5.add(p3);
		p5.add(p4);
		add(p5);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		table.setPreferredScrollableViewportSize(new Dimension(570, 180));
		setVisible(true);
		openDia = new FileDialog(this, "打开", FileDialog.LOAD);

	}

	public void actionPerformed(ActionEvent e) {

		// 设置打开文件功能
		b4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();// 获取文件路径
				String fileName = openDia.getFile();// 获取文件名称
				// System.out.println(dirPath +"++"+ fileName);

				// 如果打开路径 或 目录为空 则返回空
				if (dirPath == null || fileName == null)
					return;

				t1.setText("");// 清空文本

				file = new File(dirPath, fileName);

				try {
					BufferedReader bufr = new BufferedReader(new FileReader(
							file));

					String line = null;
					while ((line = bufr.readLine()) != null) {
						t1.append(line + "\r\n");
					}
					bufr.close();
				} catch (IOException ex) {
					throw new RuntimeException("文件读取失败！");
				}

			}
		});

		
		
		// 进行语法分析
		if (e.getSource() == b1) {
			t3.setText("");
			int Vnnum = 0, k;
			Vn = new String[100];// 非终结符
			P = new Vector<String>();// Vector类实现了一个动态数组 用于存放文法
			String s_split[] = t1.getText().split("\n");// 把一个字符串分割成字符串数组
			map = new HashMap<>();//用于存放第几个所求非终结符与顺序
			int _index = 0;
			// 如果有分隔符的进行分割成两条语句 问题：多个分割符没处理
			ArrayList<String> newString = new ArrayList<String>();
			for (String str : s_split) {
				if (str.contains("|")) {
					int index = str.indexOf('|');
					String temp = str.substring(++index);
					String otherString = str.substring(0, 2) + temp;
					newString.add(str.substring(0, --index));
					map.put(str.substring(0, --index).charAt(0)+"", _index++);
					newString.add(otherString);
				} else {
					newString.add(str);
					map.put(str.charAt(0)+"", _index++);
				}
			}
			

			String[] s = new String[newString.size()];
			for (int i = 0; i < s.length; i++) {//将分割完的文法存到s数组里
				s[i] = newString.get(i);
			}

			for (int i = 0; i < s.length; i++) {
				if (s.length < 2) {
					t3.setText("输入错误，请重新输入");// 判断长度是否符合
					return;
				}

				// substring() 方法用于提取字符串中介于两个指定下标之间的字符，判断字符是不是字母
				if (s[i].charAt(0) <= 'Z' && s[i].charAt(0) >= 'A'
						&& s[i].charAt(1) == '→') {

					for (k = 0; k < Vnnum; k++) {
						if (Vn[k].equals(s[i].substring(0, 1))) {
							break;
						}
					}
					if (Vnnum == 0 || k >= Vnnum) {
						Vn[Vnnum] = s[i].substring(0, 1);// 存入Vn数据
						Vnnum++;
					}

					P.add(s[i]);
				}

				else {
					t3.setText("输入错误，请重新输入");
					return;
				}

			}

			// 依次求 FIRST集
			yn_null = new char[100];
			first = new char[Vnnum][100];
			int flag = 0;
			String firstVn[] = null;
			firstComplete = new int[Vnnum];
			for (int i = 0; Vn[i] != null; i++) {
				flag = 0;
				firstVn = new String[20];
				if ((flag = add_First(first[i], Vn[i], firstVn, flag)) == -1)
					return;
				firstComplete[i] = 1;
			}

			// 显示FIRST集
			t3.append("first集：" + "\n");
			for (int i = 0; Vn[i] != null; i++) {
				t3.append("first ( " + Vn[i] + " ) = { ");
				for (int j = 0; first[i][j] != '\0'; j++) {
					t3.append(first[i][j] + " , ");

				}
				t3.append(" }" + "\n");
			}

			// 求FOLLOW集
			follow = new char[Vnnum][100];
			String followVn[] = null;
			followComplete = new int[Vnnum];
			for (int i = 0; Vn[i] != null; i++) {
				flag = 0;
				followVn = new String[20];
				if ((flag = tianjiaFollow(follow[i], Vn[i], followVn, flag)) == -1)
					return;
				followComplete[i] = 1;
			}

			t3.append("follow集：" + "\n"); // 显示FOLLOW集
			for (int i = 0; Vn[i] != null; i++) {
				t3.append("follow ( " + Vn[i] + " ) = { ");
				for (int j = 0; follow[i][j] != '\0'; j++) {
					if(follow[i][j]!='#'){
						t3.append(follow[i][j] + " , ");
					}
					
				}
				t3.append("# }" + "\n");
			}

			select = new char[P.size()][100];
			for (int i = 0; i < P.size(); i++) { // 求SELECT**
				flag = 0;
				tianjiaSelect(select[i], (String) P.elementAt(i), flag);
			}

			t3.append("select集：" + "\n"); // 显示SELECT**
			for (int i = 0; i < P.size(); i++) {
				t3.append("select ( " + (String) P.elementAt(i) + " ) = { ");
				for (int j = 0; select[i][j] != '\0'; j++) {
					t3.append(select[i][j] + " , ");
				}
				t3.append(" }" + "\n");
			}

			for (int i = 0; Vn[i] != null; i++) {// 判断select交集是否为空
				int biaozhi = 0;
				char save[] = new char[100];
				for (int j = 0; j < P.size(); j++) {
					String t = (String) P.elementAt(j);
					if (t.substring(0, 1).equals(Vn[i])) {
						for (k = 0; select[j][k] != '\0'; k++) {
							if (puanduanChar(save, select[j][k])) {
								save[biaozhi] = select[j][k];
								biaozhi++;
							}

							else {// 当有交集时，不为LL（1）文法
								t3.append("不是LL（1）文法！！" + "\n");
								return;
							}
						}
					}
				}
			}

			char Vt[] = new char[100];
			int biaozhi = 0;
			for (int i = 0; i < P.size(); i++) {
				String t = (String) P.elementAt(i);
				for (int j = 2; j < t.length(); j++) {// 提取表达式右侧的终结符存入Vt
					if (t.charAt(j) > 'Z' || t.charAt(j) < 'A') {
						if (puanduanChar(Vt, t.charAt(j))) {
							Vt[biaozhi] = t.charAt(j);
							biaozhi++;
						}
					}
				}
			}

			if (puanduanChar(Vt, '#')) {// 若可推出空集，则将#加入Vt。
				Vt[biaozhi] = '#';
				biaozhi++;
			}

			vt_tou = new String[biaozhi + 1];// 根据select和表达式生成预测分析表
			shuju = new String[Vnnum][biaozhi + 1];
			String f = "";
			vt_tou[0] = f;
			for (int i = 0; i < biaozhi; i++) {
				vt_tou[i + 1] = String.valueOf(Vt[i]);
			}
			for (int i = 0; i < Vnnum; i++) {
				shuju[i][0] = Vn[i];
			}

			for (int i = 0; i < P.size(); i++) {
				String t = (String) P.elementAt(i);
				int j;
				for (j = 0; j < Vn.length; j++) {
					if (Vn[j].equals(t.substring(0, 1))) {
						break;
					}
				}

				for (k = 0; select[i][k] != '\0'; k++) {
					int y = puanduanXulie(Vt, select[i][k]);
					shuju[j][y + 1] = t.substring(1);
				}
			}
			dtm = new DefaultTableModel(shuju, vt_tou);// 显示预测分析表
			table.setModel(dtm);
			LL = 1;

		}

		// 将所有内容重置
		if (e.getSource() == b3) {// 清空列表
			tf1.setText("");
			tf2.setText("");
			t1.setText("");
			t2.setText("");
			t3.setText("");
			Vn = null;
			P = null;
			firstComplete = null;
			first = null;
			followComplete = null;
			follow = null;
			select = null;
			dtm = new DefaultTableModel();
			table.setModel(dtm);

		}

	}

	/**
	 * 
	 * @desciption 求first集合
	 * @param a 存放终结符
	 * @param b 所求的符号
	 * @param firstVn 非终结符
	 * @param flag 第几个符号
	 * @return
	 */
	private int add_First(char a[], String b, String firstVn[], int flag) { // 计算FIRST**（递归）
		//TODO
		if (puanduanString(firstVn, b.charAt(0))) {
			addString(firstVn, b);
		}
		else {
			return flag;
		}

		for (int i = 0; i < P.size(); i++) {
			String t = (String) P.elementAt(i);
			for (int k = 2; k < t.length(); k++) {
				if (t.substring(0, 1).equals(b)) {//箭头左端的非终结符是不是等于所求的非终结符
					if (t.charAt(k) > 'Z' || t.charAt(k) < 'A') {//是终结符
						if (flag == 0 || puanduanChar(a, t.charAt(k))) {
							if (t.charAt(k) == '#') {// #时直接加入first
								if (k + 1 == t.length()) {
									a[flag] = t.charAt(k);
									flag++;
								}

								int flag1 = 0;
								for (int j = 0; yn_null[j] != '\0'; j++) {// 所求非终结符进入yn_null**
									if (yn_null[j] == b.charAt(0)) {// 判断能否推出空
										flag1 = 1;
										break;
									}
								}

								if (flag1 == 0) {
									int j;
									for (j = 0; yn_null[j] != '\0'; j++) {
									}
									yn_null[j] = b.charAt(0);
								}
								continue;
							}

							else {// 终结符直接加入first**
								a[flag] = t.charAt(k);
								flag++;
								break;
							}
						}

						break;
					}

					else {// 非终结符
						if (!puanduanString(Vn, t.charAt(k))) {
							int p = firstComplete(t.charAt(k));// 当该非终结符的first已经求出
							if (p != -1) {
								flag = addElementFirst(a, p, flag);// 直接加入所求first
							} else if ((flag = add_First(a,
									String.valueOf(t.charAt(k)), firstVn, flag)) == -1)
								return -1;
							int flag1 = 0;
							for (int j = 0; yn_null[j] != '\0'; j++) {// 当非终结符的first有空
								if (yn_null[j] == t.charAt(k)) {
									flag1 = 1;
									break;
								}
							}

							if (flag1 == 1) {// 当非终结符的first能推出空
								if (k + 1 == t.length() && puanduanChar(a, '#')) {// 之后无符号，且所求first无#
									a[flag] = '#';// first中加入#
									flag++;
								}
								continue;// 判断下一个字符
							} else {
								break;
							}
						}

						else {// 错误
							t3.setText("文法输入有误" + "\n");
							return -1;
						}
					}
				}
			}
		}

		return flag;
	}

	/**
	 * @description 求follow集
	 * @param a //存放follow集 （终结符）
	 * @param b   非终结符(所求的符号)
	 * @param followVn
	 * @param flag
	 * @return
	 */
	private int tianjiaFollow(char a[], String b, String followVn[], int flag) { // 计算FOLLOW**（递归）
		// TODO

		if (puanduanString(followVn, b.charAt(0))) {
			addString(followVn, b);
		} else {
			return flag;
		}
		
		if (b.equals("S")) {// 当为S时#存入follow 算法一
			a[flag] = '#';
			flag++;
		}

		for (int i = 0; i < P.size(); i++) {
			String t = (String) P.elementAt(i); // 得到一条文法
			
			for (int j = 2; j < t.length(); j++) {
				if (t.charAt(j) == b.charAt(0) && j + 1 < t.length()) { //t.charAt(j)为非终结符，
					if ((t.charAt(j + 1) != '\0')) {
						if ((t.charAt(j + 1) > 'Z' || t.charAt(j + 1) < 'A')&&t.charAt(j + 1) != '\r') {// t.charAt(j+1)为终结符
							
							if(b.charAt(0)=='T'){
								System.out.println("T");
							}
							
							if (flag == 0 || puanduanChar(a, t.charAt(2))) {// 自身
								if(a[flag]!='#'){
									a[flag] = t.charAt(j + 1);//把终结符存到follow集里
									flag++;
								}
							}
						} else if(t.charAt(j + 1) != '\r'){// t.charAt(j + 1)为非终结符并且不是'\r'
							int k;
							for (k = 0; Vn[k] != null; k++) {
								if (Vn[k]
										.equals(String.valueOf(t.charAt(j + 1)))) {
									break;// 找寻下一个非终结符的Vn位置
								}
							}
							
							flag = addElementFirst(a, k, flag);// 把下一个非终结符first加入所求follow集
							for (k = j + 1; k < t.length(); k++) {
								if ((t.charAt(j + 1) > 'Z'&& t.charAt(j + 1) < 'A')) {
									int p = followComplete(t.charAt(0)); //p是第几个非终结符
									a = followBToFollowA(a,p,t);
									flag = addElementFollow(a, p, flag);
									break;
								} else {
									if (panduan_kong(t.charAt(k))) {
//										
									} else {
										break;
									}
								}
							}

							if (k >= t.length()-1) {// 下一个非终结符可推出空，把表达式左边非终结符的follow集加入所求follow集
								System.out.println("come on");
								int p = followComplete(t.charAt(0)); //p是第几个非终结符
								if (p != -1) {
									
									a = followBToFollowA(a,p,t);
									flag = addElementFollow(a, p, flag);
								} else if ((flag = tianjiaFollow(a,
										String.valueOf(t.charAt(0)), followVn,
										flag)) == -1) {
									return -1;
								}
							}
						}else{//如果t.charAt(j+1)=='r',直接将Follow(B)插入到Follow(A)中
							int p = followComplete(t.charAt(0)); //p是第几个非终结符
							a = followBToFollowA(a,p,t);
							flag = addElementFollow(a, p, flag);
						}
					}

					else {// 错误
						t3.setText("文法输入有误,请重新输入" + "\n");
						return -1;
					}
				}

				if (t.charAt(j) == b.charAt(0) && j + 1 == t.length()) {
					// 下一个字符为空，把表达式左边非终结符的follow集加入所求follow集
					int p = followComplete(t.charAt(0));
					if (p != -1) {
//						a = followBToFollowA(a,p,t);
						flag = addElementFollow(a, p, flag);
					} else if ((flag = tianjiaFollow(a,
							String.valueOf(t.charAt(0)), followVn, flag)) == -1) {
						return -1;
					}
				}
				
				
			}
		}
		return flag;
	}

	
	//把followB放到followA里面去
	private char[] followBToFollowA(char[] a,int p,String t){

		Integer indexOfVn = map.get(""+t.charAt(0));//得到箭头左边的非终结符是第几个
//		System.out.println(map.size());
		
		
		for(int q = 0;q<follow.length;q++){
			for(int m = 0 ;m<follow.length;m++){
				if(follow[q][m]!='\0'){
					System.out
							.println(follow[q][m]+"   "+q);
				}
			}
		}
		
		char[] temp = new char[follow[indexOfVn].length]; //得到follow(B)
		temp = follow[indexOfVn];
		int tempIndex = 0;
		for(int _i = 0;_i<a.length||tempIndex==temp.length-1;_i++){//将follow(B)放入follow(A)中
			if(a[_i]=='\0'){
				if(a[_i]!='#'){
					a[_i] = temp[tempIndex++];
				}
				
			}
		}
		
		return a;
	}
	
	
	/***
	 * 
	 * @desciption 添加select集 select集 A→a  a不能推出空 就是first(a) 能推出空，就是first(a)交上Follow(A)
	 * @param 
	 * @return
	 */
	private void tianjiaSelect(char a[], String b, int flag) { // 计算SELECT**
		int i = 2;
		int biaozhi = 0;
		while (i < b.length()) {
			if ((b.charAt(i) > 'Z' || b.charAt(i) < 'A') && b.charAt(i) != '#') {// 是终结符
				a[flag] = b.charAt(i);// 将这个字符加入到Select集，结束Select集的计算
				break;
			} else if (b.charAt(i) == '#') {// 是空
				int j;
				for (j = 0; Vn[i] != null; j++) {// 将表达式左侧的非终结符的follow加入select
					if (Vn[j].equals(b.substring(0, 1))) {
						break;
					}
				}

				for (int k = 0; follow[j][k] != '\0'; k++) {
					if (puanduanChar(a, follow[j][k])) {
						a[flag] = follow[j][k];
						flag++;
					}
				}
				break;
			}

			else if (b.charAt(i) >= 'A' && b.charAt(i) <= 'Z'
					&& i + 1 < b.length()) {// 是非终结符且有下一个字符
				int j;
				for (j = 0; Vn[i] != null; j++) {
					if (Vn[j].equals(String.valueOf(b.charAt(i)))) {
						break;
					}
				}

				for (int k = 0; first[j][k] != '\0'; k++) {
					if (puanduanChar(a, first[j][k])) {// 把表达式右侧所有非终结符的first集加入。
						if (first[j][k] == '#') {// first中存在空
							biaozhi = 1;
						} else {
							a[flag] = first[j][k];
							flag++;
						}
					}
				}

				if (biaozhi == 1) {// 把右侧所有非终结符的first中的#去除
					i++;
					biaozhi = 0;
					continue;
				}else {
					biaozhi = 0;
					break;
				}
			}else if (b.charAt(i) >= 'A' && b.charAt(i) <= 'Z'
					&& i + 1 >= b.length()) {// 是非终结符且没有下一个字符
				int j;
				for (j = 0; Vn[i] != null; j++) {
					if (Vn[j].equals(String.valueOf(b.charAt(i)))) {
						break;
					}
				}

				for (int k = 0; first[j][k] != '\0'; k++) {
					if (puanduanChar(a, first[j][k])) {
						if (first[j][k] == '#') {
							biaozhi = 1;// 表达式右侧能推出空，标记
						}

						else {
							a[flag] = first[j][k];// 不能推出空，直接将first集加入select集
							flag++;
						}
					}
				}

				if (biaozhi == 1) {// 表达式右侧能推出空
					for (j = 0; Vn[i] != null; j++) {
						if (Vn[j].equals(b.substring(0, 1))) {
							break;
						}
					}

					for (int k = 0; follow[j][k] != '\0'; k++) {
						if (puanduanChar(a, follow[j][k])) {
							a[flag] = follow[j][k];// 将将表达式左侧的非终结符的follow加入select
							flag++;
						}
					}
					break;
				} else {
					biaozhi = 0;
					break;
				}
			}
		}
	}

	// 返回b在Vt[]的位置
	private int puanduanXulie(char Vt[], char b) {
		int i;
		for (i = 0; Vt[i] != '\0'; i++) {
			if (Vt[i] == b)
				break;
		}
		return i;
	}

	// 判断b是否在a中，在返回false，不在返回true
	private boolean puanduanChar(char a[], char b) {
		for (int i = 0; a[i] != '\0'; i++) {
			if (a[i] == b)
				return false;
		}
		return true;
	}

	// 判断b是否在a中，在返回false，不在返回true
	private boolean puanduanString(String a[], char b) {
		for (int i = 0; a[i] != null; i++) {
			if (a[i].equals(String.valueOf(b)))
				return false;
		}
		return true;
	}

	// 把b加入字符串组firstVn[]
	private void addString(String firstVn[], String b) {
		int i;
		for (i = 0; firstVn[i] != null; i++) {
		}
		firstVn[i] = b;
	}

	// 判断b是否已完成first判断
	private int firstComplete(char b) {
		int i;
		for (i = 0; Vn[i] != null; i++) {
			if (Vn[i].equals(String.valueOf(b))) {
				if (firstComplete[i] == 1)
					return i;
				else
					return -1;
			}
		}
		return -1;
	}

	// 判断b是否已完成follow判断
	private int followComplete(char b) {
		for (int i = 0; Vn[i] != null; i++) {
			if (Vn[i].equals(String.valueOf(b))) {
				if (followComplete[i] == 1)
					return i;
				else
					return -1;
			}
		}
		return -1;

	}

	// 把相应终结符添加到first**中
	private int addElementFirst(char a[], int p, int flag) {
		for (int i = 0; first[p][i] != '\0'; i++) {
			if (puanduanChar(a, first[p][i]) && first[p][i] != '#') {
				a[flag] = first[p][i];
				flag++;
			}
		}
		return flag;
	}

	// 把相应终结符添加到follow**中
	private int addElementFollow(char a[], int p, int flag) {
		for (int i = 0; follow[p][i] != '\0'; i++) {
			if (puanduanChar(a, follow[p][i])) {
				a[flag] = follow[p][i];
				flag++;
			}
		}
		return flag;
	}

	// 判断a能是否包含空
	private boolean panduan_kong(char a) {
		int i;
		for (i = 0; Vn[i] != null; i++) {
			if (Vn[i].equals(String.valueOf(a))) {
				break;
			}
		}
		for (int j = 0; first[i][j] != '\0'; j++) {
			if (first[i][j] == '#')
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		new GramerAnalyzer();
	}
}