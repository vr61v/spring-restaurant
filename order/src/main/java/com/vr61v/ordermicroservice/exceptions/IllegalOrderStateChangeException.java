package com.vr61v.ordermicroservice.exceptions;

public class IllegalOrderStateChangeException extends Exception{

    public IllegalOrderStateChangeException(String massage) {
        super(massage);
    }

}
