package com.pragma.powerup.usermicroservice.domain.model;

import com.pragma.powerup.usermicroservice.domain.enums.*;

public class OrderDish {
    private Long id;
    private Order order;
    private Dish dish;
    private int quantity;
    private DishTypeEnum dishTypeEnum;
    private Integer grams;
    private SoupAccompanimentEnum soupAccompanimentEnum;
    private DessertType dessertType;
    private FlavorTypeEnum flavorTypeEnum;
    private ToppingTypeEnum toppingTypeEnum;

    public OrderDish() {
    }

    public OrderDish(Long id, Order order, Dish dish, int quantity, DishTypeEnum dishTypeEnum, Integer grams, SoupAccompanimentEnum soupAccompanimentEnum, DessertType dessertType, FlavorTypeEnum flavorTypeEnum, ToppingTypeEnum toppingTypeEnum) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
        this.dishTypeEnum = dishTypeEnum;
        this.grams = grams;
        this.soupAccompanimentEnum = soupAccompanimentEnum;
        this.dessertType = dessertType;
        this.flavorTypeEnum = flavorTypeEnum;
        this.toppingTypeEnum = toppingTypeEnum;
    }

    public OrderDish(Long orderId, DishTypeEnum dishType) {
        this.order = new Order(orderId);
        this. dishTypeEnum = dishType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DishTypeEnum getDishTypeEnum() {
        return dishTypeEnum;
    }

    public void setDishTypeEnum(DishTypeEnum dishTypeEnum) {
        this.dishTypeEnum = dishTypeEnum;
    }

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public SoupAccompanimentEnum getSoupAccompanimentEnum() {
        return soupAccompanimentEnum;
    }

    public void setSoupAccompanimentEnum(SoupAccompanimentEnum soupAccompanimentEnum) {
        this.soupAccompanimentEnum = soupAccompanimentEnum;
    }

    public DessertType getDessertType() {
        return dessertType;
    }

    public void setDessertType(DessertType dessertType) {
        this.dessertType = dessertType;
    }

    public FlavorTypeEnum getFlavorTypeEnum() {
        return flavorTypeEnum;
    }

    public void setFlavorTypeEnum(FlavorTypeEnum flavorTypeEnum) {
        this.flavorTypeEnum = flavorTypeEnum;
    }

    public ToppingTypeEnum getToppingTypeEnum() {
        return toppingTypeEnum;
    }

    public void setToppingTypeEnum(ToppingTypeEnum toppingTypeEnum) {
        this.toppingTypeEnum = toppingTypeEnum;
    }
}