package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.dao.ScanDao;
import com.datagroup.ESLS.entity.Scan;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScanServiceImpl extends BaseServiceImpl implements ScanService {
    @Autowired
    ScanDao scanDao;

    @Override
    public List<Scan> findAll() {
        return scanDao.findAll();
    }

    @Override
    public List<Scan> findAll(Integer page, Integer count) {
        List<Scan> content = scanDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    @Cacheable(value = RedisConstant.CACHE_SCANS)
    public Scan saveOne(Scan tag) {
        return scanDao.save(tag);
    }

    @Override
    public Optional<Scan> findById(Long id) {
        return scanDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            scanDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
