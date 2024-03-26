package com.example.springreactivewebflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

class SpringReactiveWebfluxApplicationTests extends BaseTest {
    @Autowired
    private WebClient webClient;
}
