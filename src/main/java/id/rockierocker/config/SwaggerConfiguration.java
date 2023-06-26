package id.rockierocker.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private String accessTokenUri = "/api/oauth/token";

    public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc ="accessEverything";

    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("all")
                .select()
                .paths(PathSelectors.regex("/api/v1/.*"))
                .build()
                .securityContexts(Lists.newArrayList(securityContextOauth(),securityContextJwt()))
                .securitySchemes(Arrays.asList(securitySchema(), apiKey()))
                .apiInfo(apiInfo());
    }


    public SecurityScheme apiKey() {
        return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header");
    }


    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = newArrayList();
//        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
//        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = newArrayList();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContextOauth() {
        return SecurityContext.builder().securityReferences(defaultAuthOauth())
        		.forPaths(PathSelectors.regex("/api/v1/.*"))
                .build();
    }

    private SecurityContext securityContextJwt() {
    	return SecurityContext.builder()
    			.securityReferences(defaultAuthJwt())
    			.forPaths(PathSelectors.regex("/api/v1/.*"))
    			.build();
    }

    private List<SecurityReference> defaultAuthOauth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
    }
    
    List<SecurityReference> defaultAuthJwt() {
    	AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    	authorizationScopes[0] = authorizationScope;
    	return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}
//    
//    @Bean
//    public SecurityConfiguration security() {
//        return new SecurityConfiguration
//                ("client", "secret", "", "", "Bearer access token", ApiKeyVehicle.HEADER, HttpHeaders.AUTHORIZATION,"");
//    }

    private ApiInfo apiInfo() {
		 return new ApiInfoBuilder()
			 .title("REST full API doc")
			 .description("-")
			 .version("VERSION 0.0.1-RELEASE")
			 .termsOfServiceUrl("http://rockierocker.id/terms")
			 .license("LICENSE")
			 .licenseUrl("http://rockierocker")
			 .build();
    }

}
