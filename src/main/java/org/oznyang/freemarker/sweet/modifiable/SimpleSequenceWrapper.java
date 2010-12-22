package org.oznyang.freemarker.sweet.modifiable;

import org.oznyang.freemarker.sweet.BeanUtils;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public class SimpleSequenceWrapper extends AbstractTemplateModel {


    private SimpleSequence tm;
    private List<Object> list;

    public SimpleSequenceWrapper(TemplateModel tm) {
        this.tm = (SimpleSequence) tm;
        this.list = BeanUtils.getValue(tm, "list");
    }

    @Override
    public void add(TemplateModel obj) throws TemplateModelException {
        tm.add(obj);
    }

    @Override
    public void addAll(TemplateModel obj) throws TemplateModelException {
        if (obj instanceof TemplateSequenceModel) {
            TemplateSequenceModel tsm = (TemplateSequenceModel) obj;
            for (int i = 0; i < tsm.size(); i++) {
                add(tsm.get(i));
            }
        }
    }

    @Override
    public void set(Object key, TemplateModel obj) throws TemplateModelException {
        for (int index : getRange(key, list.size())) {
            list.set(index, obj);
        }
        clean();
    }

    @Override
    public void remove(Object key) throws TemplateModelException {
        List<Object> newList = new ArrayList<Object>();
        List<Integer> range = getRange(key, list.size());
        for (int i = 0; i < list.size(); i++) {
            if (range.indexOf(i) == -1)
                newList.add(list.get(i));
        }
        BeanUtils.setValue(tm, "list", newList);
        clean();
    }

    @Override
    public void clear() throws TemplateModelException {
        list.clear();
        clean();
    }

    private void clean() {
        BeanUtils.setValue(tm, "unwrappedList", null);
    }

}
