package p.hh.fiboot2.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class TokenResponse {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String userName;
    @JsonProperty
    private Set<String> userRoles;
    @JsonProperty
    private String token;

}
