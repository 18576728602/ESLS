package com.datagroup.ESLS.service;

import com.datagroup.ESLS.entity.TagandGood;
import java.util.List;

public interface TagAndGoodService extends Service{
    List<TagandGood> findByGoodidAndShopNumber(long goodId, String shopNumber);

    List<TagandGood> findByShopNumber(String shopNumber);

    List<TagandGood> findByGoodid(long goodId);
}
