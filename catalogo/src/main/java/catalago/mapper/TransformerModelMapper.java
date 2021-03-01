package catalago.mapper;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

public interface TransformerModelMapper<DTO, ENTITY> {

	default DTO toDtoMapper(ENTITY entity, Class<DTO> dtoReference) {
		return this.getModelMapper().map(entity, dtoReference);
	}

	default ENTITY toDomainMapper(DTO dto, Class<ENTITY> entityReference) {
		return this.getModelMapper().map(dto, entityReference);
	}

	default Page<DTO> toDtoPagedMapper(Page<ENTITY> entityPaged, Class<DTO> dtoReference) {
		return entityPaged.map((entity) -> {
			return this.toDtoMapper(entity, dtoReference);
		});
	}

	default Page<ENTITY> toEntityPagedMapper(Page<DTO> dtoPaged, Class<ENTITY> entityReference) {
		return dtoPaged.map((dto) -> {
			return this.toDomainMapper(dto, entityReference);
		});
	}

	default ModelMapper getModelMapper() {
		ModelMapper model = new ModelMapper();
		model.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		model.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return model;
	}
}
