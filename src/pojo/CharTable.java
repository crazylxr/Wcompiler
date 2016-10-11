package pojo;

public class CharTable {
	/**
	 * @ClassName
	 * @author lxr
	 * @date 2016年10月10日
	 * @版本 V 1.0
	 * @description
	 * 
	 */
	private int enroll;// 入口 eg 1
	private String name; // 单词名字 eg area
	private int length; // 长度 eg 单词长度
	private String type; // 类型 eg 整形
	private String spec; // 种属 eg 简单变量
	private String value; // 值 eg 5
	private String address;// 内存地址

	public CharTable(int enroll, String name, int length, String type,
			String spec, String value, String address) {
		super();
		this.enroll = enroll;
		this.name = name;
		this.length = length;
		this.type = type;
		this.spec = spec;
		this.value = value;
		this.address = address == null || address.isEmpty() ? "未知" : address;
	}

	public int getEnroll() {
		return enroll;
	}

	public void setEnroll(int enroll) {
		this.enroll = enroll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getValue() {
		return value.isEmpty() ? "未知" : address;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAddress() {
		return address.isEmpty() || address == null ? "未知" : address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "[enroll：" + enroll + ", name：" + name + ", length：" + length
				+ ", type：" + type + ", spec：" + spec + ", value：" + value
				+ ", address：" + address + "]";
	}

}
