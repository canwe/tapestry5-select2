package org.lazan.t5.stitch.demo.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class OnEventDemo {
	@Property
	private String message;
	
	@Inject
	private Block messageBlock;
	
	@Property
	private String text;
	
	@Property
	private String radio;
	
	@Property
	private String groupFieldName;

	@Property
	private String groupField;
	
	void beginRender() {
		text = "Sample Text";
		radio = "option1";
	}
	
	Block onKeyUpFromText(String value) {
		message = String.format("Text changed to '%s'", value);
		return messageBlock;
	}

	Block onRadioChange(String value) {
		message = String.format("Radio changed to '%s'", value);
		return messageBlock;
	}
	
	Block onGroupChange(String triggerField, String value1, String value2, String value3, String value4) {
		message = String.format(
			"%s changed {groupField1=%s, groupField2=%s, groupField3=%s, groupField4=%s}",
			triggerField, value1, value2, value3, value4
		);
		return messageBlock;
	}
	
	public String[] getGroupFields() {
		return new String[] {
			"groupField1", "groupField2", "groupField3", "groupField4"
		};
	}
}
