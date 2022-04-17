package edu.neu.absorb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.neu.absorb.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author novo
 * @since 2022/4/15
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    // get the id that just inserted
    @Select("SELECT last_insert_id()")
    Integer getLastCreatedUserId();
}
