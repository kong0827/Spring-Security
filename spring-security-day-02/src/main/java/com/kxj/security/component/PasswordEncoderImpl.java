package com.kxj.security.component;

import com.kxj.security.config.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2020/6/1 22:24
 * @desc
 */
@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        String digest = MD5Util.digest((String) rawPassword);
        return digest;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println(rawPassword);
        System.out.println(encodedPassword);
        boolean equals = encodedPassword.equals(MD5Util.digest((String) rawPassword));
        return equals;
    }
}
