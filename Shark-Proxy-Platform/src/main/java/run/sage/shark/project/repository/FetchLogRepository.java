package run.sage.shark.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import run.sage.shark.project.entity.FetchLog;

/**
 * 抓取日志库
 *
 * @author sage
 * @date 2023/05/13
 */
public interface FetchLogRepository extends MongoRepository<FetchLog, String> {
}