package cango.scf.com.constants;

/**返回code*/
    public class ResultCode {
        /** 成功 */
        public static final String SUCCESS = "200";
        /** 登录异常（用户角色验证，token认证） */
        public static final String LOGING_ERR = "202";
        /** 权限异常 （访问url权限）*/
        public static final String AUTH_ERR = "203";
        /** 业务异常（业务抛出异常） */
        public static final String BUSS_ERR = "204";
        /** 系统异常 （响应超时等）*/
        public static final String SYS_ERR = "205";
        /** 参数异常  */
        public static final String PARAM_ERR = "209";
    }