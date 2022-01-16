package com.kycni.community.controller;

import com.kycni.community.dto.GithubUser;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;


@Controller
public class AuthController {
    /**
     * @param response 携带code获取跳转地址
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
     * @param callback 携带code并验证code ok登录
     * @return 返回首页
     */
    @RequestMapping("/callback/github")
    public Object loginCallback(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        // 根据返回的参数，执行登录请求（获取用户信息）
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        GithubUser githubUser = new GithubUser();
        githubUser.setName(authResponse.getData().getUsername());
        githubUser.setRemark(authResponse.getData().getRemark());
        // 打印授权返回代码（2000表示成功，可以用来判断用户登录成功与否）
        //打印用户的昵称、ID、头像等基本信息
        System.out.println("用户的UnionID：" + authResponse.getData().getUuid());
        System.out.println("用户的用户名："+authResponse.getData().getUsername());
        System.out.println("用户的昵称：" + authResponse.getData().getNickname());
        System.out.println("用户的头像：" + authResponse.getData().getAvatar());

        //打印用户的Token中的信息
        System.out.println("access_token：" + authResponse.getData().getToken().getAccessToken());

        return "index";
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
