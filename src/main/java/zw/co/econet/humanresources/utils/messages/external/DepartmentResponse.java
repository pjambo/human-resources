package zw.co.econet.humanresources.utils.messages.external;

import zw.co.econet.humanresources.utils.messages.dto.DepartmentDto;

public class DepartmentResponse {
    private int statusCode;
    private boolean success;
    private String message;
    private DepartmentDto data;

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

    public DepartmentDto getData() {
        return data;
    }

    public void setData(DepartmentDto department) {
        this.data = department;
    }

    @Override
    public String toString() {
        return "DepartmentResponse{" +
                "statusCode=" + statusCode +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
