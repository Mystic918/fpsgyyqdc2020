package app.utils;

import conf.PathSys;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import spark.Request;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

public class ViewUtil {

    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("request", request);
        model.put("route", new PathSys());
        model.put("format", new TplUtil());

        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(properties);

        Template template = velocityEngine.getTemplate(templatePath, "UTF-8");
        if(model instanceof Map) {
            Map modelMap = (Map)model;
            VelocityContext context = new VelocityContext(modelMap);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } else {
            throw new IllegalArgumentException("modelAndView must be of type java.util.Map");
        }
    }

}
