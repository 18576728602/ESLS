package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.dao.StyleDao;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.StyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StyleServiceImpl extends BaseServiceImpl implements StyleService {
    @Autowired
    private StyleDao styleDao;

    @Override
    public List<Style> findAll() {
        return styleDao.findAll();
    }
    @Override
    public List<Style> findAll(Integer page, Integer count){
        List<Style> content = styleDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }
    @Override
    public Style saveOne(Style tag) {
        return styleDao.save(tag);
    }

    @Override
    public Optional<Style> findById(Long id) {
        return styleDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            styleDao.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public ResponseBean flushTags(RequestBean requestBean, Integer mode) {
        int sum = 0;
        int successNumber = 0;
        if(mode == 0){
            log.info("向选用该样式的标签发送刷新命令");
        }
        else if(mode == 1){
            log.info("定期刷新");
        }
        return new ResponseBean(sum, successNumber);
    }
}
