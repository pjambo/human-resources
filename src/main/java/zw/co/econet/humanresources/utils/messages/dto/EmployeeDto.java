package zw.co.econet.humanresources.utils.messages.dto;

import zw.co.econet.humanresources.utils.enums.EntityStatus;

import javax.validation.constraints.NotBlank;

public class EmployeeDto {
    private Long id;
    @NotBlank(message = "First Name is mandatory")
    private String firstName;
    @NotBlank(message = "Last Name is mandatory")
    private String lastName;
    @NotBlank(message = "Employee Number is mandatory")
    private String employeeNumber;
    @NotBlank(message = "National ID is mandatory")
    private String nationalID;
    private DepartmentDto department;
    private EntityStatus status;

    public EmployeeDto() {
    }

    private EmployeeDto(Long id,
                        String firstName,
                        String lastName,
                        String employeeNumber,
                        String nationalID,
                        DepartmentDto department,
                        EntityStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;
        this.nationalID = nationalID;
        this.department = department;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", nationalID='" + nationalID + '\'' +
                ", department=" + department +
                ", status=" + status +
                '}';
    }
}
