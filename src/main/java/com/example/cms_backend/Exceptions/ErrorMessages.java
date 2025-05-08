package com.example.cms_backend.Exceptions;

public enum ErrorMessages {
    MEMBER_NOT_FOUND("Member not found"),
    ROLE_NOT_FOUND("Role not found"),
    PARISH_NOT_FOUND("Parish not found"),
    NOTIFICATION_NOT_FOUND("Notification not found"),
    GROUP_NOT_FOUND("Group not found"),
    TRANSACTION_NOT_FOUND("Transaction not found"),
    FEEDBACK_NOT_FOUND("Feedback not found"),
    EVENT_NOT_FOUND("Event not found"),
    DIOCESE_NOT_FOUND("DIOCESE not found"),
    CONTRIBUTION_NOT_FOUND("Contribution not found"),
    ATTENDANCE_NOT_FOUND("Attendance not found"),
    FULL_NAME_IS_REQUIRED("Full name is required"),
    INVALID_PHONE_NUMBER("Invalid phone number"),
    INVALID_EMAIL("Invalid email"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    INVALID_TOKEN("Please Login again"),
    EMAIL_REQUIRED("Email is required"),
    BELOW_AGE_LIMIT("User must be at least 10 years old."),
    PASSWORD_INCORRECT("Incorrect current password"),
    PROJECT_NOT_FOUND("Project not found"),
    ;

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
