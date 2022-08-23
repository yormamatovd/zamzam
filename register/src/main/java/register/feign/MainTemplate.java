package register.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import register.enums.ApiStatus;
import register.exceptions.NotAcceptableException;
import register.exceptions.SystemException;

@Component
@RequiredArgsConstructor
public class MainTemplate {

    private final InfoTemplate infoTemplate;

    public void checkEmail(String email) {
        try {
            ResponseEntity<Boolean> response = infoTemplate.existEmail(email);
            if (response.getBody() == Boolean.TRUE) throw new NotAcceptableException(ApiStatus.EMAIL_ALREADY_USED);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof NotAcceptableException) throw new NotAcceptableException(ApiStatus.EMAIL_ALREADY_USED);
            else throw new SystemException(ApiStatus.SERVER_ERROR.name());
        }
    }

    public void checkPhone(String phone) {
        try {
            ResponseEntity<Boolean> response = infoTemplate.existPhone(phone);
            if (response.getBody() == Boolean.TRUE)
                throw new NotAcceptableException(ApiStatus.PHONE_NUMBER_ALREADY_USED);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof NotAcceptableException)
                throw new NotAcceptableException(ApiStatus.PHONE_NUMBER_ALREADY_USED);
            else throw new SystemException(ApiStatus.SERVER_ERROR.name());
        }
    }
}
