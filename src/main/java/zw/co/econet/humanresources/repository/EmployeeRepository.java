package zw.co.econet.humanresources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.econet.humanresources.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee save(Employee employee);
}
