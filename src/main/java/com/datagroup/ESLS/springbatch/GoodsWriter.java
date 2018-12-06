package com.datagroup.ESLS.springbatch;

import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.service.GoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("GoodsWriter")
@Slf4j
public class GoodsWriter  implements ItemWriter<Good> {
    @Autowired
    private GoodService goodService;
    @Override
    public void write(List<? extends Good> list) throws Exception {
        for(Good good:list) {
            System.out.println(good);
            try{
            goodService.saveOne(good);
            }
            catch (Exception e){
                System.out.println("指定条码的商品已存在！");
            }
        }
    }
}
