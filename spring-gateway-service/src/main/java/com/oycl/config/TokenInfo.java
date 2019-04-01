package com.oycl.config;

import com.google.gson.Gson;
import com.oycl.common.UserInfo;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TokenInfo {

    private static Gson GSON = new Gson();

    private List<String> authorities;

    private String userName;

    private Date expiration;

    private String id;

    private String subject;

    public TokenInfo(Claims claims){
        if (claims != null) {
            UserInfo userInfo = GSON.fromJson(claims.getSubject(), UserInfo.class);
            this.userName = userInfo.getName();
            this.expiration = claims.getExpiration();
            this.id = claims.getId();
            this.subject = claims.getSubject();
            this.authorities = CollectionUtils.isEmpty(userInfo.getRole())?null:userInfo.getRole().stream().map(t->t.getRoleId()).collect(Collectors.toList());
        }
    }


    public String getSubject() {
        return subject;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public String getUserName() {
        return userName;
    }


    public Date getExpiration() {
        return expiration;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TokenInfo{");
        sb.append("authorities=").append(authorities);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", expiration=").append(expiration);
        sb.append(", id='").append(id).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
