package py.com.pg.webstock.gwt.client.service;

import java.util.List;

import py.com.pg.webstock.entities.BaseEntity;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BaseDAOServiceAsync<T extends BaseEntity> {

	void get(int id, AsyncCallback<T> callback);
	
//	void set(int id, AsyncCallback<T> callback);

	void getEntidades(AsyncCallback<List<T>> callback);

	void add(T entidad, AsyncCallback<T> callback);

	void remove(int id, AsyncCallback<T> callback);

	void remove(T entidad, AsyncCallback<T> callback);

	void update(T entidad, AsyncCallback<T> callback);

	void getCount(AsyncCallback<Long> callback);

	void getByExample(T example, AsyncCallback<List<T>> callback);

}
