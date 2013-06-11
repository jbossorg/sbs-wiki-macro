/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.labs.clearspace.plugin.wiki;

import com.jivesoftware.community.mail.incoming.impl.EmailActionBuilder;
import com.jivesoftware.community.mail.spi.DefaultIntegrationProvider;
import ys.wikiparser.WikiParser;

/**
 * Integration provider for wiki content.<br>
 * It overrides getActionBuilder method which return wikiBuilder. wikiBuilder
 * just change setMessageBody method where run wiki markup to xHTML conversion
 *
 * @author Libor Krzyzanek
 */
public class WikiIntegrationProvider extends DefaultIntegrationProvider {

	private WikiEmailActionBuilder wikiBuilder = new WikiEmailActionBuilder();

	@Override
	protected EmailActionBuilder getActionBuilder() {
		wikiBuilder.clear();
		return wikiBuilder;
	}

	class WikiEmailActionBuilder extends EmailActionBuilder {
		@Override
		public void setMessageBody(String body) {
			StringBuilder sb = new StringBuilder();
			sb.append("<body>");
			String xhtmlBody = WikiParser.renderXHTML(body);
			sb.append(xhtmlBody);
			sb.append("</body>");
			super.setMessageBody(sb.toString());
		}
	}
}
