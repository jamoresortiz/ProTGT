package com.joandma.protgt.Models.ModelsApiSMSPubli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamoresmacbook on 4/3/18.
 */

public class SmsResponse {
    private String status;
    private List<Result> result;
    private String error_id;
    private String error_msg;

    public SmsResponse() {
        this.result = new ArrayList<>();
    }

    public SmsResponse(String status, List<Result> result, String error_id, String error_msg) {
        this.status = status;
        this.result = result;
        this.error_id = error_id;
        this.error_msg = error_msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getError_id() {
        return error_id;
    }

    public void setError_id(String error_id) {
        this.error_id = error_id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsResponse that = (SmsResponse) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (error_id != null ? !error_id.equals(that.error_id) : that.error_id != null)
            return false;
        return error_msg != null ? error_msg.equals(that.error_msg) : that.error_msg == null;
    }

    @Override
    public int hashCode() {
        int result1 = status != null ? status.hashCode() : 0;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (error_id != null ? error_id.hashCode() : 0);
        result1 = 31 * result1 + (error_msg != null ? error_msg.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "SmsResponse{" +
                "status='" + status + '\'' +
                ", result=" + result +
                ", error_id='" + error_id + '\'' +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
