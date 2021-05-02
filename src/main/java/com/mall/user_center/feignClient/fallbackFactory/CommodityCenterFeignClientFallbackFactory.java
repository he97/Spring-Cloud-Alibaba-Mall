package com.mall.user_center.feignClient.fallbackFactory;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mall.user_center.domain.dto.Brand.BrandDTO;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoLogDto;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoRespDto;
import com.mall.user_center.domain.dto.Commodity.CommodityRespDto;
import com.mall.user_center.domain.dto.adminDto.SearchDto;
import com.mall.user_center.feignClient.CommodityCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import feign.hystrix.FallbackFactory;

import java.util.List;

@Component
@Slf4j
public class CommodityCenterFeignClientFallbackFactory implements FallbackFactory<CommodityCenterFeignClient> {
    @Override
    public CommodityCenterFeignClient create(Throwable throwable) {
        return new CommodityCenterFeignClient() {
            @Override
            public BrandDTO findById(String id) {
                log.warn("远程被限流或者降级了");
                log.warn("throwable的getCause:{}", throwable.getCause());
                log.warn("throwable的toString:{}", throwable.toString());
                if (throwable instanceof BlockException) {
                    log.warn("BlockException");
                } else {
                    log.warn("throwable的getClass:{}", throwable.getClass());
                }
                BrandDTO brandDTO = new BrandDTO();
                brandDTO.setBrandId("222");
                brandDTO.setBrandName("default");
                return brandDTO;
            }

            @Override
            public CommodityInfoRespDto getCommodityInfoById(String commodityId) {
                return null;
            }

            @Override
            public List<CommodityRespDto> getRequiredCheckCommodities() {
                return null;
            }

            @Override
            public List<CommodityInfoLogDto> getSell() {
                return null;
            }

            @Override
            public List<CommodityInfoRespDto> getAllCommodity() {
                return null;
            }

            @Override
            public List<CommodityRespDto> searchOneCommodityById(SearchDto searchDto) {
                return null;
            }

        };
    }
}
