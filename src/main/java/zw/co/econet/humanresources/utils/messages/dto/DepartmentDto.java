package zw.co.econet.humanresources.utils.messages.dto;

import zw.co.econet.humanresources.utils.enums.EntityStatus;

public class DepartmentDto {
    private Long id;
    private String name;
    private EntityStatus status;

    public DepartmentDto() {
    }

    private DepartmentDto(Long id, String name, EntityStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
