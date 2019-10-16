package zw.co.econet.humanresources.utils.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import zw.co.econet.humanresources.domain.Employee;
import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee map(EmployeeDto employeeDto);
    EmployeeDto map(Employee employee);
    @IterableMapping(qualifiedByName = "mapWithoutDepartment")
    List<EmployeeDto> map(List<Employee> employees);
    @IterableMapping(qualifiedByName = "mapWithoutDepartment")
    Set<EmployeeDto> map(Set<Employee> employees);

    @Named("mapWithoutDepartment")
    @Mapping(target = "department", ignore = true)
    EmployeeDto mapWithoutDepartment(Employee employee);
}
