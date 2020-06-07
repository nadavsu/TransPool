package api.components.form;

import data.transpool.TransPoolData;

public interface Form {
    void clear();
    void setValidations();
    boolean isValid();
    void submit();
}
