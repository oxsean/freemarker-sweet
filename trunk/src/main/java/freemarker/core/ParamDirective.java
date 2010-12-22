package freemarker.core;

import freemarker.log.Logger;
import freemarker.template.*;
import org.oznyang.freemarker.sweet.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 在当前的宏上下文设置一个local变量,支持a.b,a['b'],a[b],a[1]这种写法
 * <@param name="my.name" value="123"/>
 * <@param name="my.name">123</@>
 * <@param name="my.name" action="remove"/>
 * <@param name="my.name" action="clear"/>
 * <@param name="my.name" action="add" value="ozn"/>
 * <@param name="my.name" action="addAll" value=['aa','bb']/>
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-20
 */
public class ParamDirective implements TemplateDirectiveModel {

    private static final ConcurrentMap<String, Expression> expsCache = new ConcurrentHashMap<String, Expression>();

    public enum Action {
        /**
         * 添加
         */
        add,
        /**
         * 添加所有
         */addAll,
        /**
         * 修改
         */set,
        /**
         * 删除
         */remove,
        /**
         * 清除所有
         */clear
    }

    private static final Logger logger = Logger.getLogger(ParamDirective.class.getName());

    private static TemplateModelWrapper templateModelWrapper = DefaultTemplateModelWrapper.getDefaultInstance();

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String name = DirectiveHelper.getRequiredParam(params, "name");//参数名
        Action action = Action.set;//类型
        try {
            action = Action.valueOf(DirectiveHelper.getParam(params, "action", "set"));
        } catch (IllegalArgumentException ignored) {
        }
        TemplateModel value = (TemplateModel) params.get("value");//值
        if (value == null && body != null) { //如果为空则取body值
            StringWriter sw = new StringWriter();
            body.render(sw);
            value = new SimpleScalar(sw.toString());
        }
        Macro.Context mc = (Macro.Context) DirectiveHelper.getMacroContext(env);//获取当前macroContext
        Macro.Context invokingMacroContext = env.getCurrentMacroContext();
        try {
            BeanUtils.setValue(env, "currentMacroContext", mc); //切换currentMacroContext为当前macroContext,达到访问当前macroContext的目的
            Expression exp = getExpression(name, env.getTemplate()); //转换name为freemarker表达式
            TemplateModel tm = null; //被设参数的模型
            Object key = null;
            if (action.equals(Action.add) || action.equals(Action.addAll) || action.equals(Action.clear)) { //如果为添加，则直接取出模型
                tm = exp.getAsTemplateModel(env);
            } else {
                if (exp instanceof Identifier) {
                    key = exp.toString();
                    tm = mc == null ? env.getCurrentNamespace() : mc.getLocals();//如果mc为空，则说明不在macro中执行
                } else if (exp instanceof Dot || exp instanceof DynamicKeyName) {
                    Expression target = BeanUtils.getValue(exp, "target");
                    if (target != null)
                        tm = target.getAsTemplateModel(env);
                    if (exp instanceof Dot) {
                        key = BeanUtils.getValue(exp, "key");
                    } else {
                        Expression nameExpression = BeanUtils.getValue(exp, "nameExpression");
                        if (nameExpression != null)
                            key = nameExpression.getAsTemplateModel(env);
                    }
                }
            }
            if (tm == null) {
                logger.warn("expression class [" + exp.getClass() + "] are not support,only Identifier,Dot,DynamicKeyName can be use");
                return;
            }
            ModifiableTemplateModel mtm = templateModelWrapper.wrap(tm);
            if (mtm == null) {
                logger.warn("expression class [" + exp.getClass() + "] are not support");
            }
            mtm.setBeansWrapper(env.getObjectWrapper());
            switch (action) {
                case add:
                    mtm.add(value);
                    break;
                case addAll:
                    mtm.addAll(value);
                    break;
                case set:
                    mtm.set(key, value);
                    break;
                case remove:
                    mtm.remove(key);
                    break;
                case clear:
                    mtm.clear();
                    break;
            }
        } catch (Exception e) {
            logger.error("save param [" + name + "] value [" + value + "] error", e);
        } finally {
            BeanUtils.setValue(env, "currentMacroContext", invokingMacroContext);
        }
    }

    public static Expression getExpression(String source, Template template) throws ParseException {
        Expression exp = expsCache.get(source);
        if (exp == null) {
            FMParserTokenManager tokenSource = new FMParserTokenManager(new SimpleCharStream(new StringReader(source)));
            tokenSource.SwitchTo(FMParserConstants.FM_EXPRESSION);
            FMParser parser = new FMParser(tokenSource);
            parser.template = template;
            exp = parser.Expression();
            expsCache.put(source, exp);
        }
        return exp;
    }
}
