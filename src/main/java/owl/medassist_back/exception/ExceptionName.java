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
