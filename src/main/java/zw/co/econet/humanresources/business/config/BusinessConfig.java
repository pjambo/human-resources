package zw.co.econet.humanresources.business.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.business.logic.api.EmployeeService;
import zw.co.econet.humanresources.business.logic.impl.DepartmentServiceImpl;
import zw.co.econet.humanresources.business.logic.impl.EmployeeServiceImpl;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.repository.EmployeeRepository;
import zw.co.econet.humanresources.utils.mapper.DepartmentMapper;
import zw.co.econet.humanresources.utils.mapper.EmployeeMapper;

@Configuration
public class BusinessConfig {
    @Bean
    public DepartmentMapper departmentMapper(){return Mappers.getMapper(DepartmentMapper.class);}

    @Bean
    public EmployeeMapper employeeMapper(){return Mappers.getMapper(EmployeeMapper.class);}

    @Bean
    public DepartmentService departmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper){
        return new DepartmentServiceImpl(departmentRepository, departmentMapper);
    }

    @Bean
    public EmployeeService employeeService(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper,
            DepartmentRepository departmentRepository){
        return new EmployeeServiceImpl(employeeRepository, employeeMapper, departmentRepository);
    }
}

