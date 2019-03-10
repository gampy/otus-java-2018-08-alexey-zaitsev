package ru.otus.l12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import ru.otus.l12.servlet.ServletHandler;
import ru.otus.l12.utils.AppProperties;

public class Main {

    public static void main(String[] args) throws Exception {

        ServletHandler handler = new ServletHandler();
        Server server = new Server(Integer.valueOf(AppProperties.getInstance().getProperty("port")));
        server.setHandler(new HandlerList(handler.getResourceHandler(), handler.ServletContextHandler()));

        server.start();
        server.join();
    }
}

