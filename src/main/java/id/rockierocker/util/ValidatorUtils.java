package id.rockierocker.util;

import id.rockierocker.exception.BadRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
public class ValidatorUtils {
    public static String getFirstFoundErrorBindingResult(BindingResult bindingResult){
        String errorMessage="";
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                return error.getDefaultMessage();
            }
        }
        return errorMessage;
    }

    public static void throwIfError(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                throw new BadRequest(error.getDefaultMessage());
            }
        }
    }


}
