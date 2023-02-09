package com.athub.rules.entity;

import lombok.Data;

/**
 * @Author Wang wenjun
 */

@Data
public class Order {

    /**
     * 订单金额
     */
    private int amount;

    /**
     * 积分
     */
    private int score;

    @Override
    public String toString() {
        return "Order{" +
                "amount=" + amount +
                ", score=" + score +
                '}';
    }
}
