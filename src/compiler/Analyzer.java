package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import pojo.Bracket;
import pojo.CharTable;

/**
 * java 词法分析器 读文件，结果输出未保存
 * 
 * @author ltt
 * @version 1.0
 */
public class Analyzer {
	private String keyWords[] = { "abstract", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do",
			"double", "else", "extends", "final", "finally", "float", "for",
			"if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "package", "private", "protected",
			"public", "return", "short", "static", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "try",
			"void", "volatile", "while", "strictfp", "enum", "goto", "const",
			"assert", "import", "package" }; // 关键字数组
	private char operators[] = { '+', '-', '*', '/', '=', '>', '<', '&', '%',
			'?', '|', '!', '&' }; // 运算符数组
	private char separators[] = { ',', ';', '{', '}', '(', ')', '[', ']', '_',
			':', '、', '.', '"', '@', '\'', '\"' }; // 分隔符数组
	// TODO
	private StringBuffer buffer = new StringBuffer(); // 缓冲区
	private char ch; // 字符变量，存放最新读进的源程序字符
	private static int i = 0;
	private static int ketType;
	private String strToken; // 字符数组，存放构成单词符号的字符串

	private int numberLine = 1;
	private StringBuffer tokenBuffer = new StringBuffer(); // token串
	private StringBuffer erroBuffer = new StringBuffer(); // error串

	private List<CharTable> charTable = new ArrayList<>();// 符号表
	private int numberOfCharTable = 0;// 符号表个数

	private Stack<Bracket> leftBrackets = new Stack<>();

	public Analyzer() {
	}

	public List<CharTable> getCharTable() {
		return charTable;
	}

	public void setCharTable(ArrayList<CharTable> arrayList) {
		this.charTable = arrayList;
	}

	public StringBuffer getTokenBuffer() {
		return tokenBuffer;
	}

	public void setTokenBuffer(StringBuffer tokenBuffer) {
		this.tokenBuffer = tokenBuffer;
	}

	public StringBuffer getErroBuffer() {
		return erroBuffer;
	}

	public void setErroBuffer(StringBuffer erroBuffer) {
		this.erroBuffer = erroBuffer;
	}

	// 将读出来的字符串添加到缓冲区
	public void appendBuffer(String string) {
		buffer.append(string);
	}

	/**
	 * 
	 * @desciption 添加到错误信息里
	 * @param 一条错误信息
	 * @return
	 */
	public void appendErrorBuffer(String erroStirng) {
		erroBuffer.append(erroStirng);
	}

	/**
	 * 将下一个输入字符读到ch中，搜索指示器前移一个字符
	 */
	public void getChar() {
		ch = buffer.charAt(i);
		i++;
	}

	/**
	 * 检查ch中的字符是否为空白，若是则调用getChar() 直至ch中进入一个非空白字符
	 */
	public void getBc() {
		while (Character.isSpaceChar(ch))
			getChar();
	}

	/**
	 * 将ch连接到strToken之后
	 */
	public void concat() {
		strToken += ch;
	}

	/**
	 * 判断字符是否为字母
	 */
	boolean isLetter() {
		return Character.isLetter(ch);
	}

	/**
	 * 判断字符是否为数字
	 */
	boolean isDigit() {
		return Character.isDigit(ch);
	}

	/**
	 * 将搜索指示器回调一个字符位置，将ch值为空白字
	 */
	public void retract() {
		i--;
		ch = ' ';
	}

	/**
	 * 判断单词是否为关键字 [1,50]
	 */
	public int isKeyWord() {
		ketType = -1;
		for (int i = 0; i < keyWords.length; i++) {
			if (keyWords[i].equals(strToken))
				ketType = i + 1;
		}
		return ketType;
	}

	/**
	 * 判断是否为运算符 [51,100)
	 */
	public int isOperator() {
		ketType = -1;
		for (int i = 0; i < operators.length; i++) {
			if (ch == operators[i])
				ketType = i + 51;
		}
		return ketType;
	}

	/**
	 * 判断是否为分隔符 [101,150)
	 */
	public int isSeparators() {
		ketType = -1;
		for (int i = 0; i < separators.length; i++) {
			if (ch == separators[i])
				ketType = i + 101;
		}
		return ketType;
	}

	/**
	 * 将strToken插入到关键字表
	 */
	public void insertKeyWords(String strToken) {
		// System.out.print("关键字，种别[1,50]");
		// System.out.println("=====(" + ketType + "," + strToken + ")");
		tokenBuffer.append("( " + numberLine + " , " + ketType + " , "
				+ strToken + " )" + "\r\n");
	}

	/**
	 * 将strToken插入到符号表
	 */
	public void insertId(String strToken) {
		// System.out.print("标识符，种别200");
		if (isIdentifier(strToken)) {
			if (isexist_sym(strToken) == 0) {
				charTable.add(new CharTable(++numberOfCharTable, strToken,
						strToken.length(), "整型", "简单变量", strToken, null));// 插入到符号表
			}

			tokenBuffer.append("( " + numberLine + " , " + 200 + " , "
					+ strToken + " )" + "\r\n");
		} else {
			appendErrorBuffer("error:" + numberLine + "  标识符：" + strToken
					+ "错误定义");
		}
	}

	/**
	 * 将strToken中的常数插入到常数表中
	 */
	public void insertConst(String strToken) {
		// System.out.print("常数，种别0");
		// System.out.println("=====(" + 0 + "," + strToken + ")");
		if (isexist_sym(strToken) == 0) {
			charTable.add(new CharTable(++numberOfCharTable, strToken, strToken
					.length(), "整型", "常数", strToken, null));// 插入到符号表
		}

		tokenBuffer.append("( " + numberLine + " , " + 0 + " , " + strToken
				+ " )" + "\r\n");
	}

	/**
	 * 将ch插入到运算符表中
	 */
	public void insertOperators(char ch2) {
		// System.out.print("运算符，种别 [51,100)");
		// System.out.println("=====(" + ketType + "," + ch + ")");

		// TODO
		if (ch2 == '/') {
			getChar();
			if (ch == '*') {// 多行注释
				while (true) {
					if (ch == '*') {
						getChar();
						if (ch == '/') {
							break;
						} else {
							retract();
						}
					} else {
						getChar();
						getBc();
					}
				}
			} else if (ch == '/') {// 单行注释
				while (ch != '\r') {
					getChar();
					getBc();
				}
				retract();
			} else {
				retract();
				tokenBuffer.append("( " + numberLine + " , " + ketType + " , "
						+ ch + " )" + "\r\n");
			}
		}

	}

	/**
	 * 将ch插入到分隔符表
	 */
	public void insertSeparators() {
		// System.out.print("分隔符，种别 [101,150)");
		// System.out.println("=====(" + ketType + "," + ch + ")");

		if (ch == '{' || ch == '(') { // 如果分隔符为左括号，压入左括号栈
			leftBrackets.push(new Bracket(ch + "", numberLine));
		}

		if (ch == '}') {
			if (leftBrackets.isEmpty()) {
				appendErrorBuffer("在" + numberLine + "行有不匹配的括号'}'");
			} else if (leftBrackets.peek().getName().equals("{")) {
				leftBrackets.pop();
			}
		}

		if (ch == ')') {
			if (leftBrackets.isEmpty()) {
				appendErrorBuffer("在" + numberLine + "行有不匹配的括号')'");
			} else if (leftBrackets.peek().getName().equals("(")) {
				leftBrackets.pop();
			}

		}

		tokenBuffer.append("( " + numberLine + " , " + ketType + " , " + ch
				+ " )" + "\r\n");
	}

	/**
	 * @desciption 查询符号表里是否有
	 * @param name
	 *            名字
	 * @return 如果存在，则返回入口，不存在则返回0
	 */
	public int isexist_sym(String name) {
		for (CharTable line : charTable) {
			if (line.getName().equals(name)) {
				return line.getEnroll();
			}
		}
		return 0;
	}

	// 是否是标识符
	public boolean isIdentifier(String string) {
		boolean result = false;
		String regex = "^[A-Za-z_$]+[A-Za-z_$\\d]+$";
		if (string.matches(regex)) {
			result = true;
		}

		return result;
	}

	/**
	 * 
	 * @desciption 识别数的函数
	 * @param
	 * @return
	 */
	public void recog_dig(char ch){
		char state = '0';
		while(state!='7'){
			switch(state){
			case '0'://读入一个数字
				if(Character.isDigit(ch)){
					state = '1';
				}else{
					appendErrorBuffer(numberLine+" : "+ch +"   不是一个数字！");
				}
				break;
			case '1':
				if(Character.isDigit(ch)){//仍然读入数字
					state = '1';
				}else if(ch == '.'){//读入小数点，识别实数
					state = '2';
				}else if(ch == 'e'||ch == 'E'){//读入e获E ，带指数
					state = '4';
				}else{
					retract();
					//返回整数类型
				}
				break;
			case '2':
				if(Character.isDigit(ch)){//读入数字
					state = '3';
				}else{
					appendErrorBuffer(numberLine+" : "+"数字错误");
				}
				break;
			case '3':
				if(Character.isDigit(ch)){//读入数字
					state = '3';
				}else if((ch == 'E')||(ch == 'e')){//读入e或E
					state = '4';
				}else{//已识别完带小数的实数，返回
					retract();
					//返回实数类型
				}
				break;
			case '4':
				if(Character.isDigit(ch)){//读入数字
					state = '6';
				}else if(ch == '+'||ch == '-'){//读入+，-符号
					state = '5';
				}
				break;
			}
		}
	}

	/**
	 * 词法分析
	 */
	public void analyse() {
		// boolean isCode, value;
		strToken = ""; // 置strToken为空串

		while (i < buffer.length()) {
			if (ch == '\n') {
				numberLine++;
			}

			getChar();
			getBc();

			if (isLetter()) { // 如果ch为字母
				while (isLetter() || isDigit() || ch == '_') {
					concat();
					getChar();
				}
				retract(); // 回调
				if (isKeyWord() > 0) { // 如果是为关键字，则插入到1.保留字表中
					insertKeyWords(strToken);
				} else { // 否则插入到2.符号表中
					insertId(strToken);
				}
				strToken = "";
			} else if (isDigit()) { // 如果ch为数字
				while (isDigit()) {
					concat();
					getChar();
				}
				retract(); // 回调
				insertConst(strToken); // 是常数，插入到3.常数表中
				strToken = "";
			} else if (isOperator() > 0) { // 如果是运算符，则插入到4.运算符表
				insertOperators(ch);
			} else if (isSeparators() > 0) { // 如果是分隔符，插入到5.分隔符表中
				insertSeparators();
			} else {
				// TODO
				if (ch != ' ' && ch != '\r' && ch != '\n' && ch != '\t') {
					appendErrorBuffer(numberLine + ": erro:" + "  未能识别的单词:"
							+ ch + "\r\n");
				}

			}
		}

		if (!leftBrackets.isEmpty()) {
			while (!leftBrackets.isEmpty()) {
				appendErrorBuffer("括号不满足:" + leftBrackets.pop().toString()
						+ "\r\n");
			}

		}
	}

	public static void main(String[] args) {
		System.err.println("常数，种别0");
		System.err.println("关键字，种别[1,50]");
		System.err.println("运算符，种别 [51,100)");
		System.err.println("分隔符，种别 [101,150)");
		System.err.println("标识符，种别200");
	}
}