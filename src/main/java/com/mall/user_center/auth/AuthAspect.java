package com.mall.user_center.auth;

import com.mall.user_center.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthAspect {
    private final JwtOperator jwtOperator;

    @Around("@annotation(com.mall.user_center.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

            checkToken();
            return proceedingJoinPoint.proceed();



    }

    private void checkToken() {
        try {
            HttpServletRequest request = getHttpServletRequest();

            String token = request.getHeader("X-token");
    //        检查是否合法
            Boolean isValid = jwtOperator.validateToken(token);
            if(!isValid){
                throw new SecurityException("token不合法");
            }
    //        校验成功后，写点东西到request的attribute中

            Claims claims = jwtOperator.getClaimsFromToken(token);
//            request.setAttribute("id",claims.get("id"));
//            request.setAttribute("role",claims.get("role"));
//            request.setAttribute("wxNickName",claims.get("wxNickName"));
        } catch (Throwable throwable) {
            throw new SecurityException("token 不合法");
        }

    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    @Around("@annotation(com.mall.user_center.auth.CheckAuthorization)")
    public Object checkAuthorization(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
    //        1,验证token
            this.checkToken();
            HttpServletRequest request = getHttpServletRequest();
            String token = request.getHeader("X-token");
            Claims claimsFromToken = this.jwtOperator.getClaimsFromToken(token);
            String roleFromToken = (String) claimsFromToken.get("role");
            //        2,验证用户角色是否匹配
//            HttpServletRequest request = getHttpServletRequest();
//            String role = (String) request.getAttribute("role");
    //        为什么是methodsignature呢
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    //        获得添加注解的一些方法的定义
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
    //        获得注解的值
            String value = annotation.value();

    //        做判断
            if(!Objects.equals(roleFromToken,value)){
                throw new SecurityException("用户无权访问 in checkAuthorization");
            }

        } catch (Throwable throwable) {
            throw new SecurityException("用户无权访问 in checkAuthorization try catch");
        }
        return proceedingJoinPoint.proceed();
    }
}
