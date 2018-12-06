package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.dao.ShopDao;
import com.datagroup.ESLS.entity.Shop;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl extends BaseServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    @Cacheable(value = RedisConstant.CACHE_SHOPS)
    public List<Shop> findAll() {
        return shopDao.findAll();
    }

    @Override
    public List<Shop> findAll(Integer page, Integer count) {
        List<Shop> content = shopDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }

    @Override
    @Cacheable(value = RedisConstant.CACHE_SHOPS)
    public Shop saveOne(Shop shop) {
        return shopDao.save(shop);
    }

    @Override
    @Cacheable(value = RedisConstant.CACHE_SHOPS)
    public Optional<Shop> findById(Long id) {
        return shopDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            shopDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
