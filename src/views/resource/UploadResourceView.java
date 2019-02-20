package views.resource;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.MessageBox;
import meta.converters.MetaDescriptionConverter;
import meta.model.MetaDescription;
import services.OracleService;

public class UploadResourceView extends JPanel {

	public UploadResourceView() {

		JFileChooser metaDescriptorChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
		metaDescriptorChooser.setFileFilter(filter);
		metaDescriptorChooser.showOpenDialog(this);

		if (metaDescriptorChooser.getSelectedFile() == null)
			return;

		MetaDescriptionConverter metaDescriptionConverter = new MetaDescriptionConverter();
		MetaDescription metaDescription = null;
		try {
			metaDescription = metaDescriptionConverter.fileToMetaDescription(metaDescriptorChooser.getSelectedFile());
		} catch (IOException e1) {
			MessageBox messageBox = new MessageBox(new JFrame(), e1.getMessage());
			messageBox.setVisible(true);
		}

		OracleService.addInformationResource(metaDescription);
	}

}
