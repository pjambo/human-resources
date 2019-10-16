package zw.co.econet.humanresources.business.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.domain.Department;
import zw.co.econet.humanresources.repository.DepartmentRepository;
import zw.co.econet.humanresources.utils.enums.EntityStatus;
import zw.co.econet.humanresources.utils.mapper.DepartmentMapper;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.external.DepartmentListResponse;
import zw.co.econet.humanresources.utils.messages.external.DepartmentResponse;

import java.util.List;
import java.util.Optional;

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
    public DepartmentResponse save(DepartmentDto departmentDto) {
        log.info("Incoming request to create department: {}", departmentDto);
        DepartmentResponse departmentResponse = new DepartmentResponse();
        if(departmentDto == null || departmentDto.getName() == null || departmentDto.getName().trim().equals("")) {
            departmentResponse.setStatusCode(400);
            departmentResponse.setMessage("Invalid Department creation request.");
            return departmentResponse;
        }

        Department departmentMapped = departmentMapper.map(departmentDto);
        Department newDepartment = departmentRepository.save(departmentMapped);
        DepartmentDto newDepartmentDto = departmentMapper.map(newDepartment);
        departmentResponse.setStatusCode(201);
        departmentResponse.setMessage("Department created successfully");
        departmentResponse.setSuccess(true);
        departmentResponse.setData(newDepartmentDto);
        log.info("Outgoing response of  department creation: {}", departmentResponse);
        return departmentResponse;
    }


    @Override
    public DepartmentResponse findById(Long id) {
        log.info("Incoming request to find department by ID: {}", id);

        DepartmentResponse departmentResponse = new DepartmentResponse();
        if (id == null) {
            departmentResponse.setStatusCode(400);
            departmentResponse.setMessage("Invalid Department search request.");
            return departmentResponse;
        }

        Optional<Department> departmentSearched = departmentRepository.findById(id);
        if (!departmentSearched.isPresent()){
            departmentResponse.setStatusCode(404);
            departmentResponse.setMessage("Department not found.");
            return departmentResponse;
        }

        DepartmentDto departmentDto = departmentMapper.map(departmentSearched.get());
        departmentResponse.setStatusCode(200);
        departmentResponse.setMessage("Department retrieved successfully");
        departmentResponse.setSuccess(true);
        departmentResponse.setData(departmentDto);

        log.info("Outgoing response of find department by ID: {}", departmentResponse);

        return departmentResponse;
    }

    @Override
    public DepartmentListResponse findAll() {
        log.info("Incoming request to find all departments");

        DepartmentListResponse departmentListResponse = new DepartmentListResponse();

        List<Department> departmentsSearched = departmentRepository.findAll();
        if (departmentsSearched.isEmpty()){
            departmentListResponse.setStatusCode(404);
            departmentListResponse.setMessage("Departments not found.");
            return departmentListResponse;
        }

        List<DepartmentDto> departmentDtos = departmentMapper.map(departmentsSearched);
        departmentListResponse.setStatusCode(200);
        departmentListResponse.setMessage("Departments retrieved successfully");
        departmentListResponse.setSuccess(true);
        departmentListResponse.setData(departmentDtos);

        log.info("Outgoing response of find all departments: {} Departments", departmentListResponse.getData().size());

        return departmentListResponse;
    }


    @Override
    public DepartmentResponse update(Long id, DepartmentDto departmentDto) {
        DepartmentResponse departmentResponse = new DepartmentResponse();

        if(departmentDto == null || departmentDto.getName() == null) {
            departmentResponse.setStatusCode(400);
            departmentResponse.setMessage("Invalid Department update request.");
            return departmentResponse;
        }
        Optional<Department> fetchedDepartment = departmentRepository
                .findByIdAndStatusNot(id, EntityStatus.DELETED);
        if (!fetchedDepartment.isPresent()) {
            departmentResponse.setStatusCode(400);
            departmentResponse.setMessage("Department not found.");
            return departmentResponse;
        }

        Department departmentToBeUpdated = fetchedDepartment.get();
        departmentToBeUpdated.setName(departmentDto.getName());

        Department updatedDepartment = departmentRepository.save(departmentToBeUpdated);
        DepartmentDto updatedDepartmentDto = departmentMapper.map(updatedDepartment);
        departmentResponse.setData(updatedDepartmentDto);
        departmentResponse.setStatusCode(200);
        departmentResponse.setMessage("Department updated successfully");
        departmentResponse.setSuccess(true);
        log.info("Outgoing response of  department update: {}", departmentResponse);
        return departmentResponse;
    }

    @Override
    public DepartmentResponse delete(Long id) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        if (id == null){
            departmentResponse.setStatusCode(400);
            departmentResponse.setMessage("Invalid Department ID.");
            return departmentResponse;
        }

        Optional<Department> fetchedDepartment = departmentRepository.findById(id);
        if (!fetchedDepartment.isPresent()) {
            departmentResponse.setStatusCode(400);
            departmentResponse.setMessage("Department not found.");
            return departmentResponse;
        }

        Department departmentToBeDeleted = fetchedDepartment.get();
        departmentToBeDeleted.setStatus(EntityStatus.DELETED);

        Department deletedDepartment = departmentRepository.save(departmentToBeDeleted);
        DepartmentDto deletedDepartmentDto = departmentMapper.map(deletedDepartment);
        departmentResponse.setData(deletedDepartmentDto);
        departmentResponse.setStatusCode(200);
        departmentResponse.setMessage("Department deleted successfully");
        departmentResponse.setSuccess(true);
        log.info("Outgoing response of  department delete: {}", departmentResponse);
        return departmentResponse;
    }
}
