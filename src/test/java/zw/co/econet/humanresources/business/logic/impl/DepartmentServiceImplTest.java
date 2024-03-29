package zw.co.econet.humanresources.business.logic.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.utils.enums.EntityStatus;
import zw.co.econet.humanresources.utils.mapper.DepartmentMapper;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.external.DepartmentListResponse;
import zw.co.econet.humanresources.utils.messages.external.DepartmentResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceImplTest {
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    private DepartmentDto departmentDto;
    private Department department;
    private DepartmentResponse departmentResponse;
    private DepartmentListResponse departmentListResponse;

    @Before
    public void setup(){
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
    }


    @Test
    public void shouldFailCreateDepartmentIfRequestIsNull(){
        departmentDto = null;
        departmentResponse = departmentService.save(departmentDto);
        verify(departmentRepository, times(0)).save(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Invalid Department creation request.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldFailCreateDepartmentIfEmptyStringIsProvided(){
        departmentDto.setName(" ");
        departmentResponse = departmentService.save(departmentDto);
        verify(departmentRepository, times(0)).save(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Invalid Department creation request.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldFailCreateDepartmentIfNameIsNull(){
        departmentDto.setName(null);
        departmentResponse = departmentService.save(departmentDto);
        verify(departmentRepository, times(0)).save(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Invalid Department creation request.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldCreateDepartment(){
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.map(any(Department.class))).thenReturn(departmentDto);
        when(departmentMapper.map(any(DepartmentDto.class))).thenReturn(department);

        departmentResponse = departmentService.save(departmentDto);
        verify(departmentRepository, times(1)).save(any(Department.class));
        assertThat(departmentResponse.getData()).isEqualTo(departmentDto);
        assertThat(departmentResponse.getStatusCode()).isEqualTo(201);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department created successfully");
        assertThat(departmentResponse.isSuccess()).isTrue();
    }


    @Test
    public void shouldFailFindDepartmentByIdIfIdIsNull(){
        departmentResponse = departmentService.findById(null);
        verify(departmentRepository, times(0)).findById(anyLong());
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Invalid Department search request.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldReturnDepartmentNotFoundIfInvalidId(){
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        departmentResponse = departmentService.findById(2L);

        verify(departmentRepository, times(1)).findById(anyLong());
        assertThat(departmentResponse.getStatusCode()).isEqualTo(404);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department not found.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldReturnDepartment(){
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(departmentMapper.map(any(Department.class))).thenReturn(departmentDto);

        departmentResponse = departmentService.findById(1L);

        verify(departmentRepository, times(1)).findById(anyLong());
        assertThat(departmentResponse.getData()).isEqualTo(departmentDto);
        assertThat(departmentResponse.getStatusCode()).isEqualTo(200);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department retrieved successfully");
        assertThat(departmentResponse.isSuccess()).isTrue();
    }

    @Test
    public void shouldReturnDepartmentsNotFound(){
        when(departmentRepository.findAll()).thenReturn(new ArrayList<Department>());

        departmentListResponse = departmentService.findAll();

        verify(departmentRepository, times(1)).findAll();
        assertThat(departmentListResponse.getStatusCode()).isEqualTo(404);
        assertThat(departmentListResponse.getMessage()).isEqualTo("Departments not found.");
        assertThat(departmentListResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldReturnDepartments(){
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));
        when(departmentMapper.map(anyList())).thenReturn(Collections.singletonList(departmentDto));

        departmentListResponse = departmentService.findAll();

        verify(departmentRepository, times(1)).findAll();
        assertThat(departmentListResponse.getData().size()).isEqualTo(1);
        assertThat(departmentListResponse.getStatusCode()).isEqualTo(200);
        assertThat(departmentListResponse.getMessage()).isEqualTo("Departments retrieved successfully");
        assertThat(departmentListResponse.isSuccess()).isTrue();
    }


    @Test
    public void shouldFailUpdateDepartmentIfDepartmentIsNull(){
        departmentResponse = departmentService.update(1L, null);

        verify(departmentRepository, times(0)).save(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Invalid Department update request.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldFailUpdateDepartmentIfDepartmentNotFound(){
        when(departmentRepository.findByIdAndStatusNot(anyLong(), any(EntityStatus.class)))
                .thenReturn(Optional.empty());

        departmentResponse = departmentService.update(1L, departmentDto);

        verify(departmentRepository, times(1))
                .findByIdAndStatusNot(anyLong(), any(EntityStatus.class));
        verify(departmentRepository, times(0)).save(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department not found.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldUpdateDepartment(){
        when(departmentRepository.findByIdAndStatusNot(anyLong(), any(EntityStatus.class)))
                .thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.map(any(Department.class))).thenReturn(departmentDto);

        departmentResponse = departmentService.update(1L, departmentDto);

        verify(departmentRepository, times(1)).findByIdAndStatusNot(anyLong(), any(EntityStatus.class));
        verify(departmentRepository, times(1)).save(any(Department.class));
        verify(departmentMapper, times(1)).map(any(Department.class));
        assertThat(departmentResponse.getData()).isEqualTo(departmentDto);
        assertThat(departmentResponse.getStatusCode()).isEqualTo(200);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department updated successfully");
        assertThat(departmentResponse.isSuccess()).isTrue();
    }

    @Test
    public void shouldFailDeleteDepartmentIfIdIsNull(){
        departmentResponse = departmentService.delete(null);

        verify(departmentRepository, times(0)).delete(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Invalid Department ID.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldFailDeleteDepartmentIfDepartmentNotFound(){
        when(departmentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        departmentResponse = departmentService.delete(1L);

        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentRepository, times(0)).delete(any(Department.class));
        assertThat(departmentResponse.getStatusCode()).isEqualTo(400);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department not found.");
        assertThat(departmentResponse.isSuccess()).isFalse();
    }

    @Test
    public void shouldDeleteDepartment(){
        departmentDto.setStatus(EntityStatus.DELETED);
        when(departmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.map(any(Department.class))).thenReturn(departmentDto);

        departmentResponse = departmentService.delete(1L);

        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentRepository, times(1)).save(any(Department.class));
        verify(departmentMapper, times(1)).map(any(Department.class));
        assertThat(departmentResponse.getData()).isEqualTo(departmentDto);
        assertThat(departmentResponse.getData().getStatus()).isEqualTo(EntityStatus.DELETED);
        assertThat(departmentResponse.getStatusCode()).isEqualTo(200);
        assertThat(departmentResponse.getMessage()).isEqualTo("Department deleted successfully");
        assertThat(departmentResponse.isSuccess()).isTrue();
    }
}