package com.devproject.miguelfagundez.capco.model.pojo;

/********************************************
 * Class- Error
 * Pojo class to handle error messages
 * (from server response and show on UI)
 * @author: Miguel Fagundez
 * @date: May 10th, 2020
 * @version: 1.0
 * *******************************************/
public class Error {

    private int status;
    private String message;

    public Error() {
    }

    public Error(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
