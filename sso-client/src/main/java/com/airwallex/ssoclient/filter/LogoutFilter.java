package com.airwallex.ssoclient.filter;

import com.airwallex.common.constant.AuthConst;
import com.airwallex.common.storage.SessionStorage;
import com.airwallex.ssoclient.util.HttpUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogoutFilter implements Filter {
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String logoutUrl = config.getInitParameter(AuthConst.LOGOUT_URL);
        String token = (String) session.getAttribute(AuthConst.TOKEN);
        //主动注销
        if ("/logout".equals(request.getRequestURL())) {
            Map<String, String> params = new HashMap<>();
            params.put(AuthConst.LOGOUT_REQUEST, token);
            // 没有判断是否注销成功
            HttpUtil.post(logoutUrl, params);

            response.sendRedirect("/test");

            session = SessionStorage.INSTANCE.remove(token);
            // 这里为什么不可以利用从request获得的session注销
            if (session != null) {
                session.invalidate();
            }
            return;
        }

        //被动注销，即：sso server通知其他服务注销
        token = request.getParameter(AuthConst.TOKEN);
        if (token != null && "".equals(token)) {
            session = SessionStorage.INSTANCE.remove(token);
            if (session != null) {
                session.invalidate();
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
