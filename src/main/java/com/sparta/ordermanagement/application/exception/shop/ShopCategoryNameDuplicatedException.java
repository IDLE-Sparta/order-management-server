package com.sparta.ordermanagement.application.exception.shop;

import static com.sparta.ordermanagement.application.exception.message.ShopErrorMessage.*;

import com.sparta.ordermanagement.application.exception.ConstraintException;

public class ShopCategoryNameDuplicatedException extends ConstraintException {

    public ShopCategoryNameDuplicatedException() {
        super(CATEGORY_NAME_DUPLICATED.getMessage());
    }
}
