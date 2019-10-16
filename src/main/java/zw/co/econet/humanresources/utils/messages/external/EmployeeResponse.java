package zw.co.econet.humanresources.utils.messages.external;


import zw.co.econet.humanresources.utils.messages.dto.EmployeeDto;

public class EmployeeResponse {
    private int statusCode;
    private boolean success;
    private String message;
    private EmployeeDto data;

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

    public EmployeeDto getData() {
        return data;
    }

    public void setData(EmployeeDto employee) {
        this.data = employee;
    }

    @Override
    public String toString() {
        return "EmployeeResponse{" +
                "statusCode=" + statusCode +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
