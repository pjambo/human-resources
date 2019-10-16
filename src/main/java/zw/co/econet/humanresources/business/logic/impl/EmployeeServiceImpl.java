package zw.co.econet.humanresources.business.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zw.co.econet.humanresources.business.logic.api.EmployeeService;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.repository.EmployeeRepository;
import zw.co.econet.humanresources.utils.mapper.EmployeeMapper;
import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;
import zw.co.econet.humanresources.utils.messages.external.EmployeeResponse;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;
    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class.getName());

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper,
            DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public EmployeeResponse save(EmployeeDto employee) {
        return null;
    }
}
