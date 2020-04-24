package com.golink.ecommerceb2b.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewData {

    @SerializedName("error")
    private boolean error;
    @SerializedName("data")
    private List<Data> data;
    @SerializedName("message")
    private String message;

    public NewData(boolean error, List<Data> data, String message) {
        this.error = error;
        this.data = data;
        this.message = message;
    }

    public Boolean isError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
