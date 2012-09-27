/**
 * Sencha GXT 3.0.0b - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package py.com.pg.webstock.gwt.client.gxt.examples.model;

import java.io.Serializable;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;

public class BaseDto implements Serializable, TreeStore.TreeNode<BaseDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;

	protected BaseDto() {

	}

	public BaseDto(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public BaseDto getData() {
		return this;
	}

	@Override
	public List<? extends TreeNode<BaseDto>> getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return name != null ? name : super.toString();
	}

}
