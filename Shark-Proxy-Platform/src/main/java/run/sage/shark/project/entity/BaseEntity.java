package run.sage.shark.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 基础实体
 *
 * @author Sage
 * @date 2023/05/17
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * ID
     */
    @Id
    @Field("_id")
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @CreatedDate
    private Long createTime;

    /**
     * 更新时间
     */
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @LastModifiedDate
    private Long updateTime;

}
