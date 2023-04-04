package com.xx.p.modules.admin.util;

import com.xx.p.domain.AdminUserDetails;
import com.xx.p.modules.admin.module.entity.UmsAdmin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUtil {

    public static UmsAdmin getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) auth.getPrincipal();
        return adminUserDetails.getUmsAdmin();
    }
}
