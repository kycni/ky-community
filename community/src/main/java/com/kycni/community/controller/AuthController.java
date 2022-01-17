package com.kycni.community.controller;

import com.kycni.community.mapper.UserMapper;
import com.kycni.community.model.User;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.UUID;


@Controller
public class AuthController {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * @param response 授权获取登录地址
     * @throws IOException Io异常
     */
    @RequestMapping("/login/github")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        System.out.println(authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * @param callback 验证code 执行登录请求，获取用户信息
     * @return 返回首页
     */
    @RequestMapping("/callback/github")
    public Object loginCallback(AuthCallback callback,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        AuthRequest authRequest = getAuthRequest();
        // 根据返回的参数，执行登录请求（获取用户信息）
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        
        // 打印授权返回代码（2000表示成功，可以用来判断用户登录成功与否）
        //打印用户的昵称、ID、头像等基本信息
        System.out.println("用户的UnionID：" + authResponse.getData().getUuid());
        System.out.println("用户的用户名："+authResponse.getData().getUsername());
        System.out.println("用户的昵称：" + authResponse.getData().getNickname());
        System.out.println("用户的头像：" + authResponse.getData().getAvatar());
        //打印用户的Token中的信息
        System.out.println("access_token：" + authResponse.getData().getToken().getAccessToken());
        
        /*
        保存用户信息与登录状态
        */
        if (authResponse.getData().getNickname() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(authResponse.getData().getNickname());
            user.setBio(authResponse.getData().getRemark());
            user.setAccountId(authResponse.getData().getUuid());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            
            //将token的值存入数据库
            userMapper.insert(user);
            // 登录成功，将token的值存入到cookie中,写cookie 和session
            request.getSession().setAttribute("user", user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }
        System.out.println("登录失败");
        return "redirect:/";
    }

    /**
     * @return 向github发送验证登录请求
     */
    private AuthRequest getAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId("589d815f2be959f41c6c")
                .clientSecret("efdabbe1127f00320b7644a7eb52a3caf19a0056")
                .redirectUri("http://localhost:7888/callback/github")
                // 针对国外平台配置代理
                .httpConfig(HttpConfig.builder()
                        // Http 请求超时时间
                        .timeout(15000)
                        // host 和 port 请修改为开发环境的参数
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)))
                        .build())
                .build());
    }
}
