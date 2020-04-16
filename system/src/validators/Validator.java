package validators;

import exceptions.data.time.InvalidHoursException;
import exceptions.data.time.InvalidMinutesException;
import exceptions.data.time.InvalidTimeException;
import exceptions.file.UnsupportedFileException;

public class Validator {

    public void validateTime(int hour, int min) throws InvalidTimeException {
        if (hour < 0 || hour >= 24) {
            throw new InvalidHoursException();
        }
        if (min < 0 || min >= 60) {
            throw new InvalidMinutesException();
        }
    }

    public void validateFileType(String str, String fileType) throws UnsupportedFileException {
        int dotIndex = str.lastIndexOf('.');
        if (dotIndex == -1) {
            throw new UnsupportedFileException();
        }
        if (!str.substring(dotIndex).toUpperCase().equals(fileType)) {
            throw new UnsupportedFileException();
        }
    }



}
