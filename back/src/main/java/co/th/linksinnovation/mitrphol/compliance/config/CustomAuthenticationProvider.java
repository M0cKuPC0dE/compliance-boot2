package co.th.linksinnovation.mitrphol.compliance.config;

import co.th.linksinnovation.mitrphol.compliance.model.Authority;
import co.th.linksinnovation.mitrphol.compliance.model.UserDetails;
import co.th.linksinnovation.mitrphol.compliance.model.authen.Authenticate;
import co.th.linksinnovation.mitrphol.compliance.repository.UserDetailsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jirawong Wongdokpuang <jiraowng@linksinnovation.com>
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails findOne = userDetailsRepository.findById(authentication.getName().toLowerCase()).get();
        if (findOne != null && iserviceAuthen(authentication)) {
            Authentication auth = new UsernamePasswordAuthenticationToken(authentication.getName().toLowerCase(), authentication.getCredentials().toString(), findOne.getAuthorities());
            return auth;
        } else if (findOne == null) {
            List<UserDetails> findUuids = userDetailsRepository.findByUuidAndUuidNotNull(authentication.getName());
            if (findUuids.size() == 1) {
                List<Authority> authorities = new ArrayList<>();
                authorities.add(new Authority("Owner"));
                return new UsernamePasswordAuthenticationToken(findUuids.get(0).getUsername(), authentication.getCredentials().toString(), authorities);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean iserviceAuthen(Authentication authentication) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("App-Key", "pap9DekzlVROhBplLVpx94Yk158w.RbNC8PRH5X3Kkju3JLnMu7m1JC70zhjZP6R8BFP-cINqY-t.8oS6lJbyw");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", authentication.getName().trim().toLowerCase());
        map.add("password", authentication.getCredentials().toString());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Authenticate> postForEntity = rest.exchange("https://api.mitrphol.com:3001/authentication/login", HttpMethod.POST, request, Authenticate.class);
        return postForEntity.getStatusCode() == HttpStatus.OK;
    }
}
