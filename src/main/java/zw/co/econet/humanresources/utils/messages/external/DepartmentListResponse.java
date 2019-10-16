package zw.co.econet.humanresources.utils.messages.external;

import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;

import java.util.List;

public class DepartmentListResponse {
    private int statusCode;
    private boolean success;
    private String message;
    private List<DepartmentDto> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DepartmentDto> getData() {
        return data;
    }

    public void setData(List<DepartmentDto> departments) {
        this.data = departments;
    }
}
