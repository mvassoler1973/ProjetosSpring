package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int multiploNumero;

	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.multiploNumero = constraintAnnotation.numero();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		if (value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.multiploNumero);
			var resto = valorDecimal.remainder(multiploDecimal);
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
		}
		return valido;
	}

}
