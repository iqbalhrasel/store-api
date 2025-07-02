package com.pxc.store_api.products;

import com.pxc.store_api.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ProductSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>
                                      .AuthorizationManagerRequestMatcherRegistry registry) {

        registry.requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/products/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/products/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/products/**").permitAll();
    }
}
