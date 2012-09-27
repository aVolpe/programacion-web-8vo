package py.com.pg.webstock.gwt.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface Recursos extends ClientBundle {

	@Source("icon_banco.gif")
	ImageResource iconBanco();

	@Source("add.gif")
	ImageResource iconAdd();

	@Source("delete.gif")
	ImageResource iconDelete();

	@Source("open.png")
	ImageResource iconOpen();

	@Source("list-items.gif")
	ImageResource iconListItems();

	@Source("save.png")
	ImageResource iconSave();

	@Source("new.png")
	ImageResource iconEdit();

	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource editorButtonLeft();

	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource editorButtonCenter();

	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource editorButtonRight();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource editorButtonBackground();


	public static class Util {
		private static Recursos INSTANCE;

		public static Recursos getInstance() {
			if (INSTANCE == null)
				INSTANCE = GWT.create(Recursos.class);
			return INSTANCE;
		}
	}
}
