package com.mall.user_center.sentinelTest;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
/**
 * 处理sentinel 对spring Mvc的保护
 */
public class MyUrlBlockHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, BlockException ex) throws Exception {
        MyErrorMsg msg = null;
        if (ex instanceof FlowException) {
            msg = MyErrorMsg.builder()
                    .massage("限流了")
                    .status("100")
                    .build();
        } else if (ex instanceof DegradeException) {
            msg = MyErrorMsg.builder()
                    .massage("降级了")
                    .status("100")
                    .build();
        } else if (ex instanceof ParamFlowException) {
            msg = MyErrorMsg.builder()
                    .massage("热点参数限流")
                    .status("100")
                    .build();
        } else if (ex instanceof SystemBlockException) {
            msg = MyErrorMsg.builder()
                    .massage("系统规则（负载/...不满足要求）")
                    .status("100")
                    .build();
        } else if (ex instanceof AuthorityException) {
            msg = MyErrorMsg.builder()
                    .massage("授权规则不通过")
                    .status("100")
                    .build();
        }
        // http状态码
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        // spring mvc自带的json操作工具，叫jackson
        new ObjectMapper()
                .writeValue(
                        response.getWriter(),
                        msg
                );

    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class MyErrorMsg {
    String massage;
    String status;
}
