package org.oznyang.freemarker.sweet.modifiable;

import org.oznyang.freemarker.sweet.BeanUtils;
import freemarker.template.*;

import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public class SimpleHashWrapper extends AbstractTemplateModel {

    private SimpleHash tm;
    private Map map;

    public SimpleHashWrapper(TemplateModel tm) {
        this.tm = (SimpleHash) tm;
        this.map = BeanUtils.getValue(tm, "map");
    }

    public SimpleHashWrapper() {
    }

    @Override
    public void addAll(TemplateModel obj) throws TemplateModelException {
        if (obj instanceof TemplateHashModelEx) {
            TemplateHashModelEx tme = (TemplateHashModelEx) obj;
            TemplateModelIterator keys = tme.keys().iterator();
            TemplateModelIterator values = tme.values().iterator();
            for (int i = 0; i < tme.size(); i++) {
                set(keys.next(), values.next());
            }
        }
    }

    @Override
    public void set(Object key, TemplateModel obj) throws TemplateModelException {
        put(key.toString(), obj);
    }

    @Override
    public void remove(Object key) throws TemplateModelException {
        if (key instanceof String) {
            remove((String) key);
        } else if (key instanceof TemplateSequenceModel) {
            TemplateSequenceModel tsm = (TemplateSequenceModel) key;
            for (int i = 0; i < tsm.size(); i++) {
                remove(tsm.toString());
            }
        }
    }

    protected void put(String key, TemplateModel obj) {
        tm.put(key, obj);
    }

    protected void remove(String key) {
        tm.remove(key);
    }

    @Override
    public void clear() throws TemplateModelException {
        map.clear();
        clean();
    }

    private void clean() {
        BeanUtils.setValue(tm, "unwrappedMap", null);
    }
}
