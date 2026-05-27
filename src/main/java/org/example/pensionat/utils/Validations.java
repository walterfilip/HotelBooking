package org.example.pensionat.utils;
import org.example.pensionat.error.BadRequestException;
import java.time.LocalDate;

public class Validations {

    public static void validateDateRange(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new BadRequestException("Slutdatum måste vara efter startdatum");
        }
    }
}
