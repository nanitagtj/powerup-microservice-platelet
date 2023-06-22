package com.pragma.powerup.usermicroservice.domain.model;

public class OrderDish {
    private Long id;
    private Order order;
    private Dish dish;
    private int quantity;

    public OrderDish() {
    }

    public OrderDish(Long id, Order order, Dish dish, int quantity) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
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

}