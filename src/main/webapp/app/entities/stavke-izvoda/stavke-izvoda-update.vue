<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stavkeIzvoda.home.knjizenje" v-text="$t('toplanaApp.stavkeIzvoda.home.knjizenje')"></h2>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-1">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.sifraStana')"></span>
                    </div>
                    <div class="col-2">
                        <input type="text" class="form-control" id="pozivOdobrenja" name="pozivOdobrenja"
                               v-model="stavkeIzvoda.pozivOdobrenja"  required
                               :class="{'valid': !$v.stavkeIzvoda.pozivOdobrenja.$invalid, 'invalid': $v.stavkeIzvoda.pozivOdobrenja.$invalid }"/>
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.rasporedjena')"></span>
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold">
                            {{ stavkeIzvoda.rasporedjena ? 'Да' : 'Не' }}
                        </span>
                    </div>
                 </div><hr>
               <div class="row" style="margin-bottom:20px">
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.transakcija.sifraPromene')"></span>
                    </div>
                    <div class="col-3">
                        <select class="form-control" id="transakcija-sifraPromene" name="sifraPromene" 
                        v-model="sifraPromene" required
                        :class="{'valid': !$v.sifraPromene.$invalid, 'invalid': $v.sifraPromene.$invalid }">                        
                            
                            <option v-bind:value="sifraPromene && sifraPromeneOption.id === sifraPromene.id ? sifraPromene : sifraPromeneOption" v-for="sifraPromeneOption in sifraPromenes" :key="sifraPromeneOption.id">{{sifraPromeneOption.sifra}}</option>
                        </select>
                    </div>

  					 <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.racun')"></span>
                    </div>
                    <div class="col-3">                        
                        {{stavkeIzvoda.racunZaduzenja}}
                    </div>

                </div><hr>

               


                <div class="row" style="margin-bottom:20px">
                  
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.zaduzenje')"></span>
                    </div>
                    <div class="col-2">
                        <span v-if="stavkeIzvoda.racunZaduzenja === RACUN_ZADUZENJA">
                              <input type="text" class="form-control" id="iznos" name="iznos"
                               v-model="stavkeIzvoda.iznos"  
                               />    
                        </span> 
                    </div>
                    
                    
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.podatakZaReklamaciju')"></span>
                    </div>
                    <div class="col-2">
                        {{stavkeIzvoda.podatakZaReklamaciju}}
                    </div>
                </div><hr>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-1">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.nazivIMesto')"></span>
                    </div>
                    <div class="col-3">
                        {{stavkeIzvoda.nazivZaduzenja}}
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.odobrenje')"></span>
                    </div>
                    <div class="col-2">       
                         <span v-if="stavkeIzvoda.racunZaduzenja != RACUN_ZADUZENJA">
                            <input type="text" class="form-control" id="iznos" name="iznos"
                               v-model="stavkeIzvoda.iznos"  
                               />   
                        </span>                 
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.pbz')"></span>
                    </div>
                    <div class="col-2">
                        {{stavkeIzvoda.modelPozivaZaduzenja}} {{stavkeIzvoda.pozivZaduzenja}}                                
                    </div>
                </div><hr>
                <div class="row" style="margin-bottom:20px">
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.svrhaDoznake')"></span>
                    </div>
                    <div class="col-3">
                        {{stavkeIzvoda.svrhaDoznake}}
                    </div>
                    <div class="col-2">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.sifraPlacanja')">Sifra placanja</span>
                    </div>
                    <div class="col-2">
                        {{stavkeIzvoda.sifraPlacanja}}
                    </div>
                    <div class="col-2">
                    </div>
                    <div class="col-2">
                    </div> 
                </div><hr>
                
                 <div class="row" style="margin-bottom:20px">
                    <div class="col-1">
                        <span class="font-weight-bold" v-text="$t('toplanaApp.stavkeIzvoda.datum')"></span>
                    </div>
                    <div class="col-1">
                        {{stavkeIzvoda.datumValute ? $d(Date.parse(stavkeIzvoda.datumValute), 'short') : ''}}
                    </div>
                     <div class="col-2">
                        <input type="date" class="form-control" id="datumValute" name="datumValute"
                               v-model="stavkeIzvoda.datumValute"  required
                               />
                    </div>
                  </div>
                
                
                
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="button" id="save-entity" v-on:click="knjizenje()"
                    :disabled="!isSaving" class="btn btn-primary">
                        <span v-text="$t('entity.action.knjizenje')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stavke-izvoda-update.component.ts">
</script>
