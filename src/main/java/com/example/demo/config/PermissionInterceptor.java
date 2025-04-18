package com.example.demo.config;

import com.example.demo.domain.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy thông tin request
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String httpMethod = request.getMethod();
        String requestURI = request.getRequestURI();

        // Log để debug
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path = " + path);
        System.out.println(">>> httpMethod = " + httpMethod);
        System.out.println(">>> requestURI = " + requestURI);

        // Bỏ qua cho /api/v1/email/** và /api/v1/subscribers/**
        if (requestURI.startsWith("/api/v1/email") || requestURI.startsWith("/api/v1/subscribers") || requestURI.equals("/error")) {
            System.out.println(">>> Skipping PermissionInterceptor for: " + requestURI);
            return true;
        }

        // Lấy email của user hiện tại
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        System.out.println(">>> Current user email: " + email);
        if (email == null || email.isEmpty()) {
            System.out.println(">>> No authenticated user for: " + requestURI);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("{\"error\": \"User not authenticated\"}");
            return false;
        }

        // Lấy permissions của user dựa trên email
        List<Permission> userPermissions = permissionService.getPermissionsByUserEmail(email);
        System.out.println(">>> User permissions: " + (userPermissions != null ? userPermissions.size() : 0));
        if (userPermissions == null || userPermissions.isEmpty()) {
            System.out.println(">>> No permissions found for user: " + email);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("{\"error\": \"No permissions found for user\"}");
            return false;
        }

        // Kiểm tra xem user có permission khớp với request không
        boolean hasPermission = userPermissions.stream().anyMatch(permission ->
                permission.getApiPath().equals(path) && permission.getMethod().equals(httpMethod)
        );
        System.out.println(">>> Has permission for " + path + " " + httpMethod + ": " + hasPermission);

        if (!hasPermission) {
            System.out.println(">>> Access denied for user: " + email + " on " + path + " " + httpMethod);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("{\"error\": \"Access denied: Insufficient permissions\"}");
            return false;
        }

        return true;
    }
}