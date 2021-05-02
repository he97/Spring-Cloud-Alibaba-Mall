package com.mall.user_center.configuration;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import sun.security.jca.GetInstance;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author he97
 */
@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            log.info("this is loadBalancer:{}", loadBalancer);
//        拿到请求的微服务的名称和集群名称
            String name = loadBalancer.getName();
            String nacosClusterName = nacosDiscoveryProperties.getClusterName();
//        拿到服务发现的相关api
            NamingService namingService = nacosServiceManager.getNamingService(this.nacosDiscoveryProperties.getNacosProperties());
//            已经过时的写法
//            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
//            找到了所有的实例
            List<Instance> instances = namingService.selectInstances(name, true);
//            log一下所有实例的数目
            log.info("实例总数:{}", instances.size());

//            筛选实例
            List<Instance> selectedList = instances.stream()
                    .filter(instance -> Objects.equals(instance.getClusterName(), nacosClusterName))
                    .collect(Collectors.toList());

            List<Instance> instanceList = null;

//            log一下筛选之后的实例
            log.info("筛选之后的实例数:{}", selectedList.size());
            for (Instance selected :
                    selectedList) {
                log.info("实例的端口:{}", selected.getPort());
                log.info("实例的信息:{}", selected);
            }

//            判断是不是空
            if (CollectionUtils.isEmpty(selectedList)) {
//               是空
                instanceList = instances;
                log.info("发生了跨集群的调用");
            } else {
                instanceList = selectedList;
            }
//              自定义筛选算法
            Instance instance = MyBalancer.myGetHostByRandomWeight(instanceList);
            return new NacosServer(instance);
        } catch (NacosException e) {
            log.error("发生错误");
            return null;
        }
    }

}

class MyBalancer extends Balancer {
    public static Instance myGetHostByRandomWeight(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}
