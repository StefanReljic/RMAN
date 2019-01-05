package components;

import javax.swing.JPanel;

import meta.model.types.InformationResourceType;

public class MetaDescriptionEditor extends JPanel {

	private InformationResourceType informationResourceType;

	public MetaDescriptionEditor(InformationResourceType informationResourceType) {

		this.informationResourceType = informationResourceType;
		createComponent();
	}

	public void createComponent() {

		if (this.informationResourceType == null)
			return;

		switch (informationResourceType) {

		case ORACLE:
			createOracleView();

		case CSV:
			break;
		}

	}

	private void createOracleView() {

		
	}

	public InformationResourceType getInformationResourceType() {
		return informationResourceType;
	}

	public void setInformationResourceType(InformationResourceType informationResourceType) {
		this.informationResourceType = informationResourceType;
	}

}