package com.kangec.client.ui.ui.chats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 未读消息计数器
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemindCount {
    private int count = 0;  // 消息提醒条数
}
