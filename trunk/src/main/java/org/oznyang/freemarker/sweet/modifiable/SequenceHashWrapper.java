package org.oznyang.freemarker.sweet.modifiable;

import org.oznyang.freemarker.sweet.BeanUtils;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public class SequenceHashWrapper extends SimpleHashWrapper {

    private TemplateModel tm;

    private Map<String, Object> keyMap;
    private List<Object> keyCollection, valueCollection;

    public static final String SEQUENCEHASH_CLASS_NAME = "freemarker.core.HashLiteral$SequenceHash";

    public SequenceHashWrapper(TemplateModel tm) {
        this.tm = tm;
        keyMap = BeanUtils.getValue(tm, "keyMap");
        keyCollection = BeanUtils.getValue(tm, "keyCollection.sequence.list");
        valueCollection = BeanUtils.getValue(tm, "valueCollection.sequence.list");
    }

    @Override
    protected void put(String key, TemplateModel obj) {
        keyMap.put(key, obj);
        keyCollection.add(key);
        valueCollection.add(obj);
        setSize(keyCollection.size());
    }

    @Override
    protected void remove(String key) {
        keyMap.remove(key);
        int index = keyCollection.indexOf(key);
        keyCollection.remove(index);
        valueCollection.remove(index);
        setSize(keyCollection.size());
    }

    @Override
    public void clear() throws TemplateModelException {
        setSize(0);
        BeanUtils.setValue(tm, "keyMap", new HashMap());
        BeanUtils.setValue(tm, "keyCollection.sequence.list", new ArrayList());
        BeanUtils.setValue(tm, "valueCollection.sequence.list", new ArrayList());
    }

    private void setSize(int i) {
        BeanUtils.setValue(tm, "this$0.size", i);
    }
}
