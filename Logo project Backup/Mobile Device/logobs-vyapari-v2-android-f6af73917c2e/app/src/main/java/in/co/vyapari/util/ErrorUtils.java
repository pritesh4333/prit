package in.co.vyapari.util;

import java.io.IOException;
import java.lang.annotation.Annotation;

import in.co.vyapari.middleware.ServiceCreator;
import in.co.vyapari.model.response.ErrorModel;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by bekir on 27/09/16.
 */
public class ErrorUtils {

    public static ErrorModel parseError(Response<?> response) {
        Converter<ResponseBody, ErrorModel> converter = ServiceCreator.getClient().responseBodyConverter(ErrorModel.class, new Annotation[0]);

        ErrorModel error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorModel("Error.");
        } catch (NullPointerException e) {
            return new ErrorModel("Error.");
        }

        return error;
    }
}