package pers.may.assist.config;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.may.assist.utils.Base64toImage;
import pers.may.assist.utils.MyTools;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    Base64toImage base64toImage = new Base64toImage();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置静态资源映射
         * 意思是：如果访问的资源路径是以“/images”开头的，
         * 就给我映射到本机的“E:/images/”这个文件夹内，去找你要的资源
         * 注意：E:/images/ 后面的 “/”一定要带上
         * 服务器的话一定要加file:
         */
        File file = base64toImage.getImgDirFile("");
        String filePath = file.getAbsolutePath();
//        System.out.println("图片存储路径为==========="+filePath);
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:"+ MyTools.fixPath(filePath));

        file = base64toImage.getDocsDirFile("");
        filePath = file.getAbsolutePath();
//        System.out.println("文档存储路径为==========="+filePath);
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("file:"+ MyTools.fixPath(filePath));

    }

    // 在某配置类中添加如下内容
    // 监听的http请求的端口,需要在application配置中添加http.port=端口号
    @Value("${http.port}")
    Integer httpPort;

    //正常启用的https端口 如443
    @Value("${server.port}")
    Integer httpsPort;

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(httpPort);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(httpsPort);
        return connector;
    }

}

