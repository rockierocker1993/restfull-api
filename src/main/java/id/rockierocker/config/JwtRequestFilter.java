package id.rockierocker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.rockierocker.entity.AccessToken;
import id.rockierocker.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	AccessTokenRepository accessTokenRepository;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.toLowerCase().startsWith("bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			AccessToken accessToken = accessTokenRepository.findFirstByToken(jwtToken).orElse(null);
			if(accessToken==null){
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				final Map<String, Object> body = new HashMap<>();
				body.put("error", "unauthorized");
				body.put("error_description", "Full authentication is required to access this resource");
				final ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getOutputStream(), body);
				return;
			}
		} 
		chain.doFilter(request, response);
	}
	
}
