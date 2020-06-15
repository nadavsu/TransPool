package api.components.form;

import api.components.TransPoolController;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class FormController implements Form {
    protected RequiredFieldValidator requiredFieldValidator;
    protected TransPoolController transpoolController;
    protected BooleanProperty fileLoaded;


    public FormController() {
        requiredFieldValidator = new RequiredFieldValidator("Required field");
        fileLoaded = new SimpleBooleanProperty(false);
    }

    public void setTransPoolController(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
    }
    public void initialize() {
        setValidations();
    }
}
