package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.request.RequestItem;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.dao.GoodDao;
import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl extends BaseServiceImpl implements GoodService {

    @Autowired
    private GoodDao goodDao;

    @Override
    public List<Good> findAll() {
        return goodDao.findAll();
    }

    @Override
    public List<Good> findAll(Integer page, Integer count) {
        List<Good> content = goodDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    public Good saveOne(Good good) {
        return goodDao.save(good);
    }

    @Override
    public Good findById(Long id) {
        return goodDao.getOne(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            goodDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public ResponseBean updateGoods(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {

        }
        return new ResponseBean(sum, successNumber);
    }
}
