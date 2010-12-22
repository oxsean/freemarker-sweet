package org.oznyang.freemarker.sweet;

import freemarker.core.*;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-20
 */
public class DirectiveHelper {

    public static final String ELEMENT_STACK = "elementStack";
    public static final String MACRO_CONTEXT_MAP = "__macro_context";

    /**
     * 初始化Directive定义
     *
     * @param cfg freemarker configuration
     */
    public static void init(Configuration cfg) {
        cfg.setSharedVariable("param", new ParamDirective());
        cfg.setSharedVariable("expose", new ExposeDirective());
    }

    /**
     * 获取当前的运行时环境堆栈
     *
     * @param env 运行时上下文
     * @return 堆栈
     */
    public static List getElementStack(Environment env) {
        return BeanUtils.getValue(env, ELEMENT_STACK);
    }

    /**
     * 获取必须的参数
     *
     * @param params 参数map
     * @param key    key
     * @return 参数
     * @throws freemarker.template.TemplateException 当参数不存在抛出模型异常
     */
    public static String getRequiredParam(Map params, String key) throws TemplateException {
        Object value = params.get(key);
        if (value == null || "".equals(value.toString())) {
            throw new TemplateModelException("param " + key + " is required");
        }
        return value.toString();
    }

    /**
     * 获取可选的参数
     *
     * @param params       参数map
     * @param key          key
     * @param defaultValue 默认值
     * @return 参数
     */
    public static String getParam(Map params, String key, String defaultValue) {
        Object value = params.get(key);
        return value == null || "".equals(value.toString()) ? defaultValue : value.toString();
    }

    /**
     * 从运行时上下文中获取最近macro的local变量
     *
     * @param env 运行时上下文
     * @return local变量
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    public static LocalContext getMacroContext(Environment env) throws TemplateModelException {
        List stack = DirectiveHelper.getElementStack(env);
        for (int i = stack.size() - 1; i > -1; i--) {
            if (stack.get(i) instanceof Macro) {
                return getMacroContext(env, stack.get(i - 1));
            }
        }
        return null;
    }

    /**
     * 从运行时上下文中获取某个macro UnifiedCall的local变量
     *
     * @param env       运行时上下文
     * @param macroCall macro UnifiedCall
     * @return 宏运行时上下文
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    public static LocalContext getMacroContext(Environment env, Object macroCall) throws TemplateModelException {
        MacroContextMap map = (MacroContextMap) env.getVariable(MACRO_CONTEXT_MAP);
        return map != null ? map.get(macroCall) : null;
    }

    /**
     * 暴露当前MacroContext到全局运行时变量
     *
     * @param env       运行时上下文
     * @param macroCall macro UnifiedCall
     * @param context   宏运行时上下文
     * @throws freemarker.template.TemplateModelException 模型异常
     */
    public static void exposeMacroContext(Environment env, Object macroCall, LocalContext context) throws TemplateModelException {
        MacroContextMap map = (MacroContextMap) env.getGlobalVariable(MACRO_CONTEXT_MAP);
        if (map == null) {
            map = new MacroContextMap();
            env.setGlobalVariable(MACRO_CONTEXT_MAP, map);
        }
        map.put(macroCall, context);
    }

    static class MacroContextMap implements TemplateModel {

        private Map<Object, LocalContext> map;

        MacroContextMap() {
            this.map = new HashMap<Object, LocalContext>();
        }

        LocalContext get(Object key) {
            return map.get(key);
        }

        void put(Object key, LocalContext context) {
            map.put(key, context);
        }
    }
}
