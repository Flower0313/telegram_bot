package com.example.telegram_bot.mapper;

import com.example.telegram_bot.pojo.Phoenix;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
