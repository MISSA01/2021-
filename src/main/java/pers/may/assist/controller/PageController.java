package pers.may.assist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * 测试模块
 * @author May
 *
 */
@Controller
@RequestMapping("/test")
public class PageController {

//    /**
//     * 测试文档生成
//     * @param testString 测试数据参数
//     * @return 返回测试参数
//     */
//    @RequestMapping(value = "/docs")
//    @ResponseBody
//    public String getIn(String testString) {
//        return "testRerunString";
//    }
//
//    /**
//     * 测试接口2
//     *  @description  测试文档生成的第二个接口
//     * @author May
//     * @param testUser 用户类型的测试数据参数2
//     * @return 返回用户类型的测试参数
//     */
//    @RequestMapping(value = "/docs2")
//    @ResponseBody
//    public User getIn(User testUser) {
//        User user = new User();
//        return user;
//    }

    /**
     * toChat
     * @description 转到聊天测试网页
     * @author May
     */
    @RequestMapping("/chat")
    public String toChat(){
        return "chat";
    }
}
