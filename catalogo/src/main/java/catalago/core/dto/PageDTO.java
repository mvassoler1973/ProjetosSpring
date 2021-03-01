package catalago.core.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageDTO<E> implements Serializable {

	@JsonProperty("content")
	private List<E> content;
	private PageableDTO pageable;
	private long total;

	public PageDTO(Page<E> page) {
		if (page.hasContent()) {
			this.content = page.getContent();
		}

		if (!page.getPageable().isUnpaged()) {
			this.pageable = PageableDTO.builder().size(page.getPageable().getPageSize())
					.page(page.getPageable().getPageNumber()).hasNext(page.hasNext()).hasPrevious(page.hasPrevious())
					.build();
		}

		this.total = page.getTotalElements();
	}

	public List<E> getContent() {
		return this.content;
	}

	public PageableDTO getPageable() {
		return this.pageable;
	}

	public long getTotal() {
		return this.total;
	}

	@JsonProperty("content")
	public void setContent(List<E> content) {
		this.content = content;
	}

	public void setPageable(PageableDTO pageable) {
		this.pageable = pageable;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	protected boolean canEqual(Object other) {
		return other instanceof PageDTO;
	}

	@Override
	public String toString() {
		return "PageJpaDTO(content=" + this.getContent() + ", pageable=" + this.getPageable() + ", total="
				+ this.getTotal() + ")";
	}

	public PageDTO(List<E> content, PageableDTO pageable, long total) {
		this.content = content;
		this.pageable = pageable;
		this.total = total;
	}

	public PageDTO() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((pageable == null) ? 0 : pageable.hashCode());
		result = prime * result + (int) (total ^ (total >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageDTO other = (PageDTO) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (pageable == null) {
			if (other.pageable != null)
				return false;
		} else if (!pageable.equals(other.pageable))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

}
