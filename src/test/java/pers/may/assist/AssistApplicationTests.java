package pers.may.assist;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.may.assist.mapper.ChatMapper;
import pers.may.assist.mapper.UserMapper;
import pers.may.assist.pojo.Constant;
import pers.may.assist.pojo.User;
import pers.may.assist.service.ChatService;
import pers.may.assist.service.EntranceService;
import pers.may.assist.service.OtherService;
import pers.may.assist.service.ProfileService;
import pers.may.assist.utils.MyTools;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssistApplicationTests {

    @Resource
    UserMapper userMapper;


    @Resource
    ChatMapper chatMapper;

    @Resource
    ChatService chatService;

    @Resource
    Constant constant;

    @Resource
    EntranceService entranceService;

    @Resource
    ProfileService profileService;

    @Resource
    OtherService otherService;

    @Test
    void contextLoads() {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("E:\\tools\\IT_Tools\\Kotlin\\IdeaProjects\\assist"); // 项目根目录
        config.setProjectName("assist"); // 项目名称
        config.setApiVersion("V1.0");       // 声明该API的版本
        config.setDocsPath("E:\\tools\\IT_Tools\\Kotlin\\IdeaProjects\\assist\\docs"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
        config.addPlugin(new MarkdownDocPlugin());
        Docs.buildHtmlDocs(config);

    }

}
