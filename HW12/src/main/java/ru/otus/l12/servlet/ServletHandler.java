package ru.otus.l12.servlet;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.l12.ORM.DBService;
import ru.otus.l12.ORM.hibernate.HibernateDBService;
import ru.otus.l12.utils.AppProperties;
import ru.otus.l12.utils.TemplateProcessor;

public class ServletHandler {

    public ResourceHandler getResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(AppProperties.getInstance().getProperty("web_catalog"));
        return resourceHandler;
    }

    public ServletContextHandler ServletContextHandler() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor(AppProperties.getInstance());
        DBService dbService = new HibernateDBService();

        context.addServlet(new ServletHolder(new AuthServlet(templateProcessor, dbService)), "/auth");
        context.addServlet(new ServletHolder(new CacheServlet(templateProcessor, dbService)), "/cache");
        return context;
    }
}
