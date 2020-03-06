package spring.cache;

import data.home.HomeInfo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

//@Service
//public class HomeCache {
//
//    @Cacheable(cacheNames = "Home", cacheManager = "HomeCacheManager")
//    public HomeInfo get() {
//        HomeInfo home = new HomeInfo();
//
//        return home;
//    }
//
//    @CachePut(cacheNames = "Home", cacheManager = "HomeCacheManager")
//    public HomeInfo update() {
//        HomeInfo home = new HomeInfo();
//
//        return home;
//    }
//}
