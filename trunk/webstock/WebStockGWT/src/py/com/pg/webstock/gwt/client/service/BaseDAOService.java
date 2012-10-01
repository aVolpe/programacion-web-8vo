package py.com.pg.webstock.gwt.client.service;

import java.util.List;

import py.com.pg.webstock.entities.BaseEntity;

import com.google.gwt.user.client.rpc.RemoteService;

public interface BaseDAOService<T extends BaseEntity> extends RemoteService {

	public T get(int id);

	public List<T> getEntidades();

	public T add(T entidad);

	public T remove(T entidad);

	public T remove(int id);

	public T update(T entidad);

	public Long getCount();

	public List<T> getByExample(T example);

	// dale comentamos por el momentom!
	
//	BaseEntity set(int id);
}
