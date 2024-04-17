package com.maple.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.GlobalConfig;
import com.maple.common.config.MapleConstants;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCheckException;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.model.TokenBean;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.Md5Util;
import com.maple.common.util.RedisUtil;
import com.maple.common.util.TransformUtils;
import com.maple.system.bean.User;
import com.maple.system.bean.UserRole;
import com.maple.system.mapper.UserMapper;
import com.maple.system.mapper.UserRoleMapper;
import com.maple.system.service.IUserRoleService;
import com.maple.system.service.IUserService;
import com.maple.system.vo.model.UserModel;
import com.maple.system.vo.query.LoginQuery;
import com.maple.system.vo.query.UserPageQuery;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户中心-用户信息表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final RedisUtil redisUtil;

    private final IUserRoleService userRoleService;

    @Override
    public UserModel login(LoginQuery req) {
        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getAccount, req.getAccount())
                .last(MapleConstants.LAST_LIMIT_1));
        if (Objects.isNull(user)) {
            throw new MapleCheckException(ErrorCode.USER_LOGIN_ERROR);
        }
        if (MapleConstants.STATUS_FALSE.equals(user.getStatus())) {
            throw new MapleCheckException(ErrorCode.USER_STATUS_ERROR);
        }
        if (!Md5Util.verifyPassword(req.getPassword(), user.getPassword(), user.getSalt())) {
            throw new MapleCheckException(ErrorCode.USER_LOGIN_ERROR);
        }

        List<Long> userRoles = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRole.class)
                .eq(UserRole::getUserId, user.getId()))
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        TokenBean tokenBean = TokenBean.builder()
                .userId(user.getId())
                .userType(user.getUserType())
                .account(user.getAccount())
                .deptId(user.getDeptId())
                .roleList(StringUtils.join(userRoles, ","))
                .build();

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        String token;
        try {
            token = JwtUtil.createToken(tokenBean);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MapleCommonException(ErrorCode.COMMON_ERROR);
        }
        userModel.setToken(token);
        redisUtil.set(GlobalConfig.getRedisUserKey(user.getAccount()), token);
        return userModel;
    }

    @Override
    public IPage<UserModel> getPageList(UserPageQuery userReq) {
        return userMapper.getPageList(userReq.getPage(), userReq.getQuery());
    }

    @Override
    public UserModel getUserById(Long id) {
        User user = userMapper.selectById(id);
        UserModel userModel = TransformUtils.map(user, UserModel.class);
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRole.class)
                .eq(UserRole::getUserId, id));
        userModel.setRoleIds(userRoles.stream().map(UserRole::getRoleId).filter(Objects::nonNull).collect(Collectors.toList()));
        return userModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserModel model) {
        User user = TransformUtils.map(model, User.class);
        String salt = String.valueOf(RandomUtils.nextInt(100000, 999999));
        user.setPassword(Md5Util.encrypt(user.getPassword(), salt));
        user.setSalt(salt);
        userMapper.insert(user);
        saveUserRole(user.getId(), model.getRoleIds());
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserModel model) {
        User user = TransformUtils.map(model, User.class);
        user.setPassword(null);
        user.setSalt(null);
        userMapper.updateById(user);
        userRoleService.remove(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId, model.getId()));
        saveUserRole(user.getId(), model.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
        userRoleService.remove(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId, id));
    }

    @Override
    public void logout() {
        redisUtil.remove(GlobalConfig.getRedisUserKey(JwtUtil.getAccount()));
    }

    /**
     * 保存用户和角色的关系
     *
     * @param userId  用户ID
     * @param roleIds 角色ID集合
     */
    private void saveUserRole(Long userId, List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        List<UserRole> userRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }
}
