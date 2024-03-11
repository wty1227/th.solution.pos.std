package cn.th.phonerf.model;

public class BaseEntity {
	String valueMember;
	String displayMember;
	String field_key1;

	private String key;
	private String value;
	private boolean isEvent = false;

	public BaseEntity() {
		
	}
	public BaseEntity(String value, String name ) {
		this.valueMember = value;
		this.displayMember = "[" + value + "]" + name;
	}
	
	public String getField_key1() {
		return field_key1;
	}
	public void setField_key1(String field_key1) {
		this.field_key1 = field_key1;
	}
	public String getValueMember() {
		return valueMember;
	}
	public void setValueMember(String valueMember) {
		this.valueMember = valueMember;
	}
	public String getDisplayMember() {
		return displayMember;
	}
	public void setDisplayMember(String displayMember) {
		this.displayMember = displayMember;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEvent() {
		return isEvent;
	}

	public void setEvent(boolean event) {
		isEvent = event;
	}
}
