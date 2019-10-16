package zw.co.econet.humanresources.domain;

import zw.co.econet.humanresources.utils.enums.EntityStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "hrm_departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private Set<Employee> employees;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;

    public Department() {
    }

    private Department(Long id, String name, Set<Employee> employees, EntityStatus status, LocalDateTime dateCreated, LocalDateTime dateLastModified) {
        this.id = id;
        this.name = name;
        this.employees = employees;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateLastModified = dateLastModified;
    }

    public static Department newDepartment(String name) {
        return new Builder()
                .name(name)
                .status(EntityStatus.ACTIVE)
                .dateCreated(LocalDateTime.now())
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
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
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Set<Employee> employees;
        private EntityStatus status;
        private LocalDateTime dateCreated;
        private LocalDateTime dateLastModified;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder employees(Set<Employee> employees) {
            this.employees = employees;
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

        public Department build() {
            return new Department(id, name, employees, status, dateCreated, dateLastModified);
        }
    }
}
