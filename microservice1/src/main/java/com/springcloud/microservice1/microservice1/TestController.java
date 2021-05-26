package com.springcloud.microservice1.microservice1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping("/getScope")
    public Mono<?> getScope() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return Mono.just(authentication.getAuthorities());
    }

    @GetMapping("/get")
    public Mono<String> get() {
        return Mono.just("Hello");
    }
}
