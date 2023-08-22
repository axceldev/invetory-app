package com.axceldev.inventory_service.model.dtos.rs;

public record BaseResponse(String[] errorMessages) {
    public boolean hasErrors(){
        return errorMessages != null && errorMessages.length > 0;
    }
}
