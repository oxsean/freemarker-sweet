package org.oznyang.freemarker.sweet.modifiable;

import freemarker.ext.beans.BeanModel;
import org.oznyang.freemarker.sweet.BeanUtils;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public class BeanModelWapper extends AbstractTemplateModel {

    private Object bean;


    public BeanModelWapper(TemplateModel tm) {
        this.bean = ((BeanModel) tm).getWrappedObject();
    }

    @Override
    public void set(Object key, TemplateModel obj) throws TemplateModelException {
        BeanUtils.setSimpleValue(bean, key.toString(), beansWrapper.unwrap(obj));
    }

    @Override
    public void remove(Object key) throws TemplateModelException {
        BeanUtils.setSimpleValue(bean, key.toString(), null);
    }
}
