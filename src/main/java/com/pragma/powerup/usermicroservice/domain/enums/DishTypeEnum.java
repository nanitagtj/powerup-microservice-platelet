package com.pragma.powerup.usermicroservice.domain.enums;

public enum DishTypeEnum {
    MEAT(0),
    SOUP(1),
    DESSERT(2);

    private final int priority;

    DishTypeEnum(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
