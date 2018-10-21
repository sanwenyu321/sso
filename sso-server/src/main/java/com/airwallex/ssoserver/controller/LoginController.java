package com.airwallex.ssoserver.controller;



import com.airwallex.common.constant.AuthConst;
import com.airwallex.common.domain.User;
import com.airwallex.common.storage.SessionStorage;
import com.airwallex.ssoserver.service.UserService;
import com.airwallex.ssoserver.storage.ClientStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * spring boot 坑爹的是：要返回html，需要加入     compile('org.springframework.boot:spring-boot-starter-thymeleaf')
 * 这个依赖
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, User user, Model model) {
        //利用数据库判断用户登录
        user = userService.find(user);
        if (user == null) {
            model.addAttribute("error", "username or password is wrong");
            return "redirect:/loginError";
        }

        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(AuthConst.IS_LOGIN, true);
        request.getSession().setAttribute(AuthConst.TOKEN, token);
        SessionStorage.INSTANCE.set(token, request.getSession());

        String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
        if (clientUrl != null && !"".equals(clientUrl)) {
            ClientStorage.INSTANCE.set(token, clientUrl);

            return "redirect:" + clientUrl + "?" + AuthConst.TOKEN + "=" + token;
        }

        return "redirect:/";
    }


//    private Logger logger = LoggerFactory.getLogger(LoginController.class);
//    private Map map = new HashMap<String, Object>();
//    private static Map serverMap = new HashMap();
//    static {
//        serverMap.put("server1", "http://localhost:8081");
//    }
//    @RequestMapping("/login/{server}")
//    public String login(@PathVariable String server, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("isLogin") != null && (boolean)session.getAttribute("isLogin")) {
//            //TODO
//        }
//        logger.info("receive use's request with server: {}", server);
//        session.setAttribute("server", server);
//
//        return "login";
//    }
//
//    @RequestMapping("/login/token/{token}")
//    @ResponseBody
//    public String login(@PathVariable String token) {
//        logger.info("receive user token {}", token);
//        if (map.get(token) != null) {
//            return "alreadyLogin";
//        }
//        else {
//            return "";
//        }
//    }
//
//    @RequestMapping("/login")
//    public void login(String name, String pass, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        logger.info("receive request");
//        HttpSession session = request.getSession();
//        if (name.equals("sanwenyu") && pass.equals("123")) {
//            session.setAttribute("isLogin", true);
//            String token = UUID.randomUUID().toString();
//            map.put(token, name);
//            logger.info("user's password is valid, return to server:{} resource", serverMap.get(session.getAttribute("server")));
//            response.sendRedirect("http://localhost:8081?token="+token);
//        } else {
//            response.sendRedirect("login");
//        }
//    }


}
