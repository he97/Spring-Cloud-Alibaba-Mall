package com.mall.user_center;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mall.user_center.dao.user_center.UserMapper;
import com.mall.user_center.domain.dto.Brand.BrandDTO;
import com.mall.user_center.domain.entity.user_center.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//@EnableNacosConfig()
//@NacosConfigurationProperties(dataId = "user-center-dev.yaml",autoRefreshed = true)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RefreshScope
public class TestContrlller {
    private final UserMapper userMapper;

    @GetMapping("/tset")
    public User insert() {
        User user = new User();
////        user.setUserId(String.valueOf(UUID.randomUUID()));
//        user.setUserName("holy");
//        user.setUserPassword("695");
//        user.setUserValid((byte) 1);
//        this.userMapper.insertSelective(user);
//        user.setUserName("shit");
        user = this.userMapper.selectByPrimaryKey("1");
        return user;
    }

    @Autowired
    private final TestService testService;

    @GetMapping("/test-a")
    public String testA() {
        this.testService.resource();
        return "test-a";
    }

    @GetMapping("/test-b")
    public String testB() {
        this.testService.resource();
        return "test-b";
    }

    /**
     * 热点规则
     *
     * @param a
     * @param b
     * @return
     */
    @GetMapping("/hot")
    @SentinelResource("hot")
    public String hot(
            @RequestParam(required = false) String a,
            @RequestParam(required = false) String b
    ) {
        return a + " " + b;
    }

    /**
     * sentinel API
     *
     * @param a
     * @return
     */
    @GetMapping("/sentinel-api")
    public String sentinel_api(
            @RequestParam(required = false) String a
    ) {
        String resourceName = "sentinel-api";
        ContextUtil.enter(resourceName, "test-wfw");
        Entry entry = null;
        try {
            entry = SphU.entry(resourceName);
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a不能为空");
            }
            return a;
        } catch (BlockException e) {
            e.printStackTrace();
            return "被限流了";
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            return "a不应该为空";
        } finally {
            if (entry != null) {
                entry.exit();
                ContextUtil.exit();
            }
        }
    }

    /**
     * @param a
     * @return
     */
    @SentinelResource(
            value = "sentinel-resource",
            blockHandler = "block",
            fallback = "fall"
    )
    @GetMapping("/sentinel-resource")
    public String sentinel_resource(
            @RequestParam(required = false) String a
    ) {
        if (StringUtils.isBlank(a)) {
            throw new IllegalArgumentException("a不能为空");
        }
        return a;
    }

    public String block(String a, BlockException e) {
        return "进入 block" + e.getRule();
    }

    public String fall(String a, Throwable t) {
        return "进入 fall" + t.getCause();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/restTest/{id}")
    public BrandDTO restTest(@PathVariable Integer id) {
        return this.restTemplate.getForObject(
                "http://commodity-center/brandId/{id}",
                BrandDTO.class, id
        );
    }

//    @Autowired
//    private Source source;
//    @GetMapping("/test-stream")
//    public String testStream(){
//        this.source.output()
//                .send(MessageBuilder
//                        .withPayload("消息")
//                        .build()
//                );
//        return "success";
//    }
//
//    @Autowired
//    private MySource mySource;
//    @GetMapping("/test-stream-2")
//    public String myTestStream(){
//        this.mySource.output()
//                .send(MessageBuilder
//                        .withPayload("my消息")
//                        .build()
//                );
//        return "success";
//    }

    @Value("${your.configuration}")
    private String yourConfiguration;

    @GetMapping("/test-config")
    public String testConfiguration(){
        return this.yourConfiguration;
    }
}
