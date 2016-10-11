package pojo;

public class Bracket {
	/**
	 * @ClassName
	 * @author lxr
	 * @date 2016年10月10日
	 * @版本 V 1.0
	 * @description 括号类
	 * 
	 */
	private String name;
	private int numberLine;

	public Bracket(String name, int numberLine) {
		super();
		this.name = name;
		this.numberLine = numberLine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberLine() {
		return numberLine;
	}

	public void setNumberLine(int numberLine) {
		this.numberLine = numberLine;
	}

	@Override
	public String toString() {
		return "在第" + numberLine + "行有不匹配的括号";
	}

}
