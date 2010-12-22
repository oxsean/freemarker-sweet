package org.oznyang.freemarker.sweet.test;

import org.junit.Test;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-22
 */
public class ExposeTest extends BaseTest {

    public void testExpose() throws Exception {
        assertResultEquals("expose.ftl", "cici");
    }

}
