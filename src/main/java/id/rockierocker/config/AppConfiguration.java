package id.rockierocker.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfiguration implements WebMvcConfigurer{
    @Value("${spring.jackson.property_naming_strategy:SNAKE_CASE}")
    private String JSON_NAMING_STRATEGY;

    public ObjectMapper getObjectMapper() {
        PropertyNamingStrategy c;
        switch (JSON_NAMING_STRATEGY) {
            case "LOWER_CAMEL_CASE":
                c = PropertyNamingStrategy.LOWER_CAMEL_CASE;
                break;
            case "KEBAB_CASE":
                c = new PropertyNamingStrategy.KebabCaseStrategy();
                break;
            case "UPPER_CAMEL_CASE":
                c = new PropertyNamingStrategy.UpperCamelCaseStrategy();
                break;
            case "LOWER_CASE":
                c = new PropertyNamingStrategy.LowerCaseStrategy();
                break;
            default:
                c = new PropertyNamingStrategy.SnakeCaseStrategy();
                break;
        }
        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(c);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return om;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        messageConverters.add(converter);

        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
    
    @Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
	    registry.addResourceHandler("/webjars/**")
	    .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
    
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = getObjectMapper();
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter ) {
                MappingJackson2HttpMessageConverter jacksonConverter =
                        ((MappingJackson2HttpMessageConverter) converter);

                if (objectMapper == null) {
                    objectMapper = jacksonConverter.getObjectMapper();
                } else {
                    jacksonConverter.setObjectMapper(objectMapper);
                }
            }
        }
    }


}
