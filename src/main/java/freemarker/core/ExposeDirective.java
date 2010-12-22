package freemarker.core;

import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.oznyang.freemarker.sweet.DirectiveHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 暴露当前宏上下文到全局运行时变量,这样就可以在当前运行上下文中通过宏的UnifiedCall访问到此MacroContext.
 * 同时在当前运行上下文定义了一个叫parent的local变量，可以访问父宏的local变量
 * <p/>
 * 使用方法：
 * 在macro的首部加入<@expose/>
 * 这样就可以通过${parent.xx}}来访问父宏的参数
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-20
 */
public class ExposeDirective implements TemplateDirectiveModel {

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Macro.Context mc = env.getCurrentMacroContext();
        if (mc == null) {
            throw new TemplateException("macro context not found,this directive must be call in macro", env);
        }
        List stack = DirectiveHelper.getElementStack(env);
        Object macroCall = null, parentMacroCall = null;
        for (int i = stack.size() - 1; i > -1; i--) { //遍历当前运行时堆栈，找到当前宏的UnifiedCall
            if (stack.get(i) instanceof Macro) {
                Object call = stack.get(i - 1);
                if (macroCall == null) {
                    macroCall = call;
                } else {
                    parentMacroCall = call;
                    break;
                }
            }
        }
        DirectiveHelper.exposeMacroContext(env, macroCall, mc);//暴露当前宏local变量上下文到全局上下文
        if (parentMacroCall == null)
            return;
        Macro.Context parentMc = (Macro.Context) DirectiveHelper.getMacroContext(env, parentMacroCall);//获取父宏上下文
        if (parentMc != null)
            mc.setLocalVar(DirectiveHelper.getParam(params, "parent", "parent"), parentMc.getLocals());//在当前宏上下文中保存一个叫parent的变量，值为父宏的local变量
    }
}
