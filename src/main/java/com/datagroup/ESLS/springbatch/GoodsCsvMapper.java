package com.datagroup.ESLS.springbatch;

import com.datagroup.ESLS.entity.Good;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class GoodsCsvMapper implements FieldSetMapper<Good> {
    @Override
    public Good mapFieldSet(FieldSet fieldSet) throws BindException {
        byte promotion = 0;
        double price = 0,promotePrice = 0;
        try {
            price = fieldSet.readDouble("price");
        }
        catch (Exception e){}
        try {
            promotePrice = fieldSet.readDouble("promotePrice");
        }
        catch (Exception e){}

        return new Good(
                fieldSet.readString("barCode")
                , fieldSet.readString("name")
                , price
                , promotePrice
                ,  fieldSet.readString("promotionReason")
                ,  fieldSet.readString("unit")
                , fieldSet.readString("origin")
                , fieldSet.readString("spec")
                , fieldSet.readString("category")
                , fieldSet.readString("shelfNumber")
                , fieldSet.readString("rfus01")
                , fieldSet.readString("rfus02")
                , fieldSet.readString("qrCode")
                , fieldSet.readString("provider")
        );
    }
}
