package zw.co.econet.humanresources.service.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.econet.humanresources.business.logic.api.EmployeeService;
import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;
import zw.co.econet.humanresources.utils.messages.external.EmployeeResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping(path = "employees")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody
            final EmployeeDto employeeDto) {
        EmployeeResponse response = employeeService.save(employeeDto);
        if (response.getStatusCode() != 201) {
            return ResponseEntity.badRequest().body(response);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getData().getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

}