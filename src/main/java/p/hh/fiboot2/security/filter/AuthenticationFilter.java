package p.hh.fiboot2.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;
import p.hh.fiboot2.security.provider.AuthenticationWithToken;
import p.hh.fiboot2.security.token.TokenResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final String API_PATH = "";
    private static final String AUTHENTICATE_URL = API_PATH + "/authenticate";

    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String[] loginInfo = getUsernamePasswordOrToken(httpRequest);

        String username = loginInfo[0];
        String password = loginInfo[1];
        String token = loginInfo[2];

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

        try {
            if (postToAuthenticate(httpRequest, resourcePath)) {
                logger.debug("Trying to authenticate user {} by X-Auth-Username method", username);
                processUsernamePasswordAuthentication(httpResponse, username, password);
                return;
            }

            if (!StringUtils.isEmpty(token)) {
                logger.debug("Trying to authenticate user by X-Auth-Token method. Token: {}", token);
                processTokenAuthentication(token);
            }

            logger.debug("AuthenticationFilter is passing request down the filter chain");
            addSessionContextToLogging();
            chain.doFilter(request, response);

        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());

        } finally {
            MDC.remove(TOKEN_SESSION_KEY);
            MDC.remove(USER_SESSION_KEY);
        }
    }

    private String[] getUsernamePasswordOrToken(HttpServletRequest httpRequest) {
        String username = (httpRequest.getHeader("X-Auth-Username")==null) ? "" : httpRequest.getHeader("X-Auth-Username");
        String password = (httpRequest.getHeader("X-Auth-Password")==null) ? "" : httpRequest.getHeader("X-Auth-Password");
        String token = (httpRequest.getHeader("X-Auth-Token")==null) ? "" : httpRequest.getHeader("X-Auth-Token");

        return new String[] {username, password, token};
    }

    private void addSessionContextToLogging() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String tokenValue = "EMPTY";
        if (authentication != null && !StringUtils.isEmpty(authentication.getDetails().toString())) {
            MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
            tokenValue = encoder.encodePassword(authentication.getDetails().toString(), "not_so_random_salt");
        }
        MDC.put(TOKEN_SESSION_KEY, tokenValue);

        String userValue = "EMPTY";
        if (authentication != null && !StringUtils.isEmpty(authentication.getPrincipal().toString())) {
            userValue = authentication.getPrincipal().toString();
        }
        MDC.put(USER_SESSION_KEY, userValue);
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return resourcePath.endsWith(AUTHENTICATE_URL) && httpRequest.getMethod().equals("POST");
    }

    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, String username, String password) throws IOException {
        AuthenticationWithToken resultOfAuthentication =
                (AuthenticationWithToken) tryToAuthenticateWithUsernameAndPassword(username, password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        TokenResponse tokenResponse = new TokenResponse(
                resultOfAuthentication.getUserId(),
                resultOfAuthentication.getUserName(),
                resultOfAuthentication.getDetails().toString()
        );
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.getWriter().print(tokenJsonResponse);
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(String username, String password) {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(requestAuthentication);
    }

    private void processTokenAuthentication(String token) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }
}
