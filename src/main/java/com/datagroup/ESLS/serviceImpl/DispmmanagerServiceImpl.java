package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.dao.DispmmanagerDao;
import com.datagroup.ESLS.entity.Dispmmanager;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.DispmmanagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispmmanagerServiceImpl  extends BaseServiceImpl implements DispmmanagerService {
    @Autowired
    DispmmanagerDao dispmmanagerDao;

    @Override
    public List<Dispmmanager> findAll() {
        return dispmmanagerDao.findAll();
    }

    @Override
    public List<Dispmmanager> findAll(Integer page, Integer count) {
        List<Dispmmanager> content = dispmmanagerDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    public Dispmmanager saveOne(Dispmmanager tag) {
        return dispmmanagerDao.save(tag);
    }

    @Override
    @Cacheable(value = RedisConstant.CACHE_DISPMMANAGERS)
    public Optional<Dispmmanager> findById(Long id) {
        return dispmmanagerDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            dispmmanagerDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
