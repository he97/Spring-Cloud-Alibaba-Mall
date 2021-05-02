package com.mall.user_center.feignClient;

import com.mall.user_center.domain.dto.Brand.BrandDTO;
import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoLogDto;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoRespDto;
import com.mall.user_center.domain.dto.Commodity.CommodityRespDto;
import com.mall.user_center.domain.dto.adminDto.SearchDto;
import com.mall.user_center.feignClient.fallbackFactory.CommodityCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "commodity-center",configuration = CommodityCenterGeignConfiguration.class)
@FeignClient(name = "commodity-center",
//fallback与fallbackfactory只能有一个
//        fallback = CommodityCenterFeignClientFallback.class
        fallbackFactory = CommodityCenterFeignClientFallbackFactory.class
)
public interface CommodityCenterFeignClient {
    /**
     * https://commodity-center/brandId/1
     *
     * @param id
     * @return
     */
    @GetMapping("/brandId/{id}")
    BrandDTO findById(@PathVariable String id);

    @GetMapping("/commodity/getInfo/{commodityId}")
    CommodityInfoRespDto getCommodityInfoById(@PathVariable String commodityId);

    @GetMapping("/commodity/getRequiredCheckCommodities")
    List<CommodityRespDto> getRequiredCheckCommodities();

    @GetMapping("/commodity/getSell")
    List<CommodityInfoLogDto> getSell();

    @GetMapping("/commodity/getAll")
    List<CommodityInfoRespDto> getAllCommodity();

    @PostMapping("/commodity/searchCommoditiesById")
    List<CommodityRespDto> searchOneCommodityById(@RequestBody SearchDto searchDto);
}
