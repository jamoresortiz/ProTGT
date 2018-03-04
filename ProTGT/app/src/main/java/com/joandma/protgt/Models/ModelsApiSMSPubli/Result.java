package com.joandma.protgt.Models.ModelsApiSMSPubli;

/**
 * Created by jamoresmacbook on 4/3/18.
 */

public class Result {
    private String status;
    private String sms_id;
    private String custom;
    private String error_id;
    private String error_msg;

    public Result(String status, String sms_id, String custom, String error_id, String error_msg) {
        this.status = status;
        this.sms_id = sms_id;
        this.custom = custom;
        this.error_id = error_id;
        this.error_msg = error_msg;
    }

    public Result() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSms_id() {
        return sms_id;
    }

    public void setSms_id(String sms_id) {
        this.sms_id = sms_id;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
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

        Result result = (Result) o;

        if (status != null ? !status.equals(result.status) : result.status != null) return false;
        if (sms_id != null ? !sms_id.equals(result.sms_id) : result.sms_id != null) return false;
        if (custom != null ? !custom.equals(result.custom) : result.custom != null) return false;
        if (error_id != null ? !error_id.equals(result.error_id) : result.error_id != null)
            return false;
        return error_msg != null ? error_msg.equals(result.error_msg) : result.error_msg == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (sms_id != null ? sms_id.hashCode() : 0);
        result = 31 * result + (custom != null ? custom.hashCode() : 0);
        result = 31 * result + (error_id != null ? error_id.hashCode() : 0);
        result = 31 * result + (error_msg != null ? error_msg.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", sms_id='" + sms_id + '\'' +
                ", custom='" + custom + '\'' +
                ", error_id='" + error_id + '\'' +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
