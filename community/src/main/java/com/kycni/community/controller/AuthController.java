package com.kycni.community.controller;

import com.kycni.community.mapper.UserMapper;
import com.kycni.community.model.User;
import com.kycni.community.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private UserService userService;

    /**
     * @param response 返回授权登录url
     * @method getAuthRequest() 获取登陆地址的方法
     * @throws IOException Io异常
     */
    @RequestMapping("/login/github")
    public void loginAuth(HttpServletResponse response) throws IOException {
        /*获得登陆地址请求*/
        AuthRequest authRequest = getAuthRequest();
        /*返回带state的授权登录url*/
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }

    /**
     * @param callback 回调地址，验证code 执行登录请求，获取用户信息
     * @return 返回首页
     */
    @RequestMapping("/callback/github")
    public Object loginCallback(AuthCallback callback,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        /*创建一个请求对象*/
        AuthRequest authRequest = getAuthRequest();
        /*
           调用请求对象的login方法
           根据接收的回调参数，执行登录请求
          （通过泛型，将类型转换为AuthUser获取用户信息）
           使用到泛型
        */
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        /*
            判断是否登录成功
            如果获取到了用户信息，则保存用户的登录信息
        */
        if (authResponse.getData().getNickname() != null) {
            /*创建一个用户*/
            User user = new User();
            /*UUID，唯一不可变标识符，保存唯一token，后面用来与cookie绑定*/
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(authResponse.getData().getNickname());
            user.setBio(authResponse.getData().getRemark());
            user.setAccountId(authResponse.getData().getUuid());
            user.setAvatarUrl(authResponse.getData().getAvatar());
            user.setSource(authResponse.getData().getSource());
            
            /*将创建和更新用户信息的方法封装在一起，减少代码的冗余*/
            userService.createOrUpdate(user);
            
            // 登录成功，将token的值存入到cookie中,写cookie 和session
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
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


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
