/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.labs.clearspace.plugin.wiki;

import com.jivesoftware.base.plugin.Plugin;
import com.jivesoftware.community.renderer.RenderManager;

/**
 * @author Libor Krzyzanek
 */
public class WikiPlugin implements Plugin<WikiPlugin> {

	private RenderManager renderManager;

	private WikiMacro wikiMacro;

	public void init() {
		renderManager.addRenderPlugin(wikiMacro);
	}

	public void destroy() {
	}

	public RenderManager getRenderManager() {
		return renderManager;
	}

	public void setRenderManager(RenderManager renderManager) {
		this.renderManager = renderManager;
	}

	public WikiMacro getWikiMacro() {
		return wikiMacro;
	}

	public void setWikiMacro(WikiMacro wikiMacro) {
		this.wikiMacro = wikiMacro;
	}

}
