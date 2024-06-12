package application.service;

import java.util.List;

public interface CRUDService<T> {

	public List<T> getAll();
	public T getById(Long id);
	public T save(T object) throws Exception;
	public T update(T object) throws Exception;
	public boolean delete(Long id);
	
}
