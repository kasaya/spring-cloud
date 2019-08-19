package com.oycl.definition;

/**
 * 服务常量定义.
 */
public class Constants {

    public static final String  SUFFIX_XML = "xml";

    public enum status{
        /**审批中*/
        approving("1"),
        /**已完成*/
        finish("2");

        private String code;

        status(String code){
            this.code = code;
        }

        public String getcode(){
            return code;
        }
    }

    public class SearchAction {

        /**相同*/
        public static final String  Equals = "1";
        /**不同*/
        public static final String  NotEquals = "2";

        public static final String  like = "3";

    }

}
