package p.hh.fiboot2.security.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {
    
    private static final long serialVersionUID = -2333619000404580551L;

    private Long userId;
    private String userName;
    private Set<String> roles;

    public AuthenticationWithToken(Object aPrincipal, Object aCredentials) {
        super(aPrincipal, aCredentials);
    }

    public AuthenticationWithToken(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public void setToken(String token) {
        setDetails(token);
    }
    public String getToken() {
        return (String)getDetails();
    }
}
