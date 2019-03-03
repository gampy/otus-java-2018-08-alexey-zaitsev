import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.l12.ORM.DBService;
import ru.otus.l12.ORM.hibernate.HibernateDBService;
import ru.otus.l12.servlet.CacheServlet;
import ru.otus.l12.servlet.DispatcherServlet;
import ru.otus.l12.servlet.TemplateProcessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
            properties.load(fileInputStream);

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(properties.getProperty("web_catalog"));

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            TemplateProcessor templateProcessor = new TemplateProcessor(properties);
            DBService dbService =  new HibernateDBService();

            context.addServlet(new ServletHolder(new DispatcherServlet(templateProcessor, dbService)), "/dispatcher");
            context.addServlet(new ServletHolder(new CacheServlet(templateProcessor, dbService)), "/cache");

//            context.addServlet(DispatcherServlet.class, "/dispatcher");
//            context.addServlet(CacheServlet.class, "/cache");

            Server server = new Server(Integer.valueOf(properties.getProperty("port")));
            server.setHandler(new HandlerList(resourceHandler, context));

            server.start();
            server.join();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

