package zw.co.econet.humanresources.business.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.business.logic.impl.DepartmentServiceImpl;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.utils.mapper.DepartmentMapper;

@Configuration
public class BusinessConfig {
    @Bean
    public DepartmentMapper departmentMapper(){return Mappers.getMapper(DepartmentMapper.class);}

    @Bean
    public DepartmentService departmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper){
        return new DepartmentServiceImpl(departmentRepository, departmentMapper);
    }
}
