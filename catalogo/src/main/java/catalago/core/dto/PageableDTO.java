package catalago.core.dto;

public class PageableDTO {
	private int size;
	private int page;
	private Boolean hasNext;
	private Boolean hasPrevious;

	PageableDTO(int size, int page, Boolean hasNext, Boolean hasPrevious) {
		this.size = size;
		this.page = page;
		this.hasNext = hasNext;
		this.hasPrevious = hasPrevious;
	}

	public static PageableDTO.PageableDTOBuilder builder() {
		return new PageableDTO.PageableDTOBuilder();
	}

	public int getSize() {
		return this.size;
	}

	public int getPage() {
		return this.page;
	}

	public Boolean getHasNext() {
		return this.hasNext;
	}

	public Boolean getHasPrevious() {
		return this.hasPrevious;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setHasPrevious(Boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hasNext == null) ? 0 : hasNext.hashCode());
		result = prime * result + ((hasPrevious == null) ? 0 : hasPrevious.hashCode());
		result = prime * result + page;
		result = prime * result + size;
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
		PageableDTO other = (PageableDTO) obj;
		if (hasNext == null) {
			if (other.hasNext != null)
				return false;
		} else if (!hasNext.equals(other.hasNext))
			return false;
		if (hasPrevious == null) {
			if (other.hasPrevious != null)
				return false;
		} else if (!hasPrevious.equals(other.hasPrevious))
			return false;
		if (page != other.page)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PageableDTO(size=" + this.getSize() + ", page=" + this.getPage() + ", hasNext=" + this.getHasNext()
				+ ", hasPrevious=" + this.getHasPrevious() + ")";
	}

	public static class PageableDTOBuilder {
		private int size;
		private int page;
		private Boolean hasNext;
		private Boolean hasPrevious;

		PageableDTOBuilder() {
		}

		public PageableDTO.PageableDTOBuilder size(int size) {
			this.size = size;
			return this;
		}

		public PageableDTO.PageableDTOBuilder page(int page) {
			this.page = page;
			return this;
		}

		public PageableDTO.PageableDTOBuilder hasNext(Boolean hasNext) {
			this.hasNext = hasNext;
			return this;
		}

		public PageableDTO.PageableDTOBuilder hasPrevious(Boolean hasPrevious) {
			this.hasPrevious = hasPrevious;
			return this;
		}

		public PageableDTO build() {
			return new PageableDTO(this.size, this.page, this.hasNext, this.hasPrevious);
		}

		@Override
		public String toString() {
			return "PageableDTO.PageableDTOBuilder(size=" + this.size + ", page=" + this.page + ", hasNext="
					+ this.hasNext + ", hasPrevious=" + this.hasPrevious + ")";
		}
	}

}
