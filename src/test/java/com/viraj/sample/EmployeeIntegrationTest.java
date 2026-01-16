package com.viraj.sample.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viraj.sample.SampleApplication;
import com.viraj.sample.entity.Employee;
import com.viraj.sample.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext; 
import org.springframework.test.context.TestPropertySource; 
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
    }

    @Test
    void testCreateAndRetrieveFlow() throws Exception {
        Employee emp = new Employee();
        emp.setName("Integration User");
        emp.setDept("Testing");

        mockMvc.perform(post("/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Integration User"));
    }
}