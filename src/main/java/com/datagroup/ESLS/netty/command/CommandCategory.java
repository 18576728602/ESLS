package com.datagroup.ESLS.netty.command;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "category")
@Data
public class CommandCategory {
    public final static Map<String, CategoryItem> COMMAND_CATEGORY = new HashMap<>();

    public Map<String, CategoryItem> getCOMMAND_CATEGORY() {
        return COMMAND_CATEGORY;
    }

    public static byte[] getResponse(String key, byte[] header) {
        byte[] result = new byte[4];
        CategoryItem categoryItem = COMMAND_CATEGORY.get(key);
        result[0] = categoryItem.getCommand_class();
        result[1] = categoryItem.getCommand_id();
        result[2] = header[0];
        result[3] = header[1];
        return result;
    }

    public static String getCommandCategory(byte[] header) {
        if (header[0] == COMMAND_CATEGORY.get(CommandConstant.ACK).getCommand_class() && header[1] == COMMAND_CATEGORY.get(CommandConstant.ACK).getCommand_id()) {
            return CommandConstant.ACK;
        } else if (header[0] == COMMAND_CATEGORY.get(CommandConstant.NACK).getCommand_class() && header[1] == COMMAND_CATEGORY.get(CommandConstant.NACK).getCommand_id()) {
            return CommandConstant.NACK;
        } else if (header[0] == COMMAND_CATEGORY.get(CommandConstant.OVERTIME).getCommand_class() && header[1] == COMMAND_CATEGORY.get(CommandConstant.OVERTIME).getCommand_id()) {
            return CommandConstant.OVERTIME;
        } else {
            return null;
        }
    }
}
