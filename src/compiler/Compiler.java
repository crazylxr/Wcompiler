package compiler;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Compiler extends javax.swing.JFrame {

	private javax.swing.JEditorPane jEditorPane1;

	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuItem jMenu9;
	private javax.swing.JMenuItem jMenu10;
	private javax.swing.JMenuItem jMenu11;
	private javax.swing.JMenuItem jMenu12;
	private javax.swing.JMenuItem jMenu13;

	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuItem jMenu14;
	private javax.swing.JMenuItem jMenu15;
	private javax.swing.JMenuItem jMenu16;
	private javax.swing.JMenuItem jMenu17;

	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenuItem jMenu18;

	private javax.swing.JMenu jMenu4;
	private javax.swing.JMenuItem jMenu19;

	private javax.swing.JMenu jMenu5;
	private javax.swing.JMenu jMenu6;

	private javax.swing.JMenu jMenu7;
	private javax.swing.JMenuItem jMenu20;
	private javax.swing.JMenuItem jMenu21;
	private javax.swing.JMenuItem jMenu22;

	private javax.swing.JMenu jMenu8;
	private javax.swing.JMenuItem jMenu23;
	private javax.swing.JMenuItem jMenu24;

	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea2;

	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JButton jButton8;
	private javax.swing.JButton jButton9;

	private FileDialog openDia, saveDia, othersaveDia;
	private File file;

	public Compiler() {
		initComponents();
	}

	private void initComponents() {
		

		jScrollPane1 = new javax.swing.JScrollPane();
		jEditorPane1 = new javax.swing.JEditorPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTextArea2 = new javax.swing.JTextArea();

		jMenuBar1 = new javax.swing.JMenuBar();

		jMenu1 = new javax.swing.JMenu();
		jMenu9 = new javax.swing.JMenuItem();
		jMenu10 = new javax.swing.JMenuItem();
		jMenu11 = new javax.swing.JMenuItem();
		jMenu12 = new javax.swing.JMenuItem();
		jMenu13 = new javax.swing.JMenuItem();

		jMenu2 = new javax.swing.JMenu();
		jMenu14 = new javax.swing.JMenuItem();
		jMenu15 = new javax.swing.JMenuItem();
		jMenu16 = new javax.swing.JMenuItem();
		jMenu17 = new javax.swing.JMenuItem();

		jMenu3 = new javax.swing.JMenu();
		jMenu18 = new javax.swing.JMenuItem();

		jMenu4 = new javax.swing.JMenu();
		jMenu19 = new javax.swing.JMenuItem();

		jMenu5 = new javax.swing.JMenu();
		jMenu6 = new javax.swing.JMenu();

		jMenu7 = new javax.swing.JMenu();
		jMenu20 = new javax.swing.JMenuItem();
		jMenu21 = new javax.swing.JMenuItem();
		jMenu22 = new javax.swing.JMenuItem();

		jMenu8 = new javax.swing.JMenu();
		jMenu23 = new javax.swing.JMenuItem();
		jMenu24 = new javax.swing.JMenuItem();

		jToolBar1 = new javax.swing.JToolBar();
		jButton1 = new javax.swing.JButton(new ImageIcon("image/open.png"));
		jButton2 = new javax.swing.JButton(new ImageIcon("image/save.png"));
		jButton3 = new javax.swing.JButton(new ImageIcon("image/cut.png"));
		jButton4 = new javax.swing.JButton(new ImageIcon("image/copy.png"));
		jButton5 = new javax.swing.JButton(new ImageIcon("image/paste.png"));
		jButton6 = new javax.swing.JButton(new ImageIcon(
				"image/grammarOperator.png"));
		jButton7 = new javax.swing.JButton(new ImageIcon(
				"image/codeOperator.png"));
		jButton8 = new javax.swing.JButton(new ImageIcon(
				"image/showOperator.png"));
		jButton9 = new javax.swing.JButton(new ImageIcon("image/problem.png"));

		openDia = new FileDialog(this, "打开", FileDialog.LOAD);
		saveDia = new FileDialog(this, "保存", FileDialog.SAVE);
		othersaveDia = new FileDialog(this, "另存为", FileDialog.SAVE);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jScrollPane1.setViewportView(jEditorPane1);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane2.setViewportView(jTextArea1);

		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jScrollPane3.setViewportView(jTextArea2);

		jToolBar1.setRollover(true);

		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton1);
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton2);
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton3);
		jButton4.setFocusable(false);
		jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton4);
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton5);
		jButton6.setFocusable(false);
		jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton6);
		jButton7.setFocusable(false);
		jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton7);
		jButton8.setFocusable(false);
		jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton8);
		jButton9.setFocusable(false);
		jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButton9);

		jMenu1.setText("文件(F)");

		jMenu9.setText("打开(O)");
		jMenu1.add(jMenu9);

		jMenu10.setText("保存(S)");
		jMenu1.add(jMenu10);

		jMenu11.setText("另存为(A)");
		jMenu1.add(jMenu11);

		jMenu12.setText("最近文件");
		jMenu1.add(jMenu12);

		jMenu13.setText("退出(X)");
		jMenu1.add(jMenu13);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("编辑(E)");

		jMenu14.setText("撤销(U)");
		jMenu2.add(jMenu14);

		jMenu15.setText("剪切(T)");
		jMenu2.add(jMenu15);

		jMenu16.setText("复制(C)");
		jMenu2.add(jMenu16);

		jMenu17.setText("粘贴(P)");
		jMenu2.add(jMenu17);

		jMenuBar1.add(jMenu2);

		jMenu3.setText("词法分析(W)");

		jMenu18.setText("词法分析器(A)");
		jMenu3.add(jMenu18);

		jMenuBar1.add(jMenu3);

		jMenu4.setText("语法分析(P)");

		jMenu19.setText("语法分析器(S)");
		jMenu4.add(jMenu19);

		jMenuBar1.add(jMenu4);

		jMenu5.setText("中间代码(M)");
		jMenuBar1.add(jMenu5);

		jMenu6.setText("目标代码生成(O)");
		jMenuBar1.add(jMenu6);

		jMenu7.setText("查看(V)");

		jMenu20.setText("工具栏(T)");
		jMenu7.add(jMenu20);

		jMenu21.setText("状态栏(S)");
		jMenu7.add(jMenu21);

		jMenu22.setText("显示符号表信息()");
		jMenu7.add(jMenu22);

		jMenuBar1.add(jMenu7);

		jMenu8.setText("帮助(H)");

		jMenu23.setText("帮助");
		jMenu8.add(jMenu23);

		jMenu24.setText("关于(A)...");
		jMenu8.add(jMenu24);

		jMenuBar1.add(jMenu8);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										674,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jScrollPane3,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														618, Short.MAX_VALUE)
												.addComponent(jScrollPane2))
								.addContainerGap())
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										25, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jScrollPane2,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		313,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jScrollPane3))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		jScrollPane1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		621,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))));

		pack();

		myEvent();
	}

	private void myEvent() {
		// 打开文件
		jMenu9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();//
				String fileName = openDia.getFile();//

				if (dirPath == null || fileName == null)
					return;

				String text = "";

				file = new File(dirPath, fileName);

				try {
					BufferedReader bufr = new BufferedReader(new FileReader(
							file));

					String line = null;

					while ((line = bufr.readLine()) != null) {
						text += (line + "\r\n");
					}
					jEditorPane1.setText(text);
					bufr.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();//
				String fileName = openDia.getFile();//

				if (dirPath == null || fileName == null)
					return;

				String text = "";

				file = new File(dirPath, fileName);

				try {
					BufferedReader bufr = new BufferedReader(new FileReader(
							file));

					String line = null;

					while ((line = bufr.readLine()) != null) {
						text += (line + "\r\n");
					}
					jEditorPane1.setText(text);
					bufr.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}

			}
		});
		
		// 保存文件
		jMenu10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file == null) {
					saveDia.setVisible(true);
					String dirPath = saveDia.getDirectory();
					String fileName = saveDia.getFile();

					if (dirPath == null || fileName == null)
						return;
					file = new File(dirPath, fileName);
				}

				try {
					BufferedWriter bufw = new BufferedWriter(new FileWriter(
							file));

					String text = jEditorPane1.getText();

					bufw.write(text);

					bufw.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});

		// 另存为文件
		jMenu11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDia.setVisible(true);
				String dirPath = othersaveDia.getDirectory();
				String fileName = othersaveDia.getFile();
				if (dirPath == null || fileName == null)
					return;
				file = new File(dirPath, fileName);
				try {
					BufferedWriter bufw = new BufferedWriter(new FileWriter(
							file));

					String text = jEditorPane1.getText();

					bufw.write(text);

					bufw.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});
		// 关闭窗口
		jMenu13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Compiler().setVisible(true);
			}
		});
	}
}
