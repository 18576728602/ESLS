package com.datagroup.ESLS.netty.command;

import lombok.Data;

@Data
public class CategoryItem {
    private byte command_class;
    private byte command_id;
}
