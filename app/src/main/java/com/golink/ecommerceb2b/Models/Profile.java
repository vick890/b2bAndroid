package com.golink.ecommerceb2b.Models;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("error")
    private boolean error;
    @SerializedName("data")
    private Data data;
    @SerializedName("message")
    private String message;

    public Profile(boolean error, Data data, String message) {
        this.error = error;
        this.data = data;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
