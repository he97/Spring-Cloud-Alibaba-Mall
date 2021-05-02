package com.mall.user_center.service;

import com.mall.user_center.dao.user_center.UserInformationMapper;
import com.mall.user_center.domain.entity.user_center.UserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInformationService {
    private final UserInformationMapper userInformationMapper;

    public UserInformation findById(Integer id) {
        return this.userInformationMapper.selectByPrimaryKey(id);
    }
}
