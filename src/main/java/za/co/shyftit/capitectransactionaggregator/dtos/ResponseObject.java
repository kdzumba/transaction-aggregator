package za.co.shyftit.capitectransactionaggregator.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponseObject<T>{
    private T result;
    private HttpStatus httpStatus;
    private String message;
}
