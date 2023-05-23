package com.example.telegram_bot.mapper;

import com.example.telegram_bot.pojo.Phoenix;
import com.example.telegram_bot.pojo.UserVO;
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
     * @return @List
     */
    List<Phoenix> listPhoenix();

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

    /**
     * 兑换后返回凤的真实联系方式
     *
     * @param id 凤id
     * @return 真实联系方式
     */
    String realContent(@Param("id") String id);

    /**
     * @param userId  用户id
     * @param balance 用户余额
     */
    void updateBalance(@Param("userId") Long userId, @Param("balance") BigDecimal balance);

    void linkBuyAction(@Param("userId") Long userId, @Param("phoenixId") String phoenixId);
}
