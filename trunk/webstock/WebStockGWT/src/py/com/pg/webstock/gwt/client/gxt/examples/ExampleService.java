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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

@RemoteServiceRelativePath("service")
public interface ExampleService extends RemoteService {

  PagingLoadResult<Post> getPosts(PagingLoadConfig config);

  /**
   * Returns the music root folder with all child references.
   * 
   * @return the root folder
   */
  FolderDto getMusicRootFolder();

  List<BaseDto> getMusicFolderChildren(FolderDto folder);

  List<Photo> getPhotos();

  PagingLoadResult<Stock> getStocks(FilterPagingLoadConfig config);

}
