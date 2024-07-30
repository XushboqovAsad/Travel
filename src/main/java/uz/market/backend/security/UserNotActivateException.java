package uz.market.backend.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivateException extends AuthenticationException {
    public UserNotActivateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotActivateException(String explanation) {
        super(explanation);
    }
}
