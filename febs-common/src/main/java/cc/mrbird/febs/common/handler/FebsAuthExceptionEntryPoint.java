package cc.mrbird.febs.common.handler;

import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.utils.FebsUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FebsAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        FebsResponse febsResponse = new FebsResponse();
        /*
        httpServletResponse.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getOutputStream().write(JSONObject.toJSONString(febsResponse.message("无效的token")).getBytes()); */
        FebsUtil.makeResponse(httpServletResponse, MediaType.APPLICATION_JSON_UTF8_VALUE, HttpServletResponse.SC_UNAUTHORIZED, febsResponse.message("无效的token"));
    }
}
