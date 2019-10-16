package zw.co.econet.humanresources.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.utils.enums.EntityStatus;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department save(Department department);
    Optional<Department> findById(Long id);
    Optional<Department> findByIdAndStatusNot(Long id, EntityStatus status);
    List<Department> findAll();
}
