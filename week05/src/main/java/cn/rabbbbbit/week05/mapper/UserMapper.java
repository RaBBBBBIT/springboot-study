package cn.rabbbbbit.week05.mapper;

import cn.rabbbbbit.week05.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("""
            SELECT id, username, password, age, email, create_time
            FROM tb_user
            ORDER BY id
            LIMIT 1
            """)
    User selectFirstUser();
}
