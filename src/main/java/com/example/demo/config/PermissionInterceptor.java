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

        // Lấy email của user hiện tại
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email == null || email.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("{\"error\": \"User not authenticated\"}");
            return false;
        }

        // Lấy permissions của user dựa trên email
        List<Permission> userPermissions = permissionService.getPermissionsByUserEmail(email);
        if (userPermissions == null || userPermissions.isEmpty()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("{\"error\": \"No permissions found for user\"}");
            return false;
        }

        // Kiểm tra xem user có permission khớp với request không
        boolean hasPermission = userPermissions.stream().anyMatch(permission ->
                permission.getApiPath().equals(path) && permission.getMethod().equals(httpMethod)
        );

        if (!hasPermission) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("{\"error\": \"Access denied: Insufficient permissions\"}");
            return false;
        }

        return true;
    }
}