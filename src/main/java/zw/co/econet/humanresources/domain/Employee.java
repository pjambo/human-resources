package zw.co.econet.humanresources.domain;

import zw.co.econet.humanresources.utils.enums.EntityStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hrm_employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String employeeNumber;
    private String nationalID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;

    public Employee() {
    }

    private Employee(Long id,
                     String firstName,
                     String lastName,
                     String employeeNumber,
                     String nationalID,
                     Department department,
                     EntityStatus status,
                     LocalDateTime dateCreated,
                     LocalDateTime dateLastModified) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;
        this.nationalID = nationalID;
        this.department = department;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateLastModified = dateLastModified;
    }

    public static Employee newEmployee(String firstName,
                                       String lastName,
                                       String employeeNumber,
                                       String nationalID,
                                       Department department) {
        return new Builder()
                .firstName(firstName)
                .lastName(lastName)
                .employeeNumber(employeeNumber)
                .nationalID(nationalID)
                .department(department)
                .build();
    }

    @PrePersist
    public void init() {
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
        status = EntityStatus.ACTIVE;
    }

    @PreUpdate
    public void update() {
        dateLastModified = LocalDateTime.now();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", nationalID='" + nationalID + '\'' +
                ", department=" + department +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String employeeNumber;
        private String nationalID;
        private Department department;
        private EntityStatus status;
        private LocalDateTime dateCreated;
        private LocalDateTime dateLastModified;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder employeeNumber(String employeeNumber) {
            this.employeeNumber = employeeNumber;
            return this;
        }

        public Builder nationalID(String nationalID) {
            this.nationalID = nationalID;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder status(EntityStatus status) {
            this.status = status;
            return this;
        }

        public Builder dateCreated(LocalDateTime dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder dateLastModified(LocalDateTime dateLastModified) {
            this.dateLastModified = dateLastModified;
            return this;
        }

        public Employee build() {
            return new Employee(id, firstName, lastName, employeeNumber, nationalID, department, status, dateCreated, dateLastModified);
        }
    }
}
