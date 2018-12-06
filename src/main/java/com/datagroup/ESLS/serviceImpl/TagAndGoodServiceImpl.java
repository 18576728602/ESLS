package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.dao.TagAndGoodDao;
import com.datagroup.ESLS.entity.TagandGood;
import com.datagroup.ESLS.service.TagAndGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagAndGoodServiceImpl extends BaseServiceImpl implements TagAndGoodService {

    @Autowired
    TagAndGoodDao tagAndGoodDao;
    @Override
    public List<TagandGood> findByGoodidAndShopNumber(long goodId, String shopNumber){
        return tagAndGoodDao.findByidAndShopNumber(goodId,shopNumber);
    }
    @Override
    public List<TagandGood> findByShopNumber(String shopNumber){
        return tagAndGoodDao.findByShopNumber(shopNumber);
    }
    @Override
    public List<TagandGood> findByGoodid(long goodId){
        return tagAndGoodDao.findByid(goodId);
    }
}
