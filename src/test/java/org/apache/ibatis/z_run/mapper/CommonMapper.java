package org.apache.ibatis.z_run.mapper;


import org.apache.ibatis.z_run.pojo.QueryCondition;
import org.apache.ibatis.z_run.pojo.Test;

import java.util.List;

public interface CommonMapper {
    
    List<Test> findByCondition(QueryCondition condition);
}
