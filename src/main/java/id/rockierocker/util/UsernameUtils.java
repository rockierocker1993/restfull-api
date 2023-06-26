package id.rockierocker.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

public class UsernameUtils {
    public static String getUsername(HttpServletRequest request) throws UsernameNotFoundException {
        String username = null;
        if (null != request.getUserPrincipal()) {
            username = request.getUserPrincipal().getName();
        }

        if (StringUtils.isEmpty(username)) {
            username = request.getHeader("X-User");
        }

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Unauthorized user");
        }

        return username;
    }

    public static String getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication==null) {
            return null;
        }else {
            return authentication.getName();
        }
    }

}
