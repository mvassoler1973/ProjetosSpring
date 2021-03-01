package catalago.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import catalago.core.constants.ValidationConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	@ApiModelProperty(hidden = true)
	private Long id;

	@NotEmpty(message = ValidationConstants.FIELD_REQUIRED)
	@Size(max = 100, message = ValidationConstants.FIELD_MAX_LENGHT)
	@JsonProperty("name")
	private String name;

	@NotEmpty(message = ValidationConstants.FIELD_REQUIRED)
	@Size(max = 500, message = ValidationConstants.FIELD_MAX_LENGHT)
	@JsonProperty("description")
	private String description;

	@NotNull(message = ValidationConstants.FIELD_REQUIRED)
	@JsonProperty("price")
	private BigDecimal price;

}
