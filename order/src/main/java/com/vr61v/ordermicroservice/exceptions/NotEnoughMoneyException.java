package com.vr61v.ordermicroservice.exceptions;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String message) {
        super(message);
    }

}