package catalago.core;

import org.springframework.data.domain.Page;

import catalago.core.dto.PageDTO;

public class BaseConverter {

	public static <T> PageDTO<T> toPageVO(Page<T> page) {
		return new PageDTO(page);
	}

}
