package com.minibloxx;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Our first, deliberately tiny, web endpoint.
 *
 * Its only job is to prove that an incoming HTTP request can reach
 * our Java code and get a response back. No domain logic yet.
 */
@RestController
public class HelloController {

    // Maps HTTP GET requests for the URL path "/api/hello" to this method.
    @GetMapping("/api/hello")
    public String sayHello() {
        return "Hello from mini-bloxx!";
    }
}
