package org.oznyang.freemarker.sweet.test;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.ExposeDirective;
import freemarker.core.ParamDirective;
import freemarker.template.Configuration;
import freemarker.template.Template;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;

import java.io.StringWriter;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-22
 */
public abstract class BaseTest extends TestCase {

    protected Configuration cfg;

    @Before
    public void setUp() {
        cfg = new Configuration();
        cfg.setTemplateLoader(new ClassTemplateLoader(getClass(), "tpl"));
        cfg.setSharedVariable("param", new ParamDirective());
        cfg.setSharedVariable("expose", new ExposeDirective());
    }

    protected String getResult(Template tpl, Map root) throws Exception {
        StringWriter sw = new StringWriter();
        tpl.process(root, sw);
        return sw.toString().trim();
    }

    protected void assertResultEquals(String tplName, String str) throws Exception {
        Assert.assertEquals(getResult(cfg.getTemplate(tplName), null), str);
    }
}
