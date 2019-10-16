package zw.co.econet.humanresources.service.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.business.logic.api.EmployeeService;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.domain.Employee;
import zw.co.econet.humanresources.repository.EmployeeRepository;
import zw.co.econet.humanresources.utils.enums.EntityStatus;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;
import zw.co.econet.humanresources.utils.messages.external.EmployeeResponse;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class EmployeeResourceTest {
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private EmployeeResource employeeResource;
    @Autowired
    private MockMvc mockMvc;

    private Employee employee;
    private EmployeeDto employeeDto;
    private Department department;
    private DepartmentDto departmentDto;
    private EmployeeResponse employeeResponse;

    @Before
    public void setup(){
        department = new Department.Builder()
                .id(1L)
                .name("Accounting")
                .status(EntityStatus.ACTIVE)
                .build();
        departmentDto = new DepartmentDto.Builder()
                .id(1L)
                .name("Accounting")
                .status(EntityStatus.ACTIVE)
                .build();
        employee = new Employee.Builder()
                .firstName("Prosper")
                .lastName("Jambo")
                .employeeNumber("1234")
                .nationalID("632343583F48")
                .id(1L)
                .department(department)
                .build();
        employeeDto = new EmployeeDto.Builder()
                .firstName("Prosper")
                .lastName("Jambo")
                .employeeNumber("1234")
                .nationalID("632343583F48")
                .id(1L)
                .department(departmentDto)
                .build();
        employeeResponse = new EmployeeResponse();
        employeeResponse.setMessage("");
        employeeResponse.setStatusCode(200);
        employeeResponse.setSuccess(true);
        employeeResponse.setData(employeeDto);
    }

    @Test
    public void shouldReturnCorrectResponseWhenPostRequestToDepartmentsAndDepartmentValid() throws Exception {
        employeeResponse.setStatusCode(201);
        when(employeeService.save(any(EmployeeDto.class))).thenReturn(employeeResponse);
        MediaType applicationJsonUtf8 = new MediaType(MediaType.APPLICATION_JSON_UTF8, Charset.forName("UTF-8"));
        String user = "{" + "\"firstName\":\"Prosper\"," +
                "\"lastName\":\"Jambo\"," +
                "\"employeeNumber\":\"1234\"," +
                "\"nationalID\":\"632343583F48\"," +
                "\"department\":{" +
                "            \"id\": 3" +
                "        }" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(EmployeeResource.class))
                .andExpect(handler().methodName("createEmployee"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(applicationJsonUtf8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenPostRequestToEmployeesAndDepartmentInvalid() throws Exception {
        String department = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .content(department)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(EmployeeResource.class))
                .andExpect(handler().methodName("createEmployee"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasItem("firstName: First Name is mandatory")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}