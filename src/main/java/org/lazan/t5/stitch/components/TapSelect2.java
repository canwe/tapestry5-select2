package org.lazan.t5.stitch.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.lazan.t5.stitch.select2.Settings;

/**
 * @author Victor Kanopelko
 */
@Import(library = {"context:jquery/jquery-1.10.2.min.js","context:bootstrap/js/bootstrap.js","context:select2/select2.js"},
        stylesheet = {"context:select2/select2.css","context:select2/select2-bootstrap.css"})
public class TapSelect2 extends TextField {

	@Parameter(required = true)
	private Settings settings;

	@Parameter(allowNull = false, value = "text", defaultPrefix = BindingConstants.LITERAL)
	private String owntype;

	@Parameter(required = true, value = "text", defaultPrefix = BindingConstants.LITERAL)
	private String name;

	@Persist("flash")
	@Property
	private String value;

	@Inject
	private ComponentDefaultProvider defaultProvider;

	@Inject
	private ComponentResources resources;

	@Environmental
	private JavaScriptSupport javaScriptSupport;

	public String getSelectedValue() {
		return value;
	}

	public void setSelectedValue(String value) {
		this.value = value;
	}

	Binding defaultValidate() {
		return defaultProvider.defaultValidatorBinding("value", resources);
	}

	@InjectContainer
	private ClientElement container;

	@BeginRender
	void begin(final MarkupWriter writer) {
		resources.renderInformalParameters(writer);
	}

	@AfterRender
	void afterRender() {
		javaScriptSupport.addScript("jQuery('#%s').select2(%s);", container.getClientId(), settings.toJson());
	}

	@Override
	protected void writeFieldTag(MarkupWriter writer, String value) {
		writer.element("input",

				"type", owntype,

				"name", container.getClientId(),

				"id", container.getClientId(),

				"value", value,

				"size", getWidth());
	}
}
