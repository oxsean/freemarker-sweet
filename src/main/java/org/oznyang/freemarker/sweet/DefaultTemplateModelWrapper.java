package org.oznyang.freemarker.sweet;

import freemarker.ext.beans.BeanModel;
import org.oznyang.freemarker.sweet.modifiable.BeanModelWapper;
import org.oznyang.freemarker.sweet.modifiable.SequenceHashWrapper;
import org.oznyang.freemarker.sweet.modifiable.SimpleHashWrapper;
import org.oznyang.freemarker.sweet.modifiable.SimpleSequenceWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 默认的ModifiableTemplateModel包装实现.
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public class DefaultTemplateModelWrapper implements TemplateModelWrapper {

    private static final TemplateModelWrapper INSTANCE = new DefaultTemplateModelWrapper();

    public static TemplateModelWrapper getDefaultInstance() {
        return INSTANCE;
    }

    public ModifiableTemplateModel wrap(TemplateModel tm) throws TemplateModelException {
        if (tm instanceof SimpleHash) {
            return new SimpleHashWrapper(tm);
        } else if (tm instanceof SimpleSequence) {
            return new SimpleSequenceWrapper(tm);
        } else if (SequenceHashWrapper.SEQUENCEHASH_CLASS_NAME.equals(tm.getClass().getName())) {
            return new SequenceHashWrapper(tm);
        } else if (tm instanceof BeanModel) {
            return new BeanModelWapper(tm);
        }
        return null;
    }
}
