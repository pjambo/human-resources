package zw.co.econet.humanresources.business.logic.api;

import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.external.DepartmentListResponse;
import zw.co.econet.humanresources.utils.messages.external.DepartmentResponse;

public interface DepartmentService {
    DepartmentResponse save(DepartmentDto department);
    DepartmentResponse findById(Long id);
    DepartmentListResponse findAll();
    DepartmentResponse update(Long id, DepartmentDto department);
    DepartmentResponse delete(Long id);
}