package org.oznyang.freemarker.sweet.modifiable;

import freemarker.ext.beans.BeansWrapper;
import org.oznyang.freemarker.sweet.BeanUtils;
import org.oznyang.freemarker.sweet.ModifiableTemplateModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public abstract class AbstractTemplateModel implements ModifiableTemplateModel {

    public static final String ERROR_MESSAGE = "not support this action type";
    public static final String NUMERICALRANGE_CLASS_NAME = "freemarker.core.NumericalRange";

    protected BeansWrapper beansWrapper;

    public void setBeansWrapper(ObjectWrapper objectWrapper) {
        if (objectWrapper instanceof BeansWrapper)
            this.beansWrapper = (BeansWrapper) objectWrapper;
    }

    public void add(TemplateModel obj) throws TemplateModelException {
        throw new TemplateModelException(ERROR_MESSAGE);
    }

    public void addAll(TemplateModel obj) throws TemplateModelException {
        throw new TemplateModelException(ERROR_MESSAGE);

    }

    public void set(Object key, TemplateModel obj) throws TemplateModelException {
        throw new TemplateModelException(ERROR_MESSAGE);

    }

    public void remove(Object key) throws TemplateModelException {
        throw new TemplateModelException(ERROR_MESSAGE);

    }

    public void clear() throws TemplateModelException {
        throw new TemplateModelException(ERROR_MESSAGE);
    }

    protected List<Integer> getRange(Object key, int size) {
        List<Integer> range = new ArrayList<Integer>();
        try {
            if (key instanceof String) {
                range.add(Integer.valueOf((String) key));
            } else if (key instanceof TemplateNumberModel) {
                range.add(((TemplateNumberModel) key).getAsNumber().intValue());
            } else if (NUMERICALRANGE_CLASS_NAME.equals(key.getClass().getName())) {
                Integer upper = BeanUtils.getValue(key, "upper");
                Integer lower = BeanUtils.getValue(key, "lower");
                if (upper == 0)
                    upper = size - 1;
                for (; lower <= upper; lower++) {
                    range.add(lower);
                }
            }
        } catch (Exception ignored) {
        }
        return range;
    }
}
