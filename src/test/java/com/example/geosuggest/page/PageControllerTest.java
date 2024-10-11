package com.example.geosuggest.page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PageController.class)
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // No need for initialization, mockMvc will be autowired and ready for use
    }

    @Test
    public void testDemoPage_ReturnsDemoView() throws Exception {
        // Perform GET request to the root URL and expect the "demo" view
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())    // Expect HTTP 200 (OK)
                .andExpect(view().name("demo")); // Expect the view name to be "demo"
    }
}
