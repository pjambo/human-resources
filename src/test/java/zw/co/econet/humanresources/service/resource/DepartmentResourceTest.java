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
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.utils.enums.EntityStatus;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.external.DepartmentListResponse;
import zw.co.econet.humanresources.utils.messages.external.DepartmentResponse;

import java.nio.charset.Charset;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class DepartmentResourceTest {
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private DepartmentResource departmentResource;
    @Autowired
    private MockMvc mockMvc;

    private DepartmentResponse departmentResponse;
    private DepartmentListResponse departmentListResponse;

    private DepartmentDto departmentDto;

    @Before
    public void setup(){
        departmentDto = new DepartmentDto.Builder()
                .id(1L)
                .name("Accounting")
                .status(EntityStatus.ACTIVE)
                .build();

        departmentResponse = new DepartmentResponse();
        departmentResponse.setMessage("");
        departmentResponse.setStatusCode(200);
        departmentResponse.setSuccess(true);
        departmentResponse.setData(departmentDto);

        departmentListResponse = new DepartmentListResponse();
        departmentListResponse.setMessage("");
        departmentListResponse.setStatusCode(200);
        departmentListResponse.setSuccess(true);
        departmentListResponse.setData(Collections.singletonList(departmentDto));
    }

    @Test
    public void shouldReturnCorrectResponseWhenPostRequestToDepartmentsAndDepartmentValid() throws Exception {
        departmentResponse.setStatusCode(201);
        when(departmentService.save(any(DepartmentDto.class))).thenReturn(departmentResponse);
        MediaType applicationJsonUtf8 = new MediaType(MediaType.APPLICATION_JSON_UTF8, Charset.forName("UTF-8"));
        String user = "{\"name\": \"Accounting\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("createDepartment"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(applicationJsonUtf8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenPostRequestToDepartmentsAndDepartmentInvalid() throws Exception {
        String department = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                .content(department)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("createDepartment"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasItem("name: Name is mandatory")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenGetRequestToDepartments() throws Exception {
        when(departmentService.findAll()).thenReturn(departmentListResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/departments")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("findAllDepartments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenGetRequestToDepartmentsAndIdValid() throws Exception {
        when(departmentService.findById(anyLong())).thenReturn(departmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("findDepartmentById"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenGetRequestToDepartmentsAndIdInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/c")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("findDepartmentById"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasItem("id should be of type java.lang.Long")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
    @Test
    public void shouldReturnCorrectResponseWhenGetRequestToDepartmentsAndIdNegative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/-1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("findDepartmentById"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.message", containsString("id: must be greater than 0")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenUpdateRequestToDepartmentsAndIdAndDepartmentValid() throws Exception {
        when(departmentService.update(anyLong(), any(DepartmentDto.class))).thenReturn(departmentResponse);
        MediaType applicationJsonUtf8 = new MediaType(MediaType.APPLICATION_JSON_UTF8, Charset.forName("UTF-8"));
        String user = "{\"name\": \"Accounting\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/departments/1")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("updateDepartment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(applicationJsonUtf8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenUpdateRequestToDepartmentsAndIdValid() throws Exception {
        departmentResponse.setStatusCode(200);
        when(departmentService.delete(anyLong())).thenReturn(departmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/departments/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("deleteDepartment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenDeleteRequestToDepartmentsAndIdValid() throws Exception {
        departmentResponse.setStatusCode(200);
        when(departmentService.delete(anyLong())).thenReturn(departmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/departments/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("deleteDepartment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenDeleteRequestToDepartmentsAndIdInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/departments/c")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("deleteDepartment"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasItem("id should be of type java.lang.Long")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnCorrectResponseWhenDeleteRequestToDepartmentsAndIdNegative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/departments/-1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(handler().handlerType(DepartmentResource.class))
                .andExpect(handler().methodName("deleteDepartment"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.message", containsString("id: must be greater than 0")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
