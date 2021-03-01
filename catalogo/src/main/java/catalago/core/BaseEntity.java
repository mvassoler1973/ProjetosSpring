package catalago.core;

import java.time.LocalDateTime;

public interface BaseEntity<ID> {

	ID getId();

	LocalDateTime getCreated();

	LocalDateTime getUpdated();

	void setId(ID var1);

	void setCreated(LocalDateTime var1);

	void setUpdated(LocalDateTime var1);

}
