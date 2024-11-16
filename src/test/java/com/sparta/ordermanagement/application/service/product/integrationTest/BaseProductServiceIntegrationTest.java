package com.sparta.ordermanagement.application.service.product.integrationTest;

import com.sparta.ordermanagement.application.output.ProductOutputPort;
import com.sparta.ordermanagement.application.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class BaseProductServiceIntegrationTest {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductOutputPort productOutputPort;

    @BeforeEach
    void setUp() {

    }
}
