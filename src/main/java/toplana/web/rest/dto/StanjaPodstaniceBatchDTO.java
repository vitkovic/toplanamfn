package toplana.web.rest.dto;

import java.util.List;

import toplana.domain.StanjaPodstanice;

public class StanjaPodstaniceBatchDTO {
	List<StanjaPodstanice> stanjaPodstanice;
	
	

	public StanjaPodstaniceBatchDTO() {
		super();
	}

	public List<StanjaPodstanice> getStanjaPodstanice() {
		return stanjaPodstanice;
	}

	public void setStanjaPodstanice(List<StanjaPodstanice> stanjaPodstanice) {
		this.stanjaPodstanice = stanjaPodstanice;
	}	

}
