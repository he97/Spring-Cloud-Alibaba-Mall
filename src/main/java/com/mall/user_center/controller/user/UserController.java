package com.mall.user_center.controller.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.mall.user_center.auth.CheckAuthorization;
import com.mall.user_center.auth.CheckLogin;
import com.mall.user_center.dao.user_center.AdminMapper;
import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import com.mall.user_center.domain.dto.Commodity.SelectedCommodityDTO;
import com.mall.user_center.domain.dto.cartDto.AddCartRespDto;
import com.mall.user_center.domain.dto.cartDto.RemoveCartDto;
import com.mall.user_center.domain.dto.cartDto.RemoveCartRespDto;
import com.mall.user_center.domain.dto.order.HandleOrderResp;
import com.mall.user_center.domain.dto.order.UserTransactionDto;
import com.mall.user_center.domain.dto.transaction.CancelTransactionDto;
import com.mall.user_center.domain.dto.transaction.StartCancelTranDto;
import com.mall.user_center.domain.dto.user.*;
import com.mall.user_center.domain.entity.user_center.Admin;
import com.mall.user_center.domain.entity.user_center.User;
import com.mall.user_center.domain.entity.user_center.UserInformation;
import com.mall.user_center.service.HandleOrderService;
import com.mall.user_center.service.UserService;
import com.mall.user_center.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {
    private final UserService userService;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;
    private final HandleOrderService handleOrderService;
    private final AdminMapper adminMapper;

    @GetMapping("/{id}")
    @CheckLogin
    public User findById(@PathVariable Integer id) {
        return this.userService.findById(id);
    }
    @GetMapping("/admin/{id}")
    @CheckAuthorization("admin")
    public User findByIdWithAdmin(@PathVariable Integer id) {
        return this.userService.findById(id);
    }

    @GetMapping("/gen-token")
    public String genToken(){
        Map<String,Object> userInfo = new HashMap<>(2);
        userInfo.put("id","1");
        userInfo.put("wxNickName","da");
        userInfo.put("role","admin");
        String token = this.jwtOperator.generateToken(userInfo);
        return token;
    }

    /**
     *
     * @param userLogInDTO
     * @return
     */
    @PostMapping("/adminLogin")
    public AdminLogInRespDTO adminLogin(@RequestBody UserLogInDTO userLogInDTO){
        try {
            log.info("userLogInDTO:{}",userLogInDTO);
//        微信小程序端校验是否已经登陆的结果
            WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                    .getSessionInfo(userLogInDTO.getCode());
            log.info("微信的result:{}",result);
            String openId = result.getOpenid();
//        注册用户
            Example example = new Example(Admin.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("weixinId", openId);
            List<Admin> admins = this.adminMapper.selectByExample(example);
            if(admins.size() != 1){
                throw new IllegalStateException("用户非管理员");
            }
            Admin admin = admins.get(0);
//        颁发Token
            Map<String,Object> userInfo = new HashMap<>(2);
            userInfo.put("id",admin.getAdminId());
            userInfo.put("wxNickName",userLogInDTO.getWxNickName());
            userInfo.put("role","admin");
            String token = this.jwtOperator.generateToken(userInfo);
//        开始构建loginrespdto
            JwtTokenRespDTO jwtTokenRespDTO = JwtTokenRespDTO.builder()
                    .token(token)
                    .expirationTime(jwtOperator.getExpirationTimeInSecond())
                    .build();
            UserRespDTO userRespDTO = UserRespDTO.builder()
                    .userId(admin.getAdminId())
                    .avatarUrl(userLogInDTO.getAvatarUrl())
                    .wxNickName(userLogInDTO.getWxNickName())
                    .build();
            AdminLogInRespDTO adminLogInRespDTO = AdminLogInRespDTO.builder()
                    .user(userRespDTO)
                    .jwtTokenResp(jwtTokenRespDTO)
                    .status("200")
                    .message("用户授权成功")
                    .build();
            log.info("adminLogInRespDTO:{}",adminLogInRespDTO);
            return adminLogInRespDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return AdminLogInRespDTO.builder()
                    .status("500")
                    .message(e.getMessage())
                    .build();
        }
    }
    @PostMapping("/login")
    public LogInRespDTO login(@RequestBody UserLogInDTO userLogInDTO) throws WxErrorException {
        //TODO 传进来的参数核验
        log.info("userLogInDTO:{}",userLogInDTO);
//        微信小程序端校验是否已经登陆的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                .getSessionInfo(userLogInDTO.getCode());
        log.info("微信的result:{}",result);
        String openId = result.getOpenid();
//        注册用户
        User user = this.userService.logIn(userLogInDTO,openId);
//        颁发Token
        Map<String,Object> userInfo = new HashMap<>(2);
        userInfo.put("id",user.getUserId());
        userInfo.put("wxNickName",userLogInDTO.getWxNickName());
        String token = this.jwtOperator.generateToken(userInfo);
//        开始构建loginrespdto
        JwtTokenRespDTO jwtTokenRespDTO = JwtTokenRespDTO.builder()
                .token(token)
                .expirationTime(jwtOperator.getExpirationTimeInSecond())
                .build();
        UserRespDTO userRespDTO = UserRespDTO.builder()
                .brandName("qwerty")
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .avatarUrl(userLogInDTO.getAvatarUrl())
                .wxNickName(userLogInDTO.getWxNickName())
                .build();
        LogInRespDTO logInRespDTO = LogInRespDTO.builder()
                .user(userRespDTO)
                .jwtTokenResp(jwtTokenRespDTO)
                .build();
        log.info("logInRespDTO:{}",logInRespDTO);
        return logInRespDTO;
    }
    @PostMapping("/submit-commodity")
    public void handleSubmitCommodity(@RequestBody Map<String,String>commodityInfo){
        log.info("commodityInfo:{}",commodityInfo);
    }
    @PutMapping("/getCart")
    public List<CartCommoditiesDTO> getCart(@RequestBody UserTokenDTO token){
        log.info("token:{}",token);
        Claims claimsFromToken = this.jwtOperator.getClaimsFromToken(token.getToken());
        String token_id = (String) claimsFromToken.get("id");
        return this.userService.getShoppingCartCommodities(token_id);
    }
    @PutMapping("/getSelected")
    public List<CartCommoditiesDTO> getSelected(@RequestBody SelectedCommodityDTO idList){
        log.info("idlist:{}",idList.getList());
//        return null;
        return this.userService.getUserSelected(idList);
    }
    @PutMapping("/jstBuy")
    public List<CartCommoditiesDTO> justBuy(@RequestBody SelectedCommodityDTO idList){
        log.info("idlist:{}",idList.getList());
//        return null;
        return this.userService.justBuyGetCommodity(idList);
    }
    @PutMapping("/handleOrder")
    public HandleOrderResp handleResp(@RequestBody UserTransactionDto userOrderDTO){
        log.info("userOrserDTO:{}",userOrderDTO);
        return this.handleOrderService.handleOrder(userOrderDTO);

    }

    @PostMapping("startCancelTransaction")
    public RespDto startCancelTransaction(@RequestBody StartCancelTranDto startCancelTranDto){
        return this.handleOrderService.startCancelTransaction(startCancelTranDto);
    }

    @PostMapping("/cancelTransaction")
    public HandleOrderResp cancelTransaction(@RequestBody CancelTransactionDto cancelTransactionDto){
        return this.handleOrderService.cancelTransaction(cancelTransactionDto);
    }

    @GetMapping("/addCart/{commodityId}")
    public AddCartRespDto addCart(@PathVariable String commodityId){
        return this.userService.addCartByCommodityId(commodityId);
    }
    @PostMapping("/removeCart")
    public RemoveCartRespDto removeCart(@RequestBody RemoveCartDto removeCartDto){
        return this.userService.removeCartByCommodityId(removeCartDto);

    }
    @GetMapping("/userInfo")
    public UserInfoRespDto getUserInfo(){
        return this.userService.getUserInfoById();
    }
    @CheckLogin
    @PostMapping("/alterUserInfo")
    public RespDto alterUserInfo(@RequestBody AlterUserInfoDto alterUserInfoDto) throws ParseException {
        return this.userService.alterUserInfo(alterUserInfoDto);
    }
    @CheckLogin
    @PostMapping("/finishTransaction")
    public RespDto finishTransaction(@RequestBody FinishTransactionDto finishTransactionDto){
        return this.userService.finishTransaction(finishTransactionDto);
    }
//    @GetMapping("/hadSelled")
//    public
}
