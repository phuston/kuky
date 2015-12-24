package com.mobproto.keenan.kuky.validators;

/**
 * Created by hieunguyen on 12/23/15.
 */
public class PasswordValidator {

    public PasswordValidator() {}

    public void validate(String password) throws PasswordException {
        String errorMessage = "";

        if (password.matches("")) {
            errorMessage = "Password cannot be null";
        } else if (password.length() < 4){
            errorMessage = "Password must be at least 4 characters long";
        } else if (!password.matches("[a-zA-Z0-9]*")) {
            errorMessage = "Password contains illegal characters";
        }

        if (!errorMessage.isEmpty()) {
            throw new PasswordException(errorMessage);
        }
    }

    public class PasswordException extends Exception {

        public String message;

        public PasswordException(String errorMessage) {
            this.message = errorMessage;
        }

        public String getMessage() {
            return message;
        }
    }

}
