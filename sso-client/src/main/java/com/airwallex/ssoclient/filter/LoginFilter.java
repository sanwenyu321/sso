package com.airwallex.ssoclient.filter;

import com.airwallex.common.constant.AuthConst;
import com.airwallex.common.storage.SessionStorage;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private HttpClient client;

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        if (session.getAttribute(AuthConst.IS_LOGIN)!= null) {
            chain.doFilter(request, response);
            return;

        }

        String token = request.getParameter(AuthConst.TOKEN);
        //这里没有考虑跨域cookie共享，不用考虑前端，直接从cookie里获得token即可
        if(token != null) {
            session.setAttribute(AuthConst.IS_LOGIN, true);
            session.setAttribute(AuthConst.TOKEN, token);

            //（1）保存会话，用于从sso server发起的logout!
            SessionStorage.INSTANCE.set(token, session);
            chain.doFilter(req, res);
            return;
            // client 并没有校验用户返回token是否为合法的！！ TODO
//            HttpPost post = new HttpPost("http://localhost:8080/login/token/" + request.getParameter("token"));
//            logger.info("receive user's token");
//            HttpResponse httpResponse= client.execute(post);
//            HttpEntity entity = httpResponse.getEntity();
//            if (EntityUtils.toString(entity).equals("alreadyLogin")) {
//                logger.info("user's token is valide");
//                httpSession.setAttribute("isLogin", true);
//                chain.doFilter(request, response);
//            }
        }

        //（2）和昨天写的基本一样，只不过我是写死，这里利用了FilterConfig来实现变量的传递
        //告诉sso server要哪个服务要登录验证，同事当用户登录验证后，将重新指向最早要访问的地址
        response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "=" + request.getRequestURL());
    }

    @Override
    public void destroy() {

    }
}
