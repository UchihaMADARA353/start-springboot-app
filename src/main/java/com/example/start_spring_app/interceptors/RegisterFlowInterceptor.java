package com.example.start_spring_app.interceptors;

import com.example.start_spring_app.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RegisterFlowInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        // Vérifier seulement la route GET /api/v1/auth/register/token
        String uri = request.getRequestURI();
        if (uri.equals("/api/v1/auth/register/token")) {

            String email = request.getParameter("email");
            if (email == null || email.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Email manquant");
                return false;
            }

            boolean userExists = userRepository.existsByEmail(email);
            if (!userExists) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Vous devez d'abord vous inscrire !");
                return false;
            }
        }

        return true; // OK
    }
}
