package py.com.una.pol.rest.repo;

import java.util.List;

import py.com.una.pol.rest.domain.EntidadBase;

public interface BaseDAO<T extends EntidadBase> {

	T getById(int id);

	List<T> getAll();
	
	List<T> getByExample(T ejemplo);

	T guardar(T entidad);

	T eliminar(int id);

	T eliminar(T entidad);

}
