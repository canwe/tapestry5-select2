package org.lazan.t5.stitch.components;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.lazan.t5.stitch.model.Syntax;
import org.lazan.t5.stitch.services.SyntaxSource;

@Import(library="prettify.js", stylesheet="prettify.css")
public class Code {
	@Inject
	private JavaScriptSupport jss;
	
	@Inject
	private SyntaxSource syntaxSource;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private Syntax syntax;
	
	@Parameter(required=true, defaultPrefix=BindingConstants.ASSET)
	private Asset source;
	
	@BeginRender
	void beginRender(MarkupWriter writer) throws IOException {
		if (syntax == null) {
			syntax = syntaxSource.getSyntax(source);
		}
		String cssClass = "prettyprint";
		if (syntax.getCssClass() != null) {
			cssClass += " " + syntax.getCssClass();
		}
		writer.element("pre", "class", cssClass);
		writer.write(getSourceAsString());
		writer.end();

		jss.addScript(InitializationPriority.LATE, "prettyPrint()");
	}
	
	String getSourceAsString() throws IOException {
		Reader reader = new InputStreamReader(source.getResource().openStream());
		try {
			StringBuilder builder = new StringBuilder();
			char[] chars = new char[1024];
			int length;
			while ((length = reader.read(chars)) > 0) {
				builder.append(chars, 0, length);
			}
			return builder.toString().replaceAll("\t", "   ");
		} finally {
			reader.close();
		}
	}
}
