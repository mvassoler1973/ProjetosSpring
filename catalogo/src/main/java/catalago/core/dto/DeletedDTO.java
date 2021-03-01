package catalago.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import catalago.core.BaseEntityModel;

public class DeletedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("mensagem")
	private String message;

	@JsonProperty("tabela")
	private String table;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("data_hora")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime dateTime;

	public DeletedDTO(String table, Long id) {
		this.table = table;
		this.id = id;
		this.message = "Registro excluído do sistema.";
		this.dateTime = LocalDateTime.now();
	}

	public static DeletedDTO setNewDeletedDTO(BaseEntityModel baseEntityModel) {
		return new DeletedDTO(baseEntityModel.getClass().getSimpleName(), baseEntityModel.getId());
	}

	public String getMessage() {
		return this.message;
	}

	public String getTable() {
		return this.table;
	}

	public Long getId() {
		return this.id;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	@JsonProperty("mensagem")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("tabela")
	public void setTable(String table) {
		this.table = table;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("data_hora")
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
