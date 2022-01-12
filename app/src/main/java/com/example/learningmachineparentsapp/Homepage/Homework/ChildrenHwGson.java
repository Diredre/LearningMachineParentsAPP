package com.example.learningmachineparentsapp.Homepage.Homework;

import java.util.List;

public class ChildrenHwGson {
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
        private List<ResultDTO> result;

        public List<ResultDTO> getResult() {
            return result;
        }

        public void setResult(List<ResultDTO> result) {
            this.result = result;
        }

        public static class ResultDTO {
            private int id;
            private int studentId;
            private String homeworkPic;
            private List<String> picList;
            private String picDate;
            private String createTime;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStudentId() {
                return studentId;
            }

            public void setStudentId(int studentId) {
                this.studentId = studentId;
            }

            public String getHomeworkPic() {
                return homeworkPic;
            }

            public void setHomeworkPic(String homeworkPic) {
                this.homeworkPic = homeworkPic;
            }

            public List<String> getPicList() {
                return picList;
            }

            public void setPicList(List<String> picList) {
                this.picList = picList;
            }

            public String getPicDate() {
                return picDate;
            }

            public void setPicDate(String picDate) {
                this.picDate = picDate;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
