package compiler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import pojo.CharTable;
import util.LineNumberHeaderView;

public class CompilerUI extends JFrame {
	private static final long serialVersionUID = -7711761347119181685L;

	/*
	 * 初始化组件
	 */

	JMenuBar menuBar = new JMenuBar();

	JMenu folderMenu = new JMenu("文件(F)");
	JMenu editMenu = new JMenu("编辑(E)");
	JMenu lexicalMenu = new JMenu("词法分析(W)");
	JMenu syntacticMenu = new JMenu("语法分析(P)");
	JMenu interMidMenu = new JMenu("中间代码生成(M)");
	JMenu targetCodeMenu = new JMenu("目标代码生成(O)");
	JMenu viewMenu = new JMenu("查看(V)");
	JMenu helpMenu = new JMenu("帮助(H)");

	JMenuItem newFileItem = new JMenuItem("新建");
	JMenuItem openItem = new JMenuItem("打开");
	JMenuItem saveItem = new JMenuItem("保存");
	JMenuItem saveAsItem = new JMenuItem("另存为");
	JMenuItem exitItem = new JMenuItem("退出");

	JMenuItem resetItem = new JMenuItem("撤销");
	JMenuItem cutItem = new JMenuItem("剪切");
	JMenuItem copyItem = new JMenuItem("复制");
	JMenuItem pasteItem = new JMenuItem("粘贴");

	JMenuItem lexicalItem = new JMenuItem("词法分析器");
	JMenuItem syntacticItem = new JMenuItem("语法分析器");

	/*
	 * 工具栏
	 */
	JToolBar toolBar = new JToolBar();
	Dimension d = new Dimension(25, 25);
	JButton openFileBtn = new ImgButton("./img/open_file_16px.png", d);
	JButton saveBtn = new ImgButton("./img/save_file_16px.png", d);
	JButton cutBtn = new ImgButton("./img/cut_16px.png", d);
	JButton copyBtn = new ImgButton("./img/copy_16px.png", d);
	JButton pasteBtn = new ImgButton("./img/paste_16px.png", d);
	JButton lexicalBtn = new ImgButton("./img/word_16px.png", d);
	JButton signFormBtn = new ImgButton("./img/signForm_16px.png", d);
	JButton phraseBtn = new ImgButton("./img/phrase_16px.png", d);
	JButton aboutBtn = new ImgButton("./img/about_16px.png", d);

	/*
	 * 主面板
	 */
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel innerPanel = new JPanel(new GridLayout(1, 2));
	JPanel leftPanel = new JPanel(new BorderLayout());
	JPanel rightPanel = new JPanel(new GridLayout(2, 1));
	JPanel rightTopPanel = new JPanel(new BorderLayout());
	JPanel rightBottomPanel = new JPanel(new BorderLayout());

	// 选项卡
	Container c;
	JTabbedPane tabbedPane;
	JLabel label_taken, label_char;
	JPanel panel_taken, panel_char;

	JTextArea charTableTextArea = new JTextArea();
	JScrollPane charTableScrollPane = new JScrollPane(charTableTextArea);
	JTextArea editTextArea = new JTextArea();
	JScrollPane editScrollPane = new JScrollPane(editTextArea);
	JTextArea tokenTextArea = new JTextArea();
	JScrollPane tokenScrollPane = new JScrollPane(tokenTextArea);
	JTextArea wmsgTextArea = new JTextArea();
	JScrollPane wmsgScrollPane = new JScrollPane(wmsgTextArea);
	LineNumberHeaderView lnhv = new LineNumberHeaderView();// 行号

	FileDialog openDia = new FileDialog(this, "打开", FileDialog.LOAD);
	FileDialog saveDia = new FileDialog(this, "保存", FileDialog.SAVE);
	FileDialog othersaveDia = new FileDialog(this, "另存为", FileDialog.SAVE);

	private File file;

	Analyzer analyzer = new Analyzer();

	public CompilerUI() {
		// 选项卡
		c = this.getContentPane();
		tabbedPane = new JTabbedPane();// 创建选项卡面板对象

		// 创建选项卡标签
		label_taken = new JLabel("token表", SwingConstants.CENTER);
		label_char = new JLabel("符号表", SwingConstants.CENTER);

		// 创建选项卡面板
		panel_taken = new JPanel(new BorderLayout());
		panel_char = new JPanel(new BorderLayout());

		// panel_taken.add(label_taken);
		panel_taken.add(tokenScrollPane, BorderLayout.CENTER);
		panel_char.add(charTableScrollPane, BorderLayout.CENTER);
		// panel_char.add(label_char);

		tabbedPane.addTab("token表", panel_taken);
		tabbedPane.addTab("符号表", panel_char);

		c.add(tabbedPane);
		c.setBackground(Color.white);

		// 构造顶部菜单
		menuBar.add(folderMenu);
		menuBar.add(editMenu);
		menuBar.add(lexicalMenu);
		menuBar.add(syntacticMenu);
		menuBar.add(interMidMenu);
		menuBar.add(targetCodeMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		folderMenu.add(newFileItem);
		folderMenu.add(openItem);
		folderMenu.add(saveItem);
		folderMenu.add(saveAsItem);
		folderMenu.add(exitItem);

		editMenu.add(resetItem);
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);

		lexicalMenu.add(lexicalItem);
		syntacticMenu.add(syntacticItem);

		this.setJMenuBar(menuBar);

		// 构造工具栏
		toolBar.add(openFileBtn);
		toolBar.add(saveBtn);
		toolBar.add(cutBtn);
		toolBar.add(copyBtn);
		toolBar.add(pasteBtn);
		toolBar.add(lexicalBtn);
		toolBar.add(signFormBtn);
		toolBar.add(phraseBtn);
		toolBar.add(aboutBtn);

		setContentPane(mainPanel);
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(innerPanel, BorderLayout.CENTER);
		innerPanel.add(leftPanel);
		innerPanel.add(rightPanel);
		leftPanel.add(editScrollPane, BorderLayout.CENTER);
		editScrollPane.setRowHeaderView(lnhv);

		rightPanel.add(c);
		rightPanel.add(rightBottomPanel);
		// rightTopPanel.add(tokenScrollPane, BorderLayout.CENTER);
		rightBottomPanel.add(wmsgScrollPane, BorderLayout.CENTER);

		myEvent();

		// 初始化编译器大小位置
		this.setSize(1300, 900);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("simple编译器");
	}

	// 产生token表
	public void productToken() {
		StringBuffer bu = analyzer.getTokenBuffer();
		StringBuffer erroLog = analyzer.getErroBuffer();

		if (erroLog.length() == 0) {
			wmsgTextArea.setText("暂无词法错误！");
		} else {
			wmsgTextArea.setText(erroLog.toString());
		}

		tokenTextArea.setText(bu.toString());
	}

	// 产生符号表
	public void productCharTable() {
		List<CharTable> charTable = analyzer.getCharTable();
		StringBuffer sb = new StringBuffer();
		for (CharTable line : charTable) {
			sb.append(line.toString() + "\r\n\r\n");
		}

		charTableTextArea.setText(sb.toString());
	}

	private void myEvent() {
		// 点击语法分析
		syntacticItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new GramerAnalyzer();
				System.out.println(666);
			}
		});
		
		// 点击词法分析 快捷键
		lexicalBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				productToken();
				productCharTable();
			}
		});
		// 点击词法分析
		lexicalItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				productToken();
				productCharTable();
			}
		});

		saveItem.addActionListener(new ActionListener() {
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

					String text = editTextArea.getText().toString();

					bufw.write(text);

					bufw.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});

		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();//
				String fileName = openDia.getFile();//

				if (dirPath == null || fileName == null)
					return;

				editTextArea.setText("");//

				file = new File(dirPath, fileName);
					
				//当换文件的时候清空以前的token表，错误日志，符号表
				analyzer.setCharTable(new ArrayList<CharTable>());
				analyzer.setErroBuffer(new StringBuffer());
				analyzer.setTokenBuffer(new StringBuffer());
				
				try {
					BufferedReader bufr = new BufferedReader(new FileReader(
							file));

					String line = null;

					while ((line = bufr.readLine()) != null) {
						editTextArea.append(line + "\r\n");
						analyzer.appendBuffer(line + "\r\n");
					}

					analyzer.analyse();

					bufr.close();
					
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});
		
		saveAsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file == null)//
				{
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

					String text = editTextArea.getText();

					bufw.write(text);

					bufw.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file == null)//
				{
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

					String text = editTextArea.getText();

					bufw.write(text);

					bufw.close();
				} catch (IOException ex) {
					throw new RuntimeException("11");
				}
			}
		});


		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		CompilerUI ui = new CompilerUI();
	}
}
