package com.viraj.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viraj.sample.entity.Employee;
import com.viraj.sample.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper; 

    @Test
    void testSaveEmployee() throws Exception {
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("Eddy");
        emp.setDept("IT");

        given(employeeService.saveEmployee(any(Employee.class))).willReturn(emp);

        mockMvc.perform(post("/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eddy"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        Employee e1 = new Employee(); e1.setName("Juan");
        Employee e2 = new Employee(); e2.setName("Maria");

        given(employeeService.getAllEmployees()).willReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/employee/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}