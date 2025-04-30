package com.example.scheduling.batch.domain;

import lombok.Data;


@Data
public class Account {

    private int id;
    private String memberId;
    private int money;

    public Account() {

    }

    public Account(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }

}
