package catalago.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import catalago.core.BaseEntityModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode
public class Product extends BaseEntityModel implements Serializable {

	private static final long serialVersionUID = 7294916655911434399L;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 500, nullable = false)
	private String description;

	@Column(name = "price", length = 15, precision = 3, nullable = false)
	private BigDecimal price;

}
