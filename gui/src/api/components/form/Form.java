package api.components.form;

import exception.data.TransPoolDataException;

public interface Form {
    void clear();
    void setValidations();
    boolean allRequiredFieldsFilled();
    void submit();
}
