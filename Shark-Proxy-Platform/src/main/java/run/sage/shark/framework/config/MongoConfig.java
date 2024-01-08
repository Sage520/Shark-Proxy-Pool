package run.sage.shark.framework.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import javax.annotation.Resource;

/**
 * MongoDB 配置
 *
 * @author Sage
 * @date 2023/02/03
 */
@Configuration
public class MongoConfig {

    @Resource
    private MongoDatabaseFactory mongoDbFactory;

    @Resource
    private MongoMappingContext mongoMappingContext;

    /**
     * 转换类配置
     *
     * @return 转换类
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        // 不保存 _class 属性到mongo
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

}
