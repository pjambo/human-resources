package zw.co.econet.humanresources.business.api;

import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;
import zw.co.econet.humanresources.utils.messages.external.EmployeeResponse;

public interface EmployeeService {
    EmployeeResponse save(EmployeeDto employee);
}