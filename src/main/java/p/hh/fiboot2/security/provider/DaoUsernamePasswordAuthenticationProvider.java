package p.hh.fiboot2.security.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import p.hh.fiboot2.security.token.TokenService;

public class DaoUsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider {

    private TokenService tokenService;

    public DaoUsernamePasswordAuthenticationProvider(TokenService tokenService, ApplicationUserService userService) {
        this.tokenService = tokenService;
        this.setUserDetailsService(userService);
        // this.setPasswordEncoder(encoder());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApplicationUser result = (ApplicationUser) super.authenticate(authentication).getPrincipal();
        AuthenticationWithToken authToken = new AuthenticationWithToken(result.getUsername(), result.getPassword());
        String newToken = tokenService.generateNewToken();
        authToken.setToken(newToken);
        authToken.setUserId(result.getUserId());
        authToken.setUserName(result.getUsername());
        authToken.setAuthenticated(true);
        tokenService.store(newToken, authToken);
        return authToken;
    }

//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder(10);
//    }
}
