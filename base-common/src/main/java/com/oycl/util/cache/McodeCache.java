package com.oycl.util.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "Mcode")
public class McodeCache {

    Logger logger = LoggerFactory.getLogger(McodeCache.class);



    //@Cacheable
//    public List<MCodeBase> getMcode(String classCd){
//        logger.info("getMcode start");
//        QueryWrapper<MCodeBase> wrapper = new QueryWrapper<>();
//        wrapper.eq("class_cd", classCd);
//        return mCodeMapper.selectList(wrapper);
//    }

}
