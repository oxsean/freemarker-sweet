package org.oznyang.freemarker.sweet.support;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.spring.SpringObjectFactory;
import freemarker.template.TemplateException;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.oznyang.freemarker.sweet.DirectiveHelper;

import javax.servlet.ServletContext;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 10-12-22
 */
public class SpringWiredFreemarkerManager extends FreemarkerManager {

/*    @Autowired
    private SomeService someService;*/  //you can autoWired some spirng bean here

    @Inject
    public SpringWiredFreemarkerManager(ObjectFactory objectFactory) {
        ((SpringObjectFactory) objectFactory).autoWireBean(this);
    }

    @Override
    public void init(ServletContext servletContext) throws TemplateException {
        super.init(servletContext);
        DirectiveHelper.init(config);
/*        config.setSharedVariable("someService", someService);  */
    }
}
