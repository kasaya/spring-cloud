package com.oycl.util.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oycl.model.MCodeBase;
import com.oycl.orm.dao.MCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CacheConfig(cacheNames = "Mcode")
public class McodeCache {

    Logger logger = LoggerFactory.getLogger(McodeCache.class);

    @Autowired
    MCodeMapper mCodeMapper;

    @Cacheable
    public List<MCodeBase> getMcode(String classCd){
        logger.info("getMcode start");
        QueryWrapper<MCodeBase> wrapper = new QueryWrapper<>();
        wrapper.eq("class_cd", classCd);
        return mCodeMapper.selectList(wrapper);
    }

}
