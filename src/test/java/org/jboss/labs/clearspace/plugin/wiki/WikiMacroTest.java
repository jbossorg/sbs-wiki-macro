package org.jboss.labs.clearspace.plugin.wiki;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for WikiMacro
 */
public class WikiMacroTest {
	@Test
	public void testRenderWikiSyntax() throws Exception {

		WikiMacro macro = new WikiMacro();
		Assert.assertEquals("<body><h1><span>head</span></h1></body>",
				macro.renderWikiSyntax("h1. head"));
		Assert.assertEquals("<body><ul><li level=\"1\" type=\"ul\"><p>one</p></li><li level=\"1\" type=\"ul\"><p>two</p></li><li level=\"1\" type=\"ul\"><p>three</p></li></ul></body>",
				macro.renderWikiSyntax("* one\n* two\n* three"));
		//Assert.assertEquals("<body></body>", macro.renderWikiSyntax("<p></p>"));
	}
}
