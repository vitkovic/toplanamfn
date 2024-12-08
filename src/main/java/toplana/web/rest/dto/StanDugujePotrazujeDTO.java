package toplana.web.rest.dto;

import toplana.domain.OstaliRacuni;
import toplana.domain.Stan;

public class StanDugujePotrazujeDTO {
	private Stan stan;
	private DugujePotrazujeDTO dugujePotrazujeDto;
	private OstaliRacuni ostaliRacuni;
	
	public StanDugujePotrazujeDTO() {
	}
	
	
	public StanDugujePotrazujeDTO(Stan stan, DugujePotrazujeDTO dugujePotrazujeDto) {
		super();
		this.stan = stan;
		this.dugujePotrazujeDto = dugujePotrazujeDto;
	}
	
	public StanDugujePotrazujeDTO(OstaliRacuni or, DugujePotrazujeDTO dugujePotrazujeDto) {
		super();
		this.ostaliRacuni = or;
		this.dugujePotrazujeDto = dugujePotrazujeDto;
	}


	public Stan getStan() {
		return stan;
	}
	public void setStan(Stan stan) {
		this.stan = stan;
	}
	public DugujePotrazujeDTO getDugujePotrazujeDto() {
		return dugujePotrazujeDto;
	}
	public void setDugujePotrazujeDto(DugujePotrazujeDTO dugujePotrazujeDto) {
		this.dugujePotrazujeDto = dugujePotrazujeDto;
	}


	public OstaliRacuni getOstaliRacuni() {
		return ostaliRacuni;
	}


	public void setOstaliRacuni(OstaliRacuni ostaliRacuni) {
		this.ostaliRacuni = ostaliRacuni;
	}
	
	

}
