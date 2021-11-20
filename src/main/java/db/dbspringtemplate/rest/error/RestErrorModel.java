package db.dbspringtemplate.rest.error;

import java.util.Date;

public class RestErrorModel {

    private String errorMessage;
    private Date errorTime;

    public RestErrorModel(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorTime = new Date();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

}
