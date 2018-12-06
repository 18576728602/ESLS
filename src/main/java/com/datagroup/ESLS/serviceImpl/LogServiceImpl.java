package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.dao.LogDao;
import com.datagroup.ESLS.entity.Logs;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl extends BaseServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    @Override
    public List<Logs> findAll() {
        return logDao.findAll();
    }

    @Override
    @Cacheable(value = RedisConstant.CACHE_LOGS)
    public List<Logs> findAll(Integer page, Integer count) {
        List<Logs> content = logDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }

    @Override
    @Cacheable(value = RedisConstant.CACHE_LOGS)
    public Logs saveOne(Logs logs) {
        return logDao.save(logs);
    }

    @Override
    @Cacheable(value = RedisConstant.CACHE_LOGS)
    public Optional<Logs> findById(Long id) {
        return logDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            logDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
