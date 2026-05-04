package owl.medassist_back.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionName {

    LOGIN_IS_NOT_UNIQUE(
            "Login is not unique.",
            HttpStatus.CONFLICT
    ),
    SERVICE_NOT_FOUND(
            "Service not found.",
            HttpStatus.NOT_FOUND
    ),
    SPECIALIST_NOT_FOUND(
            "Specialist not found.",
            HttpStatus.NOT_FOUND
    ),
    DOCUMENT_NOT_FOUND(
            "Document not found.",
            HttpStatus.NOT_FOUND
    ),
    FACILITY_NOT_FOUND(
            "Medical facility not found.",
            HttpStatus.NOT_FOUND
    ),
    SPECIALIST_FACILITY_NOT_FOUND(
            "Specialist facility relation not found.",
            HttpStatus.NOT_FOUND
    ),
    SERVICE_PRICE_NOT_FOUND(
            "Service price not found.",
            HttpStatus.NOT_FOUND
    ),
    CONDITION_NOT_FOUND(
            "Condition not found.",
            HttpStatus.NOT_FOUND
    ),
    SPECIALIZATION_NOT_FOUND(
            "Specialization not found.",
            HttpStatus.NOT_FOUND
    ),
    DOCUMENT_TYPE_NOT_FOUND(
            "Document type not found.",
            HttpStatus.NOT_FOUND
    ),
    SCHEDULE_NOT_FOUND(
            "Schedule slot not found.",
            HttpStatus.NOT_FOUND
    ),
    APPOINTMENT_TARGET_REQUIRED(
            "Either serviceId or specialistId must be provided.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_DAY_OF_WEEK(
            "Day of week must be in range 1..7.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_SCHEDULE_INTERVAL(
            "Schedule endTime must be greater than startTime.",
            HttpStatus.BAD_REQUEST
    ),
    SCHEDULE_TARGET_REQUIRED(
            "Provide specialistFacilityId or both specialistId and facilityId.",
            HttpStatus.BAD_REQUEST
    ),
    DUPLICATE_SERVICE_URL(
            "Service URL must be unique.",
            HttpStatus.CONFLICT
    ),
    DUPLICATE_CONDITION_TEXT(
            "Condition text must be unique.",
            HttpStatus.CONFLICT
    ),
    DUPLICATE_SPECIALIZATION_NAME(
            "Specialization name must be unique.",
            HttpStatus.CONFLICT
    ),
    DUPLICATE_DOCUMENT_TYPE_NAME(
            "Document type name must be unique.",
            HttpStatus.CONFLICT
    ),
    INVALID_APPOINTMENT_ID(
            "Appointment ID must be greater than 0.",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_SCHEDULE_DATE(
            "Schedule date must not be in the past.",
            HttpStatus.BAD_REQUEST
    ),
    APPOINTMENT_CREATE_FAILED(
            "Failed to create appointment in MIS.",
            HttpStatus.BAD_GATEWAY
    ),
    APPOINTMENT_CANCEL_FAILED(
            "Failed to cancel appointment in MIS.",
            HttpStatus.BAD_GATEWAY
    ),
    SCHEDULE_DAY_FETCH_FAILED(
            "Failed to fetch daily schedule from MIS.",
            HttpStatus.BAD_GATEWAY
    ),
    SCHEDULE_PERIOD_FETCH_FAILED(
            "Failed to fetch two-week schedule from MIS.",
            HttpStatus.BAD_GATEWAY
    ),
    MIS_UNAVAILABLE(
            "MIS is temporarily unavailable.",
            HttpStatus.SERVICE_UNAVAILABLE
    );

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionName(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return this.name();
    }

}
