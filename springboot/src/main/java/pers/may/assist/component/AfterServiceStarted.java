package pers.may.assist.component;


import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pers.may.assist.pojo.Constant;
import pers.may.assist.utils.MyTools;

import javax.annotation.Resource;

@Component
public class AfterServiceStarted implements ApplicationRunner {

    @Resource
    Constant constant;

    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        MyTools my = new MyTools();
        my.resetToken(constant);

    }
}
