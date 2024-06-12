package application.controller;

import org.springframework.http.ResponseEntity;

public interface CRUDController<T> {
	
	public ResponseEntity<?> getAll();
	public ResponseEntity<?> getById(Long id);
	public ResponseEntity<?> save(T object);
	public ResponseEntity<?> update(T object);
	public ResponseEntity<?> delete(Long id);
	
}
