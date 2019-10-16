package zw.co.econet.humanresources.business.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zw.co.econet.humanresources.business.logic.api.EmployeeService;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.domain.Employee;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.repository.EmployeeRepository;
import zw.co.econet.humanresources.utils.mapper.EmployeeMapper;
import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;
import zw.co.econet.humanresources.utils.messages.external.EmployeeResponse;

import java.util.Optional;

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
    public EmployeeResponse save(EmployeeDto employeeDto) {
        log.info("Incoming request to create employee: {}", employeeDto);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        if(employeeDto == null || employeeDto.getDepartment() == null) {
            employeeResponse.setStatusCode(400);
            employeeResponse.setMessage("Invalid Employee creation request.");
            log.warn("Invalid Employee creation request: {}", employeeResponse);
            return employeeResponse;
        }

        Optional<Department> departmentResponse = departmentRepository.findById(employeeDto.getDepartment().getId());
        if (!departmentResponse.isPresent()) {
            employeeResponse.setStatusCode(400);
            employeeResponse.setMessage("Department not found.");
            log.warn("Department not found: {}", employeeResponse);
            return employeeResponse;
        }

        Employee employeeMapped = employeeMapper.map(employeeDto);
        employeeMapped.setDepartment(departmentResponse.get());
        Employee newEmployee = employeeRepository.save(employeeMapped);
        EmployeeDto newEmployeeDto = employeeMapper.map(newEmployee);
        employeeResponse.setStatusCode(201);
        employeeResponse.setMessage("Employee created successfully");
        employeeResponse.setSuccess(true);
        employeeResponse.setData(newEmployeeDto);

        log.info("Outgoing response of  employee creation: {}", employeeResponse);

        return employeeResponse;
    }
}
