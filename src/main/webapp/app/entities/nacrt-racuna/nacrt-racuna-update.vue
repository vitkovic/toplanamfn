// @ts-nocheck
<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.nacrtRacuna.home.createOrEditLabel" v-text="$t('toplanaApp.nacrtRacuna.home.createOrEditLabel')">Create or edit a NacrtRacuna</h2>
                <div>
                    <div class="form-group" v-if="nacrtRacuna.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="nacrtRacuna.id" readonly />
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.datumRacuna')" for="datepicker-dateformat2">Datum Racuna</label>
                         <!--
                          <div class="d-flex"> 
                                <date-picker format="DD.MM.YYYY" v-model="nacrtRacuna.datumRacuna" type="date"
                                    required  
                                    :class="{'valid': !$v.nacrtRacuna.datumRacuna.$invalid, 'invalid': $v.nacrtRacuna.datumRacuna.$invalid }"                                                                                        
                                    >
                                </date-picker>                                                               
                            </div>  
                        -->

                        
                        <b-form-datepicker
                            id="datepicker-dateformat2"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="nacrtRacuna.datumRacuna"
                            locale="sr" required 
                            :class="{'valid': !$v.nacrtRacuna.datumRacuna.$invalid, 'invalid': $v.nacrtRacuna.datumRacuna.$invalid }"
                        ></b-form-datepicker>




                          
                        </div>
                        <div class="col-4">
                             <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.period')" for="nacrt-racuna-period">Period</label>
                            <input type="text" class="form-control" name="period" id="nacrt-racuna-period"
                                :class="{'valid': !$v.nacrtRacuna.period.$invalid, 'invalid': $v.nacrtRacuna.period.$invalid }" v-model="$v.nacrtRacuna.period.$model"  
                                v-on:blur="checkForm" required/>
                            <div v-if="$v.nacrtRacuna.period.$anyDirty && $v.nacrtRacuna.period.$invalid">
                                <small class="form-text text-danger" v-if="!$v.nacrtRacuna.period.required" v-text="$t('entity.validation.required')">
                                    This field is required.
                                </small>
                            </div>        
                        </div>
                        <div class="col-4">
                            <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.valutaPlacanja')" for="datepicker-dateformat3">Valuta Placanja</label>
                                
                                <b-form-datepicker
                                    id="datepicker-dateformat3"
                                    :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                                    v-model="nacrtRacuna.valutaPlacanja"
                                    locale="sr" required
                                    :class="{'valid': !$v.nacrtRacuna.valutaPlacanja.$invalid, 'invalid': $v.nacrtRacuna.valutaPlacanja.$invalid }"
                                ></b-form-datepicker>                                                       
                        </div>
                    </div>

                    <div class="row" style="margin-top:10pt;margin-bottom:10pt">
                        <div class="col-2">
                            <label class="font-weight-bold" v-text="$t('toplanaApp.nacrtRacuna.proknjizen')">proknjizen</label>
                        </div>
                        <div class="col-2">
                            <span class="font-weight-bold">
                                {{ nacrtRacuna.proknjizen ? 'Да' : 'Не' }}
                                </span>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-6">
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.cenaKwh')" >Cena Kwh</label>
                                </div>
                                <div class="col-2">
                                    {{nacrtRacuna.cenaKwh}}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.cenaFix')" >Cena Kwh</label>
                                </div>
                                <div class="col-6">
                                    {{nacrtRacuna.cenaFix}}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.cenaFixIskljucen')" >Cena Kwh</label>
                                </div>
                                <div class="col-2">
                                    {{nacrtRacuna.cenaFixIskljucen}}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.cenaOdrzavanje')" >Cena Kwh</label>
                                </div>
                                <div class="col-6">
                                    {{nacrtRacuna.cenaOdrzavanje}}
                                </div>
                            </div>                            
                        </div>
                        <div class="col-6">
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.popust')" >Cena Kwh</label>
                                </div>
                                <div class="col-2">
                                    {{nacrtRacuna.popust}}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.pdv1')" >Cena Kwh</label>
                                </div>
                                <div class="col-6">
                                    {{nacrtRacuna.pdv1}}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.pdv2')" >Cena Kwh</label>
                                </div>
                                <div class="col-6">
                                    {{nacrtRacuna.pdv2}}
                                </div>
                            </div>   
                            <div class="row">
                                <div class="col-6">
                                    <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.cenaOStalo')" >Cena Kwh</label>
                                </div>
                                <div class="col-2">
                                    {{nacrtRacuna.cenaOStalo}}
                                </div>
                            </div>                        
                        </div>
                    </div>



                   <!--

                    <div class="row" style="margin-top:20px" v-for="stanja in nacrtRacuna.stanjaPodstaniceZaRacune">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title" 
                                    v-text="$t('toplanaApp.nacrtRacuna.podstanicaBroj', {'broj': stanja.podstanica.broj})"
                                    ></h5>

                                    <table class="table table-striped">
                                        <tbody>    
                                        <tr class="row">
                                            <th class="col-3" v-text="$t('toplanaApp.nacrtRacuna.staroStanjePodstanice')"></th>
                                            <td class="col-3" >{{stanja.staroStanje.stanje}}</td>
                                            <th class="col-3"  v-text="$t('toplanaApp.nacrtRacuna.datumOcitavanja')"></th>
                                            <td class="col-3" >{{stanja.staroStanje.datumOcitavanja ? $d(Date.parse(stanja.staroStanje.datumOcitavanja), 'short') : ''}}</td>                                            
                                        </tr>
                                        <tr class="row">
                                            <th class="col-3"  v-text="$t('toplanaApp.nacrtRacuna.novoStanjePodstanice')"></th>
                                            <td class="col-3" >{{stanja.novoStanje.stanje}}</td>
                                            <th  class="col-3" v-text="$t('toplanaApp.nacrtRacuna.datumOcitavanja')"></th>
                                            <td class="col-3" >{{stanja.novoStanje.datumOcitavanja ? $d(Date.parse(stanja.novoStanje.datumOcitavanja), 'short') : ''}}</td>                                            
                                        </tr>
                                        <tr class="row">
                                            <th class="col-3"  v-text="$t('toplanaApp.nacrtRacuna.ukupnaPovrsina')"></th>
                                            <td class="col-3" >{{stanja.ukupnaPovrsina}}</td>
                                            <th class="col-3"  ></th>
                                            <td class="col-3" ></td>                                            
                                        </tr>
                                        <tr class="row">
                                            <th class="col-3"  v-text="$t('toplanaApp.nacrtRacuna.utrosakPoM2')"></th>
                                            <td class="col-3" >{{stanja.utrosakPoM2}}</td>
                                            <th class="col-3"  ></th>
                                            <td class="col-3" ></td>                                            
                                        </tr>
                                        </tbody>
                                    </table>                                    
                                </div>
                            </div>
                        </div>  
                    </div>
                    <div class="row">
                        <div class="col-8">
                            <label class="font-weight-bold  float-right form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnoStanjeNovo')"></label>
                        </div>
                        <div class="col-1">
                            <span class=" float-right">{{nacrtRacuna.ukupnoStanjeNovo}}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-8">
                            <label class="font-weight-bold float-right form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnoStanjeStaro')"></label>
                        </div>
                        <div class="col-1">
                            <span class=" float-right">{{nacrtRacuna.ukupnoStanjeStaro}}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-8"> 
                            <label class="font-weight-bold  float-right form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnaPotrosnja')"></label>
                        </div>
                        <div class="col-1">
                            <span class=" float-right">{{nacrtRacuna.potrosnja}}</span>
                        </div>
                    </div>
-->

                    <div class="row" style="margin-top:20px" v-for="stanja in nacrtRacuna.stanjaPodstaniceZaRacune">
                        <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title" 
                                v-text="stanja.podstanica.naziv"
                                ></h5>
                                <div class="row">                                    
                                    <div class="col-3">                                        
                                        <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.staroStanjePodstanice')" for="nacrt-racuna-staroStanjePodstanice">Staro Stanje Podstanice</label>
                                        <input type="number" class="form-control" name="staroStanjePodstanice" id="nacrt-racuna-staroStanjePodstanice"
                                            :class="{'valid': stanja.staroStanje.stanje, 'invalid': !stanja.staroStanje.stanje }" v-model.number="stanja.staroStanje.stanje"
                                            v-on:blur="checkForm"  required/> 
                                    </div>
                                    <div class="col-3">
                                        <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.novoStanjePodstanice')" for="nacrt-racuna-novoStanjePodstanice">Novo Stanje Podstanice</label>
                                        <input type="number" class="form-control" name="novoStanjePodstanice" id="nacrt-racuna-novoStanjePodstanice"
                                             :class="{'valid': stanja.novoStanje.stanje, 'invalid': !stanja.novoStanje.stanje }" v-model.number="stanja.novoStanje.stanje" 
                                             v-on:blur="checkForm" required/>                                             
                                    </div>
                                    <div class="col-3">
                                         <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnaPovrsina')" for="nacrt-racuna-ukupnaPovrsina">Ukupna Povrsina</label>
                                        <input type="number" class="form-control" name="ukupnaPovrsina" id="nacrt-racuna-ukupnaPovrsina"
                                            :class="{'valid': stanja.ukupnaPovrsina, 'invalid': !stanja.ukupnaPovrsina }" v-model.number="stanja.ukupnaPovrsina" 
                                            v-on:blur="checkForm" required/>
                                    </div>
                                    <div class="col-3">
                                        <label class="form-control-label" v-text="$t('toplanaApp.nacrtRacuna.utrosakPoM2')" for="nacrt-racuna-utrosakPoM2">Utrosak Po M 2</label>
                                        <input type="number" class="form-control" name="utrosakPoM2" id="nacrt-racuna-utrosakPoM2"
                                            :class="{'valid': stanja.utrosakPoM2, 'invalid': !stanja.utrosakPoM2 }" v-model.number="stanja.utrosakPoM2" 
                                            v-on:blur="checkForm" required/>
                                    </div>
                                </div>
                                <div class="row">                                    
                                    <div class="col-3"> 
                                        <span v-text="$t('toplanaApp.nacrtRacuna.ocitano')"></span> {{stanja.novoStanje.datumOcitavanja ? $d(Date.parse(stanja.staroStanje.datumOcitavanja), 'short') : ''}}
                                    </div>
                                    <div class="col-3"> 
                                        <span v-text="$t('toplanaApp.nacrtRacuna.ocitano')"></span> {{stanja.novoStanje.datumOcitavanja ? $d(Date.parse(stanja.novoStanje.datumOcitavanja), 'short') : ''}}
                                    </div>
                                </div>    
                            </div>
                        </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-8">
                            <label class="font-weight-bold  float-right form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnoStanjeNovo')"></label>
                        </div>
                        <div class="col-1">
                            <span class=" float-right">{{nacrtRacuna.ukupnoStanjeNovo}}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-8">
                            <label class="font-weight-bold float-right form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnoStanjeStaro')"></label>
                        </div>
                        <div class="col-1">
                            <span class=" float-right">{{nacrtRacuna.ukupnoStanjeStaro}}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-8"> 
                            <label class="font-weight-bold  float-right form-control-label" v-text="$t('toplanaApp.nacrtRacuna.ukupnaPotrosnja')"></label>
                        </div>
                        <div class="col-1">
                            <span class=" float-right">{{nacrtRacuna.potrosnja}}</span>
                        </div>
                    </div>

                </div>
                <div>               
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button><!--:disabled="$v.nacrtRacuna.$invalid || isFormDisabled || nacrtRacuna.proknjizen "-->
                    <button type="submit" id="save-entity"  class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                    <button v-if="nacrtRacuna.id" type="button" id="knjizenje" class="btn btn-primary" 
                        :disabled="nacrtRacuna.proknjizen" v-on:click="knjizenje()">
                        <span v-text="$t('entity.action.knjizenje')"></span>
                    </button>
                    <button v-if="nacrtRacuna.id" type="button" id="stampanje" class="btn btn-primary" 
                        v-on:click="stampanje()">
                        <span v-text="$t('entity.action.stampanje')"></span>
                    </button>
                     <button v-if="nacrtRacuna.id" type="button" id="smail" class="btn btn-primary" 
                        v-on:click="smail()" :disabled="true">
                        <span v-text="$t('entity.action.smail')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./nacrt-racuna-update.component.ts">
</script>
