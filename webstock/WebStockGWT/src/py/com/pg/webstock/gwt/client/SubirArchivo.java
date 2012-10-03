package py.com.pg.webstock.gwt.client;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
//import gwtupload.client.IUploader.Utils;
import gwtupload.client.MultiUploader;
import py.com.pg.webstock.gwt.client.service.ClienteService;
import py.com.pg.webstock.gwt.client.service.ClienteServiceAsync;
import py.com.pg.webstock.gwt.client.service.PagoService;
import py.com.pg.webstock.gwt.client.service.PagoServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.xml.client.Document;
//import com.google.gwt.xml.client.XMLParser;
import com.sencha.gxt.widget.core.client.info.Info;


public class SubirArchivo implements IsWidget {

	private VerticalPanel panel;

	PagoServiceAsync pagoService = GWT.create(PagoService.class);
	ClienteServiceAsync clienteService = GWT.create(ClienteService.class);

	public Widget asWidget() {
		if (panel == null) {

			panel = new VerticalPanel();
			 
			//agregue ahora
			//panel.setScrollMode(Scroll.AUTO);
	 
			panel.setSpacing(10);
			
			//agregue ahora
			//panel.setHorizontalAlign(HorizontalAlignment.RIGHT);
	 
			MultiUploader uploader = new MultiUploader(FileInputType.LABEL);
			// we can change the internationalization by creating custom Constants
			// file
			
			
			
	 
			uploader.setAvoidRepeatFiles(false);
			uploader.setServletPath("uploader.fileUpload");
			uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
				public void onFinish(IUploader uploader) {
	 
					if (uploader.getStatus() == Status.SUCCESS) {
	 
						String response = uploader.getServerResponse();
	 
						if (response != null) {
							//Document doc = XMLParser.parse(response);
							//String message = Utils.getXmlNodeValue(doc, "mensaje");
							//String finished = Utils
							//.getXmlNodeValue(doc, "finalizado");
							
							Info.display("Archivo", "Guardado");
							//Window.alert("Respuesta del servidor: \n" + message + "\n"
								//	+ "finalizado: " + finished);
						} else {
							//Window.alert("Respuesta del servidor inaccesible");
							Info.display("Archivo", "No ha podido ser procesado");
						}
	 
						// uploader.reset();
					} else {
						Window.alert("Estado de la carga: \n" + uploader.getStatus());
					}
	 
				}
			});
	 
			panel.add(uploader);
	 
			

		}
		return panel;
	}


}