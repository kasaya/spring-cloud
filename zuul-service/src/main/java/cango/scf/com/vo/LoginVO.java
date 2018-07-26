package cango.scf.com.vo;




/**
 * 登录信息
 *
 * @author cango
 */
public class LoginVO extends BaseVO {

    /**
     * 用户token
     */
    private String token;

    private ScfUser userInfo;

    public ScfUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ScfUser userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
