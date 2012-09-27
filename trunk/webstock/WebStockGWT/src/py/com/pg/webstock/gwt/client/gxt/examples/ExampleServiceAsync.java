/**
 * Sencha GXT 3.0.0b - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package py.com.pg.webstock.gwt.client.gxt.examples;

import java.util.List;

import py.com.pg.webstock.gwt.client.gxt.examples.model.BaseDto;
import py.com.pg.webstock.gwt.client.gxt.examples.model.FolderDto;
import py.com.pg.webstock.gwt.client.gxt.examples.model.Photo;
import py.com.pg.webstock.gwt.client.gxt.examples.model.Post;
import py.com.pg.webstock.gwt.client.gxt.examples.model.Stock;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface ExampleServiceAsync {

  void getPosts(PagingLoadConfig config, AsyncCallback<PagingLoadResult<Post>> callback);

  void getMusicRootFolder(AsyncCallback<FolderDto> callback);
  
  void getMusicFolderChildren(FolderDto folder, AsyncCallback<List<BaseDto>> callback);
  
  void getPhotos(AsyncCallback<List<Photo>> callback);
  
  void getStocks(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Stock>> callback);
}
