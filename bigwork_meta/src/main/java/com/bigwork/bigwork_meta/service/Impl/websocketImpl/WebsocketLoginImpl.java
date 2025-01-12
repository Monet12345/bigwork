package com.bigwork.bigwork_meta.service.Impl.websocketImpl;

import cn.dev33.satoken.stp.StpUtil;
import com.bigwork.bigwork_meta.service.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebsocketLoginImpl implements WebsocketService {
    @Override
    public String getMessage() {
        if(StpUtil.isLogin()){
            return "登录成功";
        }
        else {
            return "登录失败";
        }
    }
}
