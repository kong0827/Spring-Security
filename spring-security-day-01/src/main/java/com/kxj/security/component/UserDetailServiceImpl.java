package com.kxj.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiangjin.kong
 * @date 2020/5/28 23:28
 * @desc
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String querySQL = "SELECT * FROM `t_admin` WHERE loginacct=?";

        // 查询指定用户的信息
        Map<String, Object> map = jdbcTemplate.queryForMap(querySQL, username);

        // 将查询到的用户信息封装到UserDetailService中
        User user = new User(map.get("loginacct").toString(), map.get("userpswd").toString(), AuthorityUtils.createAuthorityList("学徒"));

        return user;
    }
}
