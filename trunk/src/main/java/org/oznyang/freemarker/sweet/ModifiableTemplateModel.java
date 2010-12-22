package org.oznyang.freemarker.sweet;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 模型修改相关方法接口.
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-21
 */
public interface ModifiableTemplateModel extends TemplateModel {
    /**
     * 设置  objectWrapper
     *
     * @param objectWrapper freemarker包装对象
     */
    void setBeansWrapper(ObjectWrapper objectWrapper);

    /**
     * 向模型中添加一个对象，仅list类型模型支持
     *
     * @param obj 对象
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    void add(TemplateModel obj) throws TemplateModelException;

    /**
     * 批量向模型中添加对象
     *
     * @param obj 对象
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    void addAll(TemplateModel obj) throws TemplateModelException;

    /**
     * 设置模型中的某个对象
     *
     * @param key 对象的索引字段
     * @param obj 对象
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    void set(Object key, TemplateModel obj) throws TemplateModelException;

    /**
     * 删除模型中某个对象
     *
     * @param key 对象的索引字段
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    void remove(Object key) throws TemplateModelException;

    /**
     * 清空模型包含的对象
     *
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    void clear() throws TemplateModelException;
}
