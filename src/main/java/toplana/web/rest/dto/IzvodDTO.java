package toplana.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Dokument")
public class IzvodDTO {
	
	@JacksonXmlProperty(localName = "Zaglavlje")
	private IzvodZaglavljeDTO Zaglavlje;
	@JacksonXmlProperty(localName = "Zbirni")
	private IzvodZbirniDTO Zbirni;
	
	@JacksonXmlProperty(localName = "Stavka")
	@JacksonXmlElementWrapper(useWrapping = false)
    private List<IzvodStavkaDTO> Stavka;

	public IzvodZaglavljeDTO getZaglavlje() {
		return Zaglavlje;
	}

	public void setZaglavlje(IzvodZaglavljeDTO zaglavlje) {
		Zaglavlje = zaglavlje;
	}

	public IzvodZbirniDTO getZbirni() {
		return Zbirni;
	}

	public void setZbirni(IzvodZbirniDTO zbirni) {
		Zbirni = zbirni;
	}

	public List<IzvodStavkaDTO> getStavka() {
		return Stavka;
	}

	public void setStavka(List<IzvodStavkaDTO> stavka) {
		Stavka = stavka;
	}
	
	
	

}
