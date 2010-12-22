package org.oznyang.freemarker.sweet.test;

import org.oznyang.freemarker.sweet.BeanUtils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oznyang.freemarker.sweet.test.model.Address;
import org.oznyang.freemarker.sweet.test.model.Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-22
 */
public class BeanUtilsTest extends TestCase {

    private Friend friend;

    @Before
    public void setUp() {
        friend = new Friend();
        friend.setSex("boy");
        friend.setName("ozn");
        List<String> emails = new ArrayList<String>();
        emails.add("oxsean@gmail.com");
        emails.add("oznyang@163.com");
        friend.setEmails(emails);
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("age", "27");
        attrs.put("qq", "12345678");
        friend.setAttributes(attrs);
        Address addr = new Address();
        addr.setCountry("China");
        addr.setArea("nanjing gulou");
        friend.setAddress(addr);
    }

    public void testGetValue() {
        Assert.assertEquals("boy", BeanUtils.getValue(friend, "sex"));
        Assert.assertEquals("ozn", BeanUtils.getValue(friend, "name"));
    }

    public void testGetValueFromList() {
        Assert.assertEquals("oznyang@163.com", BeanUtils.getValue(friend, "emails[1]"));
    }

    public void testGetValueFromMap() {
        Assert.assertEquals("27", BeanUtils.getValue(friend, "attributes.age"));
    }

    public void testGetValueFromObject() {
        Assert.assertEquals("nanjing gulou", BeanUtils.getValue(friend, "address.area"));
    }
}
