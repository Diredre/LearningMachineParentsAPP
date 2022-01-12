package com.example.learningmachineparentsapp.Homepage.Message;

public class MessagePayGson {
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
        private int code;
        private String msg;
        private ExtendDTO extend;

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

        public ExtendDTO getExtend() {
            return extend;
        }

        public void setExtend(ExtendDTO extend) {
            this.extend = extend;
        }

        public static class ExtendDTO {
        }
    }
}
