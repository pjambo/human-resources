package zw.co.econet.humanresources.service.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.econet.humanresources.business.logic.api.DepartmentService;
import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;
import zw.co.econet.humanresources.utils.messages.external.DepartmentListResponse;
import zw.co.econet.humanresources.utils.messages.external.DepartmentResponse;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@CrossOrigin
@Validated
@RequestMapping(path = "departments")
public class DepartmentResource {

    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody
            final DepartmentDto departmentDto) {
        DepartmentResponse response = departmentService.save(departmentDto);
        if (response.getStatusCode() != 201) {
           return ResponseEntity.badRequest().body(response);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getData().getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public DepartmentListResponse findAllDepartments() {
        return departmentService.findAll();
    }

    @GetMapping(value = "/{id}")
    public DepartmentResponse findDepartmentById(
            @PathVariable @Positive @Digits(integer = 19, fraction = 0)
            final Long id) {
        return departmentService.findById(id);
    }

    @PutMapping(value = "/{id}")
    public DepartmentResponse updateDepartment(
            @PathVariable @Positive @Digits(integer = 19, fraction = 0)
            final Long id,
            @RequestBody @Valid
            final DepartmentDto departmentDto) {
        return departmentService.update(id, departmentDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DepartmentResponse> deleteDepartment(
            @PathVariable @Positive @Digits(integer = 19, fraction = 0)
            final Long id) {
        DepartmentResponse response = departmentService.delete(id);
        if (response.getStatusCode() != 200) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
