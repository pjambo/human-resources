package zw.co.econet.humanresources.utils.mapper;

import org.mapstruct.Mapper;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department map(DepartmentDto departmentDto);
    DepartmentDto map(Department department);
    List<DepartmentDto> map(List<Department> departments);
}
