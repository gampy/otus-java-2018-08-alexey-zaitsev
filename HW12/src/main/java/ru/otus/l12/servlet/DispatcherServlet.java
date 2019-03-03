package ru.otus.l12.servlet;

import ru.otus.l12.ORM.DBService;
import ru.otus.l12.ORM.hibernate.HibernateDBService;
import ru.otus.l12.dataSets.UserDataSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/dispatcher")
public class DispatcherServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    public DispatcherServlet(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    private static Map<String, Object> getPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
//        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("login", request.getParameter("login"));
//        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String action = request.getParameter("action");

        if (action.contentEquals("signIn")) {
            signIn(request, response);
        } else if (action.contentEquals("signUp")) {
            try {
                signUp(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(isValidUser(login, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("cache");
            dispatcher.forward(request, response);
        }
        else{
            String page = templateProcessor.getPage(templateProcessor.getProperties().getProperty("login_error_tmpl"), getPageVariablesMap(request));
            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String login = request.getParameter("login");
        String password=request.getParameter("password");

        UserDataSet user = new UserDataSet(login, password);
        String pageAddr = null;
        try {
            dbService.saveUser(user);
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            pageAddr = templateProcessor.getProperties().getProperty("regist_error_tmpl");
        }
        if (pageAddr == null) pageAddr = templateProcessor.getProperties().getProperty("regist_success_tmpl");
        String page = templateProcessor.getPage(pageAddr, getPageVariablesMap(request));
        response.getWriter().println(page);
    }


    private boolean isValidUser (String username, String password) {
        UserDataSet user = dbService.readByLogin(username);

        if (user != null) {
            return user.getPassword().contentEquals(HibernateDBService.cryptWithMD5(password));
        } else {
            return false;
        }
    }

}
