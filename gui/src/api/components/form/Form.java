package api.components.form;

public interface Form {
    void clear();
    void setValidations();
    boolean isValid();
    void submit();
}
