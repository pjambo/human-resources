package zw.co.econet.humanresources.business.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.utils.mapper.DepartmentMapper;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.external.DepartmentResponse;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class.getName());

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentResponse save(DepartmentDto department) {
        return null;
    }

    @Override
    public DepartmentResponse findById(Long id) {
        return null;
    }

    @Override
    public DepartmentResponse findAll() {
        return null;
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentDto department) {
        return null;
    }

    @Override
    public DepartmentResponse delete(Long id) {
        return null;
    }
}
