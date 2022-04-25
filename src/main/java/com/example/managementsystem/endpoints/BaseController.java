package com.example.managementsystem.endpoints;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.enumeration.UserType;
import com.example.managementsystem.services.UserService;
import com.example.managementsystem.system.IAuthenticationFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class BaseController {

    public BaseController() {
    }

    /**
     * Used for logging endpoint runtimes
     *
     * @param start from System.currentTimeMillis
     */
    protected void endpointLogging(Long start) {
        /*https://www.baeldung.com/java-name-of-executing-method*/
        final StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        String msg = "Method: " +
                stackTraceElement[2].getFileName().replace(".java", "") +
                " - " +
                stackTraceElement[2].getMethodName() +
                " lasted " +
                (System.currentTimeMillis() - start) +
                " ms";
        log.info(msg);
    }

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;
    

    protected User getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return userService.getByUsername(authentication.getName());
    }

    protected boolean hasAnyAuthorities(UserType... authorities) {
        if (authorities.length == 0) {
            return true;
        }
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            return false;
        }
        List<String> userAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList());
        for (UserType authority : authorities) {
            if (userAuthorities.contains(authority.toString())) {
                return true;
            }
        }
        return false;
    }

    

}
