package api.components.form;

import api.components.TransPoolController;
import com.jfoenix.validation.RequiredFieldValidator;

public abstract class FormController implements Form {
    protected RequiredFieldValidator requiredFieldValidator;
    protected TransPoolController transpoolController;


    public FormController() {
        requiredFieldValidator = new RequiredFieldValidator("Required field");
    }

    public void setTransPoolController(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
    }

    public void initialize() {
        setValidations();
    }
}
