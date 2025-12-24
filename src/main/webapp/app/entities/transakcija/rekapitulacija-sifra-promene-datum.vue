<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.rekapitulacijaSifraPromeneDatum')" id="transakcija-heading"></span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
		 <b-alert v-if="alertMessage" :variant="alertVariant" show>
		  {{ alertMessage }}
		</b-alert>
        <div class="card" :class="{ margine: margina }">            
            <div class="card-body">
                <h5 class="card-title"><span v-text="$t('toplanaApp.transakcija.pretraga')"></span></h5>
                <div class="row">
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.racun.doDatuma')"></label>
                    </div>                     
                </div>
                <div class="row">
                                                                                            
                                                                              
                    <div class="col-3">
                        <b-form-datepicker
                            id="datepicker-dateformat3"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="search.datumDo"
                            locale="sr"                            
                        ></b-form-datepicker> 
                    </div>                            
                </div><br>
                
                 <div class="row">
                    <div class="col-3">                                                
                        <label v-text="$t('toplanaApp.racun.podstanicaOd')"></label>
                    </div>  
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.racun.podstanicaDo')"></label>
                    </div>                     
                </div>
                
                   <div class="row">
                  <div class="col-3">
                        <input type="text" class="form-control" name="podstanicaOd" id="podstanicaOd"
                                v-model="search.podstanicaOd"  
                                /> 
                    </div>
                  <div class="col-3">
                        <input type="text" class="form-control" name="podstanicaDo" id="podstanicaDo"
                                v-model="search.podstanicaDo"  
                                /> 
                  </div>
                 </div><br>
                   <div class="row">
                    <div class="col-3">                                                
                        <label v-text="$t('toplanaApp.racun.sifraOd')"></label>
                    </div>  
                    <div class="col-3">
                        <label v-text="$t('toplanaApp.racun.sifraDo')"></label>
                    </div>                     
                </div>
                
                   <div class="row">
                  <div class="col-3">
                        <input type="text" class="form-control" name="sifraOd" id="sifraOd"
                                v-model="search.sifraOd"  
                                /> 
                    </div>
                  <div class="col-3">
                        <input type="text" class="form-control" name="sifraDo" id="sifraDo"
                                v-model="search.sifraDo"  
                                /> 
                  </div>
                 </div>
                         
                     
                     
                <button type="button" id="save-entity"  :disabled="isFetching" @click="send"
                                    class="btn btn-primary" style="margin-top:20px">
                    <span v-text="$t('entity.action.posalji')">Save</span>
                </button>                
            </div>
        </div>
     <!--
        <div v-show="rekapitulacije && rekapitulacije.length > 0 && rekapitulacije[0].datum" style="margin-top:10px;">
            <b-row>
                <b-col md="9">
                    
                </b-col>
                <b-col md="3" style="text-align:right;">
                    <b-form-input v-model="filter" type="search" :placeholder="potrazi"></b-form-input>
                </b-col>
            </b-row>
            <b-row style="margin-top:10px">
                <b-col>
                    <b-table striped sort-compare-locale="sr-Cyrl"
                        :items="rekapitulacije" 
                        :filter="filter" 
                        :per-page="perPage" 
                        :fields="fields"
                        :current-page="currentPage" >
                            <template slot="bottom-row" slot-scope="data">                   
                                <td style="background-color:#557360;color:white"></td>
                                <td style="background-color:#557360;color:white">Ukupno</td>
                                <td style="background-color:#557360;color:white">{{totalDuguje | currency('')}}</td>                                
                                <td style="background-color:#557360;color:white">{{totalPotrazuje | currency('')}}</td>
        
                            </template>                            
                    </b-table>                   
                   <b-pagination v-model="currentPage" class="customPagination" :total-rows="rows" :per-page="perPage"></b-pagination>
                   
                </b-col>
            </b-row>     
            <b-row class="mt-4">
            <b-col sm="2">
                <b-button  v-on:click="stampanje()"
                        variant="info"
                        class="btn ">                    
                    <span class="d-none d-md-inline" v-text="$t('entity.action.stampanje')">Stampanje</span>
                </b-button>
            </b-col>            
        </b-row>      
        </div>
-->
         <div class="table-responsive" v-if="racuns && racuns.length > 0"><br>
             <b-button  v-on:click="stampanje()"
                        variant="info"
                        class="btn ">                    
                    <span class="d-none d-md-inline" v-text="$t('entity.action.stampanje')">Stampanje</span>
                </b-button><br>
            <table class="table table-striped">
                <thead>
                
                <th>Датум Рачуна</th>
                <th>Варијабилни</th>
                <th>Фиксни</th>
                <th>Попуст</th>
                <th>Укупно</th>
                <th>ПДВ (10%)</th>
                <th>Одржавање</th>
                <th>ПДВ (20%)</th>
                <th>Укупно Задужење</th>
                <th>Шифра</th>
               
                
                
                </thead>
                <tbody>
                <tr v-for="racun in racuns"
                    :key="racun.id">
                    <!--
                    <td>
                        <router-link :to="{name: 'RacunView', params: {racunId: racun.id}}">{{racun.id}}</router-link>
                    </td>
                    -->
                    <td>{{ racun.datumRacuna ? racun.datumRacuna : 'Ukupno' }}</td>
                    <td>{{racun.utrosakVarijabilni}}</td>
                    <td>{{racun.utrosakFiksni}}</td>
                    <td>{{racun.popust}}</td>
                    <td>{{racun.ukupno}}</td>
                    <td>{{racun.pdv2}}</td>
                    <td>{{racun.utrosakOdrzavanje}}</td>
                    <td>{{racun.pdv1}}</td>
                   <td>{{racun.ukupnoZaduzenje}}</td>
                   <td>{{racun.strippedsifra}}</td>
                    <!--  <td>{{racun.stan.sifra}}</td>-->
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RacunView', params: {racunId: racun.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link  :to="{name: 'RacunEdit', params: {racunId: racun.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-if="!racun.proknjizen" v-on:click="prepareRemove(racun)"
                                   variant="danger"
                                   class="btn btn-sm"    
                                   v-b-modal.removeEntity>       
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table><br>
                <b-button  v-on:click="stampanje()"
                        variant="info"
                        class="btn ">                    
                    <span class="d-none d-md-inline" v-text="$t('entity.action.stampanje')">Stampanje</span>
                </b-button>
        </div>

      

        <!--
        <div v-show="transakcije && transakcije.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
        -->
    </div>
</template>

<script lang="ts" src="./rekapitulacija-sifra-promene-datum.component.ts">
</script>
