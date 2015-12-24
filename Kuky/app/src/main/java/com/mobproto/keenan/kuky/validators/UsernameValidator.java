package com.mobproto.keenan.kuky.validators;

/**
 * Created by hieunguyen on 12/23/15.
 */
public class UsernameValidator {

    public UsernameValidator() {}

    public void validate(String username) throws UsernameException {
        String errorMessage = "";
        if (username.isEmpty()) {
            errorMessage = "Username cannot be null";
        } else if (!username.matches("^[a-zA-Z0-9._-]*$")) {
            errorMessage = "Username contains illegal characters";
        } else if (username.length() < 4) {
           errorMessage = "Username must be at least 4 characters long";
        }

        if (!errorMessage.isEmpty()) {
            throw new UsernameException(errorMessage);
        }
    }

    public class UsernameException extends Exception {
        public String message;

        public UsernameException(String errorMessage) {
            this.message = errorMessage;
        }

        public String getMessage() {
            return message;
        }
    }

}
