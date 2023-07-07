package com.pragma.powerup.usermicroservice.domain.comparator;

import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DishComparator implements Comparator<OrderDish> {
    private final Map<DishTypeEnum, Integer> priorityMap;

    public DishComparator() {
        priorityMap = new HashMap<>();
        priorityMap.put(DishTypeEnum.MEAT, 0);
        priorityMap.put(DishTypeEnum.SOUP, 1);
        priorityMap.put(DishTypeEnum.DESSERT, 2);
    }

    @Override
    public int compare(OrderDish dish1, OrderDish dish2) {
        Integer priority1 = priorityMap.get(dish1.getDishTypeEnum());
        Integer priority2 = priorityMap.get(dish2.getDishTypeEnum());

        return Integer.compare(priority1, priority2);
    }
}
