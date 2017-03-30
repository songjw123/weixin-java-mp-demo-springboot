package com.github.binarywang.demo.wechat;

import com.github.binarywang.demo.wechat.websocket.WebSocketServer1;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.xnio.Options;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Binary Wang
 */
@SpringBootApplication

@ComponentScan
@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class})
@EnableWebMvc
public class WechatMpDemoApplication extends SpringBootServletInitializer {

    private static int cpuCount = 1;

    public static ExecutorService EXECUTOR = new ThreadPoolExecutor(
            cpuCount * 2, cpuCount * 25, 200, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(cpuCount * 100),
            new ThreadPoolExecutor.CallerRunsPolicy());

//    public static void main(String[] args) {
//        SpringApplication.run(WechatMpDemoApplication.class, args);
//    }

    public static void main(String[] args) throws Exception {
        WebSocketServer1.start();
//        new SpringApplicationBuilder(WechatMpDemoApplication.class).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WechatMpDemoApplication.class);
    }

    @Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

            @Override
            public void customize(Undertow.Builder builder) {
                builder.setBufferSize(1024 * 16)
                        .setIoThreads(Runtime.getRuntime().availableProcessors() * 2000)
                        .setSocketOption(Options.BACKLOG, 10000)
                        .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
                        .setServerOption(UndertowOptions.ALWAYS_SET_DATE, true)
                        .setWorkerThreads(20000);
            }

        });
        return factory;
    }
}
