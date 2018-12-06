package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.dao.DipmsDao;
import com.datagroup.ESLS.entity.Dispms;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.DispmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispmsServiceImpl extends BaseServiceImpl  implements DispmsService {
   @Autowired
   private DipmsDao dipmsDao;
   @Override
    public List<Dispms> findAll() {
        return dipmsDao.findAll();
    }

    @Override
    public List<Dispms> findAll(Integer page, Integer count) {
        List<Dispms> content = dipmsDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    public Dispms saveOne(Dispms tag) {
        return dipmsDao.save(tag);
    }

    @Override
    public Optional<Dispms> findById(Long id) {
        return dipmsDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            dipmsDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
