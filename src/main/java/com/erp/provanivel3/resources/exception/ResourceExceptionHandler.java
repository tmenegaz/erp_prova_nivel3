package com.erp.provanivel3.resources.exception;

import com.erp.provanivel3.domain.exception.CondicaoException;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.services.exceptions.DataIntegrityException;
import com.erp.provanivel3.services.exceptions.IllegalArgumentException;
import com.erp.provanivel3.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(
				System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(),
				"Não encontrado",
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(
				System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(),
				"Integridade de dados",
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	@ExceptionHandler(CondicaoException.class)
	public ResponseEntity<StandardError> notAcceptable(CondicaoException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_ACCEPTABLE.value(), "Não é possível incluir o produto desativado no pedido", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

	@ExceptionHandler(DescontoException.class)
	public ResponseEntity<StandardError> descontoException(DescontoException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Tentativa ilegal", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> outOfCatalogException(IllegalArgumentException e, HttpServletRequest request) {

		StandardError err = new StandardError(
				System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(),
				"Tentativa ilegal",
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
