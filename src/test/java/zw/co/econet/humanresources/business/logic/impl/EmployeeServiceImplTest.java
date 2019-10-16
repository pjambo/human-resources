package zw.co.econet.humanresources.business.logic.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.business.logic.api.EmployeeService;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.domain.Employee;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.repository.EmployeeRepository;
import zw.co.econet.humanresources.utils.enums.EntityStatus;
import zw.co.econet.humanresources.utils.mapper.DepartmentMapper;
import zw.co.econet.humanresources.utils.mapper.EmployeeMapper;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;
import zw.co.econet.humanresources.utils.messages.external.EmployeeResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    private EmployeeService employeeService;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentMapper departmentMapper;
    @Mock
    private EmployeeMapper employeeMapper;
    private Employee employee;
    private EmployeeDto employeeDto;
    private Department department;
    private DepartmentDto departmentDto;
    private EmployeeResponse employeeResponse;


    @Before
    public void setup() {
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeMapper, departmentRepository);
        departmentService = new DepartmentServiceImpl(departmentRepository, departmentMapper);

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
    }

    @Test
    public void shouldFailCreateEmployeeIfRequestIsNull() {
        employeeDto = null;
        employeeResponse = employeeService.save(employeeDto);
        verify(employeeRepository, times(0)).save(any(Employee.class));
        assertThat(employeeResponse.getStatusCode()).isEqualTo(400);
        assertThat(employeeResponse.getMessage()).isEqualTo("Invalid Employee creation request.");
        assertThat(employeeResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldFailCreateEmployeeIfDepartmentIsNull() {
        employeeDto.setDepartment(null);
        employeeResponse = employeeService.save(employeeDto);
        verify(employeeRepository, times(0)).save(any(Employee.class));
        assertThat(employeeResponse.getStatusCode()).isEqualTo(400);
        assertThat(employeeResponse.getMessage()).isEqualTo("Invalid Employee creation request.");
        assertThat(employeeResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldFailCreateEmployeeIfDepartmentIsNotFound() {
        employeeDto.setDepartment(departmentDto);
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        employeeResponse = employeeService.save(employeeDto);
        verify(departmentRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(0)).save(any(Employee.class));
        assertThat(employeeResponse.getStatusCode()).isEqualTo(400);
        assertThat(employeeResponse.getMessage()).isEqualTo("Department not found.");
        assertThat(employeeResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldCreateDepartment() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.map(any(Employee.class))).thenReturn(employeeDto);
        when(employeeMapper.map(any(EmployeeDto.class))).thenReturn(employee);

        employeeResponse = employeeService.save(employeeDto);

        verify(departmentRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).map(any(Employee.class));
        verify(employeeMapper, times(1)).map(any(EmployeeDto.class));
        assertThat(employeeResponse.getData()).isEqualTo(employeeDto);
        assertThat(employeeResponse.getStatusCode()).isEqualTo(201);
        assertThat(employeeResponse.getMessage()).isEqualTo("Employee created successfully");
        assertThat(employeeResponse.isSuccess()).isTrue();
    }
}