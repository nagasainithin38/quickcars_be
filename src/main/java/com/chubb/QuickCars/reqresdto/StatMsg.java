package com.chubb.QuickCars.reqresdto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


//@AllArgsConstructor
////@NoArgsConstructor
public class StatMsg {
    ReqStatus status;

    String message;

    public ReqStatus getStatus() {
        return status;
    }

    public void setStatus(ReqStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StatMsg(ReqStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
