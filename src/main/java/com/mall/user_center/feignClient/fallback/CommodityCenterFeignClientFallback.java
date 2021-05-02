package com.mall.user_center.feignClient.fallback;

import com.mall.user_center.domain.dto.Brand.BrandDTO;
import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoLogDto;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoRespDto;
import com.mall.user_center.domain.dto.Commodity.CommodityRespDto;
import com.mall.user_center.domain.dto.adminDto.SearchDto;
import com.mall.user_center.feignClient.CommodityCenterFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommodityCenterFeignClientFallback implements CommodityCenterFeignClient {
    @Override
    public BrandDTO findById(String id) {
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

}
