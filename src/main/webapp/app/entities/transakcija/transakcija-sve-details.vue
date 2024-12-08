<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <div v-if="transakcijaZbirno">
                <div class="row">
                    <div class="col-8">
                        <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.transakcija.analitickaKartica')"></span></h2>
                    </div>
                    <div v-if="transakcijaZbirno.stan || transakcijaZbirno.ostaliRacuni" class="col-4" style="margin-top:20px;">
                        <span v-if="transakcijaZbirno.stan" >Status: {{transakcijaZbirno.stan.status}}</span>
                        <span v-if="transakcijaZbirno.ostaliRacuni && transakcijaZbirno.ostaliRacuni.stan" >Status: {{transakcijaZbirno.ostaliRacuni.stan.status}}</span>
                    </div>    
                </div>
                <div class="row" style="margin-top:20px" v-if="transakcijaZbirno.stan || transakcijaZbirno.ostaliRacuni">
                    <div class="col-2">
                        <span v-text="$t('toplanaApp.transakcija.maticniBroj')"></span>:
                    </div>
                    <div class="col-1">
                        <span v-if="transakcijaZbirno.stan">{{transakcijaZbirno.stan.sifra}}</span>
                        <span v-if="transakcijaZbirno.ostaliRacuni">{{transakcijaZbirno.ostaliRacuni.sifra}}</span>
                    </div>
                    <div class="col-2">
                        <span v-if="transakcijaZbirno.stan">{{transakcijaZbirno.stan.vlasnik.prezime}} {{transakcijaZbirno.stan.vlasnik.ime}}</span>
                        <span v-if="transakcijaZbirno.ostaliRacuni">{{transakcijaZbirno.ostaliRacuni.naziv}}</span>
                    </div>
                    <div class="col-1">
                        <span v-if="transakcijaZbirno.stan">{{transakcijaZbirno.stan.grad}} </span>
                        <span v-if="transakcijaZbirno.ostaliRacuni  && transakcijaZbirno.ostaliRacuni.stan">{{transakcijaZbirno.ostaliRacuni.stan.grad}} </span>
                    </div>
                    <div class="col-2">
                        <span v-if="transakcijaZbirno.stan">{{transakcijaZbirno.stan.ulica}} {{transakcijaZbirno.stan.ulaz}}/{{transakcijaZbirno.stan.broj}}  </span>
                        <span v-if="transakcijaZbirno.ostaliRacuni  && transakcijaZbirno.ostaliRacuni.stan">{{transakcijaZbirno.ostaliRacuni.stan.ulaz}}/{{transakcijaZbirno.ostaliRacuni.stan.broj}} </span>
                    </div>
                </div>
                <hr class="new3">
                <div class="table-responsive" style="height:500px;" v-if="transakcijaZbirno.transakcije.length > 0">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.datumKnjizenja')"></span></th>
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.sifraDokumenta')"></span></th>
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.sifraPromene')"></span></th>     
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.opis')"></span></th>                                                            
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.duguje')"></span></th>
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.potrazuje')"></span></th>
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.saldo')"></span></th>
                                <th class="sticky"><span v-text="$t('toplanaApp.transakcija.racunIliIzvod')"></span></th>                                                                                                                
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(t, index) in transakcijaZbirno.transakcije" :key="t.id">
                                <td>
                                    {{t.datumKnjizenja ? $d(Date.parse(t.datumKnjizenja), 'short') : ''}}                                    
                                </td>
                                <td>
                                    {{t.sifraDokumenta}}
                                </td>
                                <td>
                                    {{t.sifraPromene}}
                                </td>
                                <td>
                                    {{t.opis}}
                                </td>
                                <td>
                                    {{t.duguje == 0 ? '' : t.duguje | currency('')}}
                                </td>
                                <td>
                                    {{t.potrazuje == 0 ? '' : t.potrazuje | currency('')}}
                                </td>
                                <td>
                                    {{t.saldo | currency('')}} 
                                </td>
                                <td>
                                   <button type="button" class="btn btn-info" @click="pogledaj(index)"
                                   v-if="t.racunId || t.stavkaIzvodaId || t.stavkaIzvodaPostanskaId">
                                        <span v-text="$t('toplanaApp.transakcija.pogledaj')"></span>
                                   </button>
                                </td>
                            </tr>
                            
                            <tr>
                                <td colspan="12">
                                    <hr class="new3">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" style="text-align:right;">
                                    <span v-text="$t('toplanaApp.transakcija.ukupno')"></span>:
                                </td>
                                <td>
                                    {{transakcijaZbirno.dugujeUkupno | currency('')}}
                                </td>
                                <td>
                                    {{transakcijaZbirno.potrazujeUkupno | currency('')}}
                                </td>
                                <td>
                                    {{transakcijaZbirno.saldoUkupno | currency('')}}
                                </td>
                                <td>
                                    
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>



                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="transakcija.id" :to="{name: 'TransakcijaEdit', params: {transakcijaId: transakcija.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
                <b-button  v-on:click="stampanje()"
                        variant="info"
                        class="btn ">                    
                    <span class="d-none d-md-inline" v-text="$t('entity.action.stampanje')">Stampanje</span>
                </b-button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./transakcija-sve-details.component.ts">
</script>
