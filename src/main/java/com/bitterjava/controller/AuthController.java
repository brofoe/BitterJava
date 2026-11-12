package com.bitterjava.controller;

import com.bitterjava.model.User;
import com.bitterjava.service.CsrfTokenService;
import com.bitterjava.service.SessionKeys;
import com.bitterjava.service.UserService;
import com.bitterjava.service.UsernameAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final CsrfTokenService csrfTokenService;

    public AuthController(UserService userService, CsrfTokenService csrfTokenService) {
        this.userService = userService;
        this.csrfTokenService = csrfTokenService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam(name = "_csrf", required = false) String csrfToken,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "password", required = false) String password,
            HttpServletRequest request,
            Model model) {
        csrfTokenService.verify(request.getSession(), csrfToken);

        try {
            userService.register(username, password);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException | UsernameAlreadyExistsException exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("username", username);
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(name = "registered", defaultValue = "false") boolean registered,
            @RequestParam(name = "loggedOut", defaultValue = "false") boolean loggedOut,
            Model model) {
        model.addAttribute("registered", registered);
        model.addAttribute("loggedOut", loggedOut);
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "_csrf", required = false) String csrfToken,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "password", required = false) String password,
            HttpServletRequest request,
            Model model) {
        csrfTokenService.verify(request.getSession(), csrfToken);

        Optional<User> user = userService.authenticate(username, password);
        if (user.isEmpty()) {
            model.addAttribute("error", "Invalid username or password.");
            model.addAttribute("username", username);
            return "login";
        }

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            // Replace the session ID after login to prevent session fixation.
            oldSession.invalidate();
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionKeys.USER_ID, user.get().id());
        session.setAttribute(SessionKeys.USERNAME, user.get().username());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(
            @RequestParam(name = "_csrf", required = false) String csrfToken,
            HttpServletRequest request) {
        csrfTokenService.verify(request.getSession(), csrfToken);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?loggedOut=true";
    }
}
