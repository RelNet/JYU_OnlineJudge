package spring.cache;

import old.Database.OJProblemStatus.ProblemStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class StatusCache {
//    @Cacheable(cacheNames = "Status", cacheManager = "StatusCacheManager")
//    public List<ProblemStatus> getStatusCache(Integer page, Integer contestId) {
//        ProblemStatus status = new ProblemStatus();
//
//        return status.GetStatuspage(page, contestId);
//    }
//}
