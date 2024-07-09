package com.apple.shop.Member;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    public String displayName;
    public Long memberId;
    public CustomUser(
            String username,
            String password,
            Collection<?extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
