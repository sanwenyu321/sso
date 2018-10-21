package com.airwallex.ssoserver.controller;


import com.airwallex.common.constant.AuthConst;
import com.airwallex.ssoserver.storage.ClientStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    /**
     * 用来返回给用户登录的界面
     */

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {model.addAttribute(AuthConst.CLIENT_URL, request.getParameter(AuthConst.CLIENT_URL));

        if (request.getSession().getAttribute(AuthConst.IS_LOGIN) != null) {
            String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
            if (clientUrl != null && !"".equals(clientUrl)) {
                String token = (String) request.getSession().getAttribute(AuthConst.TOKEN);
                ClientStorage.INSTANCE.set(token, clientUrl);
                return "redirect:" + clientUrl + "?" + AuthConst.TOKEN + "=" + token;
            }
        }

        return "index";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    @RequestMapping("/loginError")
    public String loginError() {
        return "loginError";

    }
}
