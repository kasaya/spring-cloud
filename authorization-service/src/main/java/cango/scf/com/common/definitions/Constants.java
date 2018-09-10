package cango.scf.com.common.definitions;

public class Constants {
    /**返回code*/
    public static class ResultCode{
        /** 成功 */
        public static final String SUCCESS = "200";
        /** 需要客户确认的成功数据 */
        public static final String SUCCESS_CONFIRM = "210";
        /** 登录异常（用户角色验证，token认证） */
        public static final String LOGING_ERR = "202";
        /** 权限异常 （访问url权限）*/
        public static final String AUTH_ERR = "203";
        /** 业务异常（业务抛出异常） */
        public static final String BUSS_ERR = "204";
        /** 系统异常 （响应超时等）*/
        public static final String SYS_ERR = "205";
        /** 查询次数异常 （响应超时等）*/
        public static final String TIMES_ERR = "206";
        /** 参数异常  */
        public static final String PARAM_ERR = "209";
    }

    /**
     * 默认用户ID
     */
    public static final String DEFAULT_USER_ID = "1";

    /**
     * 授权token
     */
    public static final String REDIS_KEY_HEAD_TOKEN = "scf:authorization:token";
}
