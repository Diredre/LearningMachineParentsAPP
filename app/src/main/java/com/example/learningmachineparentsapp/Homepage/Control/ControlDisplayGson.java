package com.example.learningmachineparentsapp.Homepage.Control;

import java.util.List;

public class ControlDisplayGson {
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
        private List<SurfingInfoDTO> surfingInfo;

        public List<SurfingInfoDTO> getSurfingInfo() {
            return surfingInfo;
        }

        public void setSurfingInfo(List<SurfingInfoDTO> surfingInfo) {
            this.surfingInfo = surfingInfo;
        }

        public static class SurfingInfoDTO {
            private int id;
            private int studentId;
            private int moduleId;
            private int controlTime;
            private int ifOpen;
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

            public int getModuleId() {
                return moduleId;
            }

            public void setModuleId(int moduleId) {
                this.moduleId = moduleId;
            }

            public int getControlTime() {
                return controlTime;
            }

            public void setControlTime(int controlTime) {
                this.controlTime = controlTime;
            }

            public int getIfOpen() {
                return ifOpen;
            }

            public void setIfOpen(int ifOpen) {
                this.ifOpen = ifOpen;
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
