// @ts-nocheck
<template>
    <div class="row ">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2  id="toplanaApp.transakcija.zaduzenje" v-text="$t('toplanaApp.transakcija.razduzenje')"></h2>
                
                
                 <div class="row" style="margin-top:30px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.vlasnikt')">Vlasnik</span>
                    </div>
                    <div class="col-6">
                        <select class="form-control" name="vlasniks" id="vlasniks"
                             v-model="vlasniks" v-on:change="getSifra" 
                             :class="{'valid': !vlasniks.$invalid, 'invalid':vlasniks.$invalid }"
                             >
                               <option value="">Одаберите Власника из Листе</option>
                                 <option v-bind:value="vlasnik.sifra" v-for="vlasnik in vlasnikt" :key="vlasnik.sifra">  {{ vlasnik.ime }} {{ vlasnik.prezime }} ({{ vlasnik.sifra }})</option>
                        </select>
                    </div>
                </div>
                
                
                
                
                <div class="row" style="margin-top:30px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.maticniBroj')">Maticni broj:</span>
                    </div>
                    <div class="col-6">
                        <input type="text" class="form-control" name="sifraStana" id="sifraStana" ref="sifraStana" required
                             v-model="$v.sifra.$model" v-on:keyup.enter="getStan" v-on:blur="getStan" v-on:focus="getStan"
                             :class="{'valid': !$v.sifra.$invalid, 'invalid': $v.sifra.$invalid }"
                             >
                    </div>
                    
                </div>
                
                
                
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.prezimeIIme')"></span>                        
                    </div>
                    <div class="col-6">
                        <span v-if="transakcija.ostaliRacuni">{{transakcija.ostaliRacuni.naziv}}</span> 
                        <span v-if="transakcija.ostaliRacuni && transakcija.ostaliRacuni.stan">{{transakcija.stan.vlasnik.prezime}} {{transakcija.stan.vlasnik.ime}}</span>
                        <span v-if="!transakcija.ostaliRacuni && transakcija.stan">{{transakcija.stan.vlasnik.prezime}} {{transakcija.stan.vlasnik.ime}}</span>
                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.adresa')"></span>
                    </div>
                    <div class="col-6">
                        <span v-if="transakcija.stan.id && transakcija.stan.id > 0">{{transakcija.stan.ulica}} {{transakcija.stan.ulaz}}/{{transakcija.stan.broj}}, {{transakcija.stan.postanskiBroj}} {{transakcija.stan.grad}}</span>
                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.status')"></span>
                    </div>
                    <div class="col-6">
                        {{transakcija.stan.status}} 
                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.datumKnjizenja')"></span>
                    </div>
                    <div class="col-6">                        
                        <b-form-datepicker
                            id="transakcija-datum"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="transakcija.datum"
                            locale="sr"        
                            placeholder="Изаберите датум"                    
                            :class="{'valid': !$v.transakcija.datum.$invalid, 'invalid': $v.transakcija.datum.$invalid }"
                        >
                        </b-form-datepicker>
                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.sifraDokumenta')"></span>
                    </div>
                    <div class="col-6">
                        <input type="text" class="form-control" name="sifraDokumenta" id="sifraDokumenta"
                             v-model="transakcija.sifraDokumenta"/>
                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.datumDokumenta')"></span>
                    </div>
                    <div class="col-6">
                        <b-form-datepicker
                            id="transakcija-datumDokumenta"
                            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                            v-model="transakcija.datumDokumenta"
                            locale="sr"        
                            placeholder="Изаберите датум"                                                
                        >
                        </b-form-datepicker>

                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.sifraPromene')"></span>
                    </div>
                    <div class="col-6">                        
                        <select class="form-control" id="transakcija-sifraPromene" name="sifraPromene" v-model="transakcija.sifraPromene">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="transakcija.sifraPromene && sifraPromeneOption.id === transakcija.sifraPromene.id ? transakcija.sifraPromene : sifraPromeneOption" v-for="sifraPromeneOption in sifraPromenes" :key="sifraPromeneOption.id">{{sifraPromeneOption.sifra}}</option>
                        </select>

                    </div>
                </div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-3">
                       <span v-text="$t('toplanaApp.transakcija.opisPromene')"></span>
                    </div>
                    <div class="col-6">
                        <input type="text" class="form-control" name="opis" id="opis"
                             v-model="transakcija.opis"/>
                    </div>
                </div>
                <div class="row" style="margin-top:20px;margin-bottom:20px">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.iznos')"></span>
                    </div>
                    <div class="col-6">
                        <input type="number" class="form-control" name="iznos" id="iznos" required
                            v-model="transakcija.potrazuje" 
                            :class="{'valid': !$v.transakcija.potrazuje.$invalid, 'invalid': $v.transakcija.potrazuje.$invalid }"
                            />
                    </div>
                </div>



                
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="button" id="save-entity" :disabled="$v.transakcija.$invalid || $v.sifra.$invalid || ispravnaSifra == false || isSaving" class="btn btn-primary" v-on:click="save()">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                    <button type="button" id="save-entity" class="btn btn-primary" v-on:click="clear()">
                        <span v-text="$t('entity.action.reset')"></span>
                    </button>
                </div>
            </form>
        </div>
        <div class="col-4" v-if="dugujePotrazujeDto.saldoDuguje != null">
            <h2  id="zz">&nbsp;</h2>
                <div class="row" style="margin-top:30px;">
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.duguje')"></span>
                    </div>
                    <div class="col-3">
                        <span v-text="$t('toplanaApp.transakcija.potrazuje')"></span>
                    </div>
                </div>
                <div class="row" style="margin-top:30px;">
                    <div class="col-3">
                        {{dugujePotrazujeDto.saldoDuguje}}
                    </div>
                    <div class="col-3">
                        {{dugujePotrazujeDto.saldoPotrazuje}}
                    </div>
                </div>
                <div class="row" style="margin-top:30px;">
                    <div class="col-6">
                        <span v-text="$t('toplanaApp.transakcija.stanje')"></span>
                    </div>
                </div>
                <div class="row" style="margin-top:30px;">
                    <div class="col-3">
                        {{dugujePotrazujeDto.stanjeDuguje}}
                    </div>
                    <div class="col-3">
                        {{dugujePotrazujeDto.stanjePotrazuje}}
                        
                    </div>
                </div>
        </div>
    </div>
</template>
<script lang="ts" src="./transakcija-razduzenje.component.ts">
</script>
