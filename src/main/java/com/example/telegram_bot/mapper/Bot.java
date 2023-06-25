package com.example.telegram_bot.mapper;

import com.example.telegram_bot.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Holden_-__--___Xiao
 */
@Mapper
public interface Bot {
    /**
     * 查询凤列表
     *
     * @param search 筛选列表
     * @return @List
     */
    List<Phoenix> listPhoenix(@Param("search") List<String> search);

    /**
     * 查询凤详情
     *
     * @param id 凤id
     * @return
     */
    Phoenix getTargetPhoenix(@Param("id") String id);

    /**
     * 新增用户
     *
     * @param userId 用户ID
     */
    void addUser(@Param("userId") Long userId);

    /**
     * 查询用户是否存在
     *
     * @param userId 用户ID
     * @return 用户主键
     */
    UserVO selectUser(@Param("userId") Long userId);

    UserVO selectUserOri(@Param("userId") Long userId);

    /**
     * 兑换后返回凤的真实联系方式
     *
     * @param id 凤id
     * @return 真实联系方式
     */
    String realContent(@Param("id") String id);

    /**
     * 更新余额
     *
     * @param userId  用户id
     * @param balance 用户余额
     */
    void updateBalance(@Param("userId") Long userId, @Param("balance") BigDecimal balance);

    /**
     * @param userId  用户id
     * @param balance 余额
     * @param type    类型
     */
    void updateIdentity(@Param("userId") Long userId, @Param("balance") BigDecimal balance, @Param("type") Integer type);

    /**
     * 增加用户和凤的活动
     *
     * @param userId    用户id
     * @param phoenixId 凤id
     * @param locked    是否解锁
     */
    void linkBuyAction(@Param("userId") Long userId, @Param("phoenixId") String phoenixId, @Param("locked") Integer locked);

    /**
     * 增加斗王用户的城市关系
     *
     * @param userId 用户id
     * @param cityId 城市id
     */
    void insertUserCity(@Param("userId") Long userId, @Param("cityId") Integer cityId);

    /**
     * 判断该用户和凤是否处于解锁关系
     *
     * @param userId    用户id
     * @param phoenixId 凤id
     * @return 是否解锁
     */
    Integer userPhoenixAction(@Param("userId") Long userId, @Param("phoenixId") String phoenixId);

    /**
     * 添加支付宝卡密
     *
     * @param userId 用户id
     */
    void addRedBag(@Param("userId") Long userId);

    /**
     * 显示城市
     *
     * @return 城市实体
     */
    List<CityVO> listCity();

    /**
     * 用户有关的城市代码
     *
     * @param userId 用户id
     * @return 用户城市关系
     */
    List<Integer> userCityInfo(@Param("userId") Long userId);

    List<UserVO> checkUserPhoenix(@Param("userId") Long userId);

    /**
     * 查看解锁城市
     *
     * @param userId 用户id
     * @return 解锁城市
     */
    List<String> checkUserCity(@Param("userId") Long userId);

    /**
     * 选择菜单
     *
     * @param menuCode 菜单代码
     * @return 菜单实体
     */
    List<Menu> listMenu(@Param("menuCode") Integer menuCode);

    /**
     * 展示菜单文本
     *
     * @param menuCode 菜单代码
     */
    String showText(@Param("menuCode") Integer menuCode);
}
