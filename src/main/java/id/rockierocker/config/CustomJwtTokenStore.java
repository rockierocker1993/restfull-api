package id.rockierocker.config;

import id.rockierocker.entity.AccessToken;
import id.rockierocker.repository.AccessTokenRepository;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJwtTokenStore extends JwtTokenStore {

    AccessTokenRepository accessTokenRepository;

    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String tokenValue = token.getValue();
        AccessToken accessToken = authentication.isClientOnly() ? new AccessToken():accessTokenRepository.findFirstByUsername(authentication.getName()).orElse(new AccessToken());
        accessToken.setToken(tokenValue);
        accessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
        accessToken.setClientId(authentication.getOAuth2Request().getClientId());
        try {
            Map<String, String> details = new LinkedHashMap<>();
            details = (Map<String, String>) authentication.getDetails();
            accessToken.setDetails("");
        }catch (Exception e){e.printStackTrace();}
        accessTokenRepository.save(accessToken);
    }

    public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }


}
