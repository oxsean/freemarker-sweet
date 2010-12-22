package org.oznyang.freemarker.sweet;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 模型包装接口，把普通的TemplateModel包装成可编辑的TemplateModel.
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public interface TemplateModelWrapper {
    ModifiableTemplateModel wrap(TemplateModel tm) throws TemplateModelException;
}
