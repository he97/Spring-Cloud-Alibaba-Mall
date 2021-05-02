package com.mall.user_center.configuration;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.jca.GetInstance;

@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            log.info("this is loadBalancer:{}", loadBalancer);
//        拿到请求的微服务的名称
            String name = loadBalancer.getName();
//        拿到服务发现的相关api
            NamingService namingService = nacosServiceManager.getNamingService(this.nacosDiscoveryProperties.getNacosProperties());
//            已经过时的写法
//            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            log.info("namingService:{}", namingService);
//        开始使用内置的均衡算法
            Instance instance = namingService.selectOneHealthyInstance(name);
            log.info("this is port:{} ||this is instance:{}", instance.getPort(), instance);
//            返回一个服务
            return new NacosServer(instance);
        } catch (NacosException e) {
            return null;
        }
    }
}
