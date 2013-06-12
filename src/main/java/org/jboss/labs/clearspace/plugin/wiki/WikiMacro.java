/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.labs.clearspace.plugin.wiki;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jivesoftware.base.wiki.HtmlBuilder;
import com.jivesoftware.base.wiki.WikiLexer;
import com.jivesoftware.community.renderer.BaseMacro;
import com.jivesoftware.community.renderer.RenderContext;
import com.jivesoftware.community.renderer.RenderType;
import com.jivesoftware.community.renderer.annotations.MacroTagName;
import com.jivesoftware.community.renderer.annotations.RenderTypes;
import com.jivesoftware.community.renderer.annotations.SingleTagMacro;
import com.jivesoftware.community.renderer.impl.v2.HtmlRenderUtils;
import com.jivesoftware.community.renderer.impl.v2.JAXPUtils;
import com.jivesoftware.util.StringUtils;

/**
 * Macro for Wiki markup
 *
 * @author Libor Krzyzanek
 */
@RenderTypes(value = {RenderType.BODY, RenderType.DOCUMENT_BODY,
		RenderType.COMMENT_BODY})
@MacroTagName(value = "wiki")
@SingleTagMacro(value = false)
public class WikiMacro extends BaseMacro {

	protected static final Logger log = LogManager.getLogger(WikiMacro.class);

	private boolean showInRTE = true;

	private boolean button = false;

	private String icon;

	public void execute(Element element, RenderContext renderContext) {
		String text = convertToOriginalWikiText(element);
		if (log.isTraceEnabled()) {
			log.trace("Original wiki text to markup: " + text);
		}
		WikiLexer lexer = new WikiLexer(text);
		org.jdom.Element htmlNodes = lexer.parse();

		HtmlBuilder builder = new HtmlBuilder(htmlNodes);
		builder.build();
		String xhtmlBody = elementToString(htmlNodes);
		xhtmlBody = StringUtils.escapeEntitiesInXmlString(xhtmlBody);

		Node parsedParent = JAXPUtils.toXmlNode(xhtmlBody);

		if (parsedParent != null) {
			NodeList parsedNodes = parsedParent.getChildNodes();

			JAXPUtils.replace(element, parsedNodes);
		} else {
			Element span = JAXPUtils.createElement("span");
			span.setTextContent(text);
			JAXPUtils.replace(element, span);
		}
	}

	/**
	 * Taken from HtmlRenderUtils.toString
	 *
	 * @param element
	 * @return
	 * @see HtmlRenderUtils#toString()
	 */
	public String elementToString(org.jdom.Element element) {
		Format format = Format.getRawFormat();
		// Here is fix for ORG-500
		format.setExpandEmptyElements(false);
		format.setLineSeparator("\n");
		XMLOutputter outputter = new XMLOutputter(format);
		StringWriter writer = new StringWriter(512);
		try {
			outputter.output(element, writer);
		} catch (IOException e) {
			log.error("Cannot format wiki markuped text", e);
		}
		return writer.getBuffer().toString();
	}

	@Override
	protected String convertToOriginalWikiText(Element element) {
		StringBuilder value = new StringBuilder();
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			String nodeContent = node.getTextContent();
			if (log.isTraceEnabled()) {
				log.trace("Processing node: " + node.getNodeName() + ", content: " + nodeContent);
			}
			if ("p".equalsIgnoreCase(node.getNodeName())) {
				value.append(nodeContent);
				if (!nodeContent.endsWith("\n")) {
					value.append("\n");
				}
			} else if ("br".equalsIgnoreCase(node.getNodeName())) {
				value.append("\n");
			} else {
				value.append(nodeContent);
			}
		}
		return value.toString();
	}

	@Override
	public int getOrder() {
		// very important - we need to ensure to run this macro before other macros
		// like code, document etc.
		return -2000;
	}

	public boolean isShowSettings() {
		return false;
	}

	public void setShowInRTE(boolean showInRTE) {
		this.showInRTE = showInRTE;
	}

	public boolean isShowInRTE() {
		return showInRTE;
	}

	public void setButton(boolean button) {
		this.button = button;
	}

	public boolean isButton() {
		return button;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

}
