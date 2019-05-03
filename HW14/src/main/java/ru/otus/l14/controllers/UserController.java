package ru.otus.l14.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.l14.model.UserDataSet;
import ru.otus.l14.security.Crypt;
import ru.otus.l14.services.db.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private String msg = "Welcome";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("login", "");
//        model.addAttribute("password", "");
      return "redirect:/user/dispatcher?page=sign_in.html";
    }

    @GetMapping("/user/dispatcher")
    public String dispatcher(Model model, @RequestParam(name = "page") String page) {
        model.addAttribute("message", msg);
        return page;
    }

    @PostMapping({"/user/auth"})
    public String userAuth(@ModelAttribute("login") String login, @ModelAttribute("password") String password) {
        UserDataSet user = userService.readByLogin(login);

        if(isValidUser(user, password)) {
            return "redirect:/user/list";
        }
        else{
            msg = "Wrong password or user " + login +  " doesn't exist";
            return "redirect:/";
        }

    }

    private boolean isValidUser (UserDataSet user, String password) {

        if (user != null) {
            return user.getPassword().contentEquals(Crypt.cryptWithMD5(password));
        } else {
            return false;
        }
    }

    @PostMapping("/user/create")
    public String userCreate(@ModelAttribute("login") String login, @ModelAttribute("password") String password) {
        UserDataSet user = new UserDataSet(login, password);
        try {
            userService.saveUser(user);
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            msg = "User " + login + " already exists. Choose another login";
            return "redirect:/user/dispatcher?page=sign_up.html";
        }  catch (Exception e) {
            return "unspecific_error.html";
        }
        msg = "Registration of " + login + " completed successfully";
        return "redirect:/user/dispatcher?page=sign_in.html";
    }


    @GetMapping({"/user/list"})
    public String userList(Model model) {
        List<UserDataSet> users = userService.readAll();
        model.addAttribute("users", users);
        return "userList.html";
    }

}
