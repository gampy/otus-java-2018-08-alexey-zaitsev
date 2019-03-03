package ru.otus.l12.servlet;

import org.h2.engine.User;
import ru.otus.l12.ORM.DBService;
import ru.otus.l12.cache.CacheEngine;
import ru.otus.l12.cache.CacheEngineImpl;
import ru.otus.l12.cache.CachedElement;
import ru.otus.l12.cache.CachedElementImpl;
import ru.otus.l12.dataSets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cache")
public class CacheServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    private static CacheEngine<Long, Object> cache = new CacheEngineImpl<>(100, 0, 0, false);

    public CacheServlet(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCache();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<b>All cached DB users:</b><br/>");
        out.print(cache.toString());
        out.close();

        dbService.closeConnection();
    }


    private void  setCache() {
       List<UserDataSet> users = dbService.readAll();
       for (UserDataSet user : users) {
           cache.put(user.getId(), user.getLogin());
       }
    }

}
