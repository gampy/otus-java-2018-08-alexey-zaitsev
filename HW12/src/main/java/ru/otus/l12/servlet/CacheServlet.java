package ru.otus.l12.servlet;

import ru.otus.l12.ORM.DBService;
import ru.otus.l12.cache.CacheHandler;
import ru.otus.l12.utils.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cache")
public class CacheServlet extends HttpServlet {

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;


    public CacheServlet(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    private static Map<String, Object> getPageVariablesMap(CacheHandler cacheHandler) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", cacheHandler.getCachedUsers());
        return pageVariables;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CacheHandler cacheHandler = new CacheHandler();
        cacheHandler.pushUsersToCache(dbService);

        response.setContentType("text/html");

        String pageAddr = templateProcessor.getProperties().getProperty("cache_output_tmpl");

        String page = templateProcessor.getPage(pageAddr, getPageVariablesMap(cacheHandler));
        response.getWriter().println(page);

//        dbService.closeConnection();
    }




}
