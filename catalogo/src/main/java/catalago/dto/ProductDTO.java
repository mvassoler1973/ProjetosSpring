package catalago.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	@NotEmpty(message = "Nome do produto é requerido.")
	@Size(max = 100, message = "Tamanho do nome do produto excedeu a 100 caracteres.")
	@JsonProperty("name")
	private String name;

	@NotEmpty(message = "Descrição do produto é requerida.")
	@Size(max = 500, message = "Tamanho da descrição do produto excedeu a 500 caracteres.")
	@JsonProperty("description")
	private String description;

	@NotNull(message = "Preço do produto é requerido.")
	@JsonProperty("price")
	private BigDecimal price;

}
