package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.dao.RouterDao;
import com.datagroup.ESLS.entity.Router;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouterServiceImpl extends BaseServiceImpl implements RouterService {
    @Autowired
    private RouterDao routerDao;

    @Override
    public List<Router> findAll() {
        return routerDao.findAll();
    }

    @Override
    public List<Router> findAll(Integer page, Integer count) {
        List<Router> content = routerDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    public Router saveOne(Router tag) {
        return routerDao.save(tag);
    }

    @Override
    public Optional<Router> findById(Long id) {
        return routerDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            routerDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
