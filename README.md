SBS plugin: Wiki Markup Macro
=============================

Jive SBS plugin providing wiki syntax macro

Configuration
-------------

Wiki macro can be displayed as a separate button in RTE or under >> menu.
By default it's under >> menu. If you want to use it as a separate button then in spring.xml change property button to true
and uncomment <css> section in plugin.xml or include content of wiki-plugin.css into your custom CSS files in your theme.

Installation steps
------------------

1. Install plugin via Admin console
2. Restart application

Development
-----------


### Logging

To enable verbose logging for system renderer then change logging lavel to TRACE on class `com.jivesoftware.community.renderer.util.RenderLogger`.
