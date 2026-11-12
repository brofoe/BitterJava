package com.bitterjava.controller;

import com.bitterjava.service.CsrfTokenService;
import com.bitterjava.service.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SharedModelAdvice {

    private final CsrfTokenService csrfTokenService;

    public SharedModelAdvice(CsrfTokenService csrfTokenService) {
        this.csrfTokenService = csrfTokenService;
    }

    @ModelAttribute
    public void addSharedAttributes(HttpServletRequest request, Model model) {
        // Make account navigation and a session-bound form token available to every page.
        HttpSession session = request.getSession();
        model.addAttribute("currentUsername", session.getAttribute(SessionKeys.USERNAME));
        model.addAttribute("csrfToken", csrfTokenService.getToken(session));
    }
}
