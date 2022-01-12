package com.example.learningmachineparentsapp.LoginRegist;

import com.google.gson.annotations.SerializedName;

public class GetChildGson {
    private int code;
    private String msg;
    private DataDTO data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("1005")
        private String _$1005;
        @SerializedName("1006")
        private String _$1006;

        public String get_$1005() {
            return _$1005;
        }

        public void set_$1005(String _$1005) {
            this._$1005 = _$1005;
        }

        public String get_$1006() {
            return _$1006;
        }

        public void set_$1006(String _$1006) {
            this._$1006 = _$1006;
        }
    }
}
