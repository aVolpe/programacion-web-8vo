/**
 * Sencha GXT 3.0.0b - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package py.com.pg.webstock.gwt.client.gxt.examples;

import java.util.List;

import py.com.pg.webstock.gwt.client.gxt.examples.model.FileModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async <code>FileService<code> interface.
 */
public interface FileServiceAsync {

  public void getFolderChildren(FileModel model, AsyncCallback<List<FileModel>> children);

  // public void getFolderChildren(RemoteSortTreeLoadConfig loadConfig,
  // AsyncCallback<List<FileModel>> children);

}
