package com.github.essien.banquemisr.irrigation.rest;

import com.github.essien.banquemisr.irrigation.out.PageOutput;
import com.github.essien.banquemisr.irrigation.out.Response;
import com.github.essien.banquemisr.irrigation.out.Status;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
public class RestUtil {

    static ResponseEntity<Response> created(String name, Object data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.builder().withStatus(Status.success).withMessage(createdPhrase(name)).withData(data).build());
    }

    private static String createdPhrase(String what) {
        return String.format("Okay, %s was successfully created.", what);
    }

    static ResponseEntity<Response> updated(String name, Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder().withStatus(Status.success)
                .withMessage(updatedPhrase(name)).withData(data).build());
    }

    private static String updatedPhrase(String what) {
        return String.format("Okay, %s was successfully updated.", what);
    }

    static ResponseEntity<Response> retrievedMany(String singular, String plural, PageOutput pageOutput) {
        return response(HttpStatus.OK, Status.success, describeList(pageOutput.getElements(), singular, plural), pageOutput);
    }

    private static String describeList(List<?> dtos, String singular, String plural) {
        return String.format("Okay, %s %s found.",
                             dtos.isEmpty() ? "no"
                             : dtos.size() == 1 ? 1
                               : dtos.size(),
                             dtos.size() > 1 ? plural : singular);
    }

    static ResponseEntity<Response> response(HttpStatus httpStatus, Status status, String message, Object data) {
        return ResponseEntity.status(httpStatus)
                .body(Response.builder().withStatus(status).withMessage(message).withData(data).build());
    }
}
