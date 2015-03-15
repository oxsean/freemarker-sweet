### Add some usefull feature for freemarker ###
  * Macro nest support
  * TemplateModel modify
  * Set param in a macro body
  * Template extend support
  * Access strut2 freemarker configuration in spring



you need add two directive into freemarker sharedVariable,liks this:
```
    public static void init(Configuration cfg) {
        cfg.setSharedVariable("param", new ParamDirective());
        cfg.setSharedVariable("expose", new ExposeDirective());
    }
```

esaily you can call
```
    DirectiveHelper.init(cfg); 
```

example:
```
<#macro outer name="cici">
<@expose/>
<#nested>
</#macro>

<#macro inner parent="">
<@expose parent="parent"/>
${parent.name}
</#macro>

<@outer><@inner/></@>
```

add <@expose/> to a macro body at the first line,
then you can access parent macro context use parent variable

or set a param use:

<@param name="parent.name" value="ozn"/>

see testCase code for more info