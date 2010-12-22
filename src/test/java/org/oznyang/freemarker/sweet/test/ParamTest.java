package org.oznyang.freemarker.sweet.test;

import freemarker.template.Template;
import org.junit.Assert;
import org.oznyang.freemarker.sweet.test.model.Address;
import org.oznyang.freemarker.sweet.test.model.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-22
 */
public class ParamTest extends BaseTest {

    public void testParam() throws Exception {
        Template tpl = cfg.getTemplate("param.ftl");
        Assert.assertEquals(getResult(tpl, null), "cici");
    }

    public void testBeanParam() throws Exception {
        Address addr = new Address();
        addr.setCountry("China");
        addr.setArea("nanjing gulou");
        Map<String, Address> root = new HashMap<String, Address>();
        root.put("addr", addr);
        Template tpl = cfg.getTemplate("bean/param.ftl");
        Assert.assertEquals(getResult(tpl, root), "China");
        Template tpl1 = cfg.getTemplate("bean/param1.ftl");
        Assert.assertEquals(getResult(tpl1, root), "China");
    }

    public void testDateBeanParam() throws Exception {
        Date now = new Date();
        Person person = new Person();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("person", person);
        root.put("now", now);
        Template tpl = cfg.getTemplate("bean/dateParam.ftl");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(getResult(tpl, root), sdf.format(now));
    }

    public void testSequenceHashSetParam() throws Exception {
        assertResultEquals("sequenceHash/setParam.ftl", "ozn");
    }

    public void testSequenceHashAddAllParam() throws Exception {
        assertResultEquals("sequenceHash/addAllParam.ftl", "4");
    }

    public void testSequenceHashRemoveParam() throws Exception {
        assertResultEquals("sequenceHash/removeParam.ftl", "1");
    }

    public void testSequenceHashClearParam() throws Exception {
        assertResultEquals("sequenceHash/clearParam.ftl", "0");
    }

    public void testSimpleSequenceAddParam() throws Exception {
        assertResultEquals("simpleSequence/addParam.ftl", "3");
    }

    public void testSimpleSequenceAddAllParam() throws Exception {
        assertResultEquals("simpleSequence/addAllParam.ftl", "4");
    }

    public void testSimpleSequenceSetParam() throws Exception {
        assertResultEquals("simpleSequence/setParam.ftl", "me");
    }

    public void testSimpleSequenceRemoveParam() throws Exception {
        assertResultEquals("simpleSequence/removeParam.ftl", "2");
    }

    public void testSimpleSequenceClearParam() throws Exception {
        assertResultEquals("simpleSequence/clearParam.ftl", "0");
    }

    public void testSimpleHashSetParam() throws Exception {
        assertResultEquals("simpleHash/setParam.ftl", "cici");
    }

    public void testSimpleHashAddAllParam() throws Exception {
        assertResultEquals("simpleHash/addAllParam.ftl", "5");
    }

    public void testSimpleHashRemoveParam() throws Exception {
        assertResultEquals("simpleHash/removeParam.ftl", "2");
    }

    public void testSimpleHashClearParam() throws Exception {
        assertResultEquals("simpleHash/clearParam.ftl", "0");
    }

}
