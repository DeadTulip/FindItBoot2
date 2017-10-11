package p.hh.fiboot2.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class TokenResponse {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String userName;
    @JsonProperty
    private String token;

}
