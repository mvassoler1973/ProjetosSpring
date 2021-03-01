package catalago.core.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO implements Serializable {

	private static final long serialVersionUID = -3448636400238178910L;

	private String field;
	private String exception;
	private String message;
	private HttpStatus statusCode;
	private int code;

}
