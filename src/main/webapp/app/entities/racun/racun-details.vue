<template>
    <div class="row justify-content-center" v-if="loaded">
        <div class="col-12">
            <div class="row" >
                <div class="col-2">
                    <span v-text="$t('toplanaApp.racun.prezimeIme')"></span>:
                </div>
                <div class="col-3">
                    {{racun.stan.vlasnik.prezime}} {{racun.stan.vlasnik.ime}} {{racun.stan.vlasnik.naziv}}
                </div>
                <div class="col-3">
                    {{racun.stan.ulica}} <span v-text="$t('toplanaApp.racun.ulaz')"></span>: {{racun.stan.ulaz}} <span v-text="$t('toplanaApp.racun.broj')"></span>: {{racun.stan.broj}} 
                </div>
                <div class="col-2">
                    <span v-text="$t('toplanaApp.racun.sifra')"></span>: {{racun.stan.sifra}} 
                </div>
                <div class="col-2">
                    <span v-text="$t('toplanaApp.racun.status')"></span>: {{racun.stan.status}} 
                </div>
            </div>
            <div class="row">
                <div class="col-2">
                    <span v-text="$t('toplanaApp.racun.tekuciRacun')"></span>:
                </div>
                <div class="col-3">
                    {{racun.stan.vlasnik.brojRacuna}} 
                </div>
                <div class="col-3">
                    <span v-text="$t('toplanaApp.racun.partija')"></span>:
                    {{racun.stan.vlasnik.partijaRacuna}} 
                </div>
                <div class="col-2">
                    
                </div>
                <div class="col-2">
                    
                </div>
            </div>
        <hr class="new3">
        <div class="row">
            <div class="col-12">
                <span v-text="$t('toplanaApp.racun.obracunPotrosnje')"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.staroStanjePodstanice')"></span>
            </div>
            <div class="col-2">            
                {{racun.stanjeZaRacun.staroStanje.stanje}} kwh
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.ukupnaPovrsina')"></span>
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.utrosakKwhPoM2')"></span>
            </div>
        </div>
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.novoStanjePodstanice')"></span>
            </div>
            <div class="col-2">            
                {{racun.stanjeZaRacun.novoStanje.stanje}} kwh
            </div>
            
        </div>
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.utrosak')"></span>
            </div>
            <div class="col-2">            
                {{racun.stanjeZaRacun.novoStanje.stanje - racun.stanjeZaRacun.staroStanje.stanje}} kwh
            </div>
            <div class="col-3">            
                {{racun.stanjeZaRacun.ukupnaPovrsina}} m2
            </div>
            <div class="col-3">            
                {{racun.stanjeZaRacun.utrosakPoM2}}
            </div>

            
        </div>
        <hr class="new3">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="w-20" v-text="$t('toplanaApp.racun.opis')">opis</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.povrsinaStanam2')">Povrsina</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.utrosakKwh')">Utros. (kwh)</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.cenaDin')">Cena din.</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.vrednostDin')">Vrednost din.</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.popustDin')">Popust din.</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.vrednostDin')">Vrednost din.</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.pdvProcenat')">PDV %</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.pdvDin')">PDV din.</th>
                    <th class="w-10" v-text="$t('toplanaApp.racun.ukupnoDin')">Ukupno din.</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="w-20 text-nowrap"  v-text="$t('toplanaApp.racun.tekuceZaduzenje')"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                    </tr>
                    <tr>
                        <td class="w-20" style="padding-left:35px" v-text="$t('toplanaApp.racun.varijabilno')"></td>
                        <td class="w-10">{{racun.stan.povrsina}}</td>
                        <td class="w-10">{{racun.utrosakUKwh}}</td>
                        <td class="w-10">{{racun.cenaKwh}}</td>
                        <td class="w-10">{{racun.utrosakVarijabilniBezPopusta}}</td>
                        <td class="w-10">{{racun.utrosakVarijabilniPopust}}</td>
                        <td class="w-10">{{racun.utrosakVarijabilniBezPdv}}</td>
                        <td class="w-10">{{racun.pdv2}}</td>
                        <td class="w-10">{{racun.utrosakVarijabilniPdv}}</td>
                        <td class="w-10">{{racun.utrosakVarijabilni}}</td>
                    </tr>
                    <tr>
                        <td class="w-20" style="padding-left:35px" v-text="$t('toplanaApp.racun.fiksno')"></td>
                        <td class="w-10">{{racun.stan.povrsina}}</td>
                        <td class="w-10"></td>
                        <td class="w-10">
                            <span v-if="racun.stan.ukljucen">{{racun.cenaFix}}</span>
                            <span v-if="racun.stan.ukljucen == false">{{racun.cenaFixIskljucen}}</span>
                        </td>
                        <td class="w-10">{{racun.utrosakFiksniBezPopusta}}</td>
                        <td class="w-10">{{racun.utrosakFiksniPopust}}</td>
                        <td class="w-10">{{racun.utrosakFiksniBezPdv}}</td>
                        <td class="w-10">{{racun.pdv2}}</td>
                        <td class="w-10">{{racun.utrosakFiksniPdv}}</td>
                        <td class="w-10">{{racun.utrosakFiksni}}</td>
                    </tr>
                    <tr>
                        <td class="w-20" style="padding-left:35px" v-text="$t('toplanaApp.racun.odrzavanje')"></td>
                        <td class="w-10">{{racun.stan.povrsina}}</td>
                        <td class="w-10"></td>
                        <td class="w-10">{{racun.cenaOdrzavanje}}</td>
                        <td class="w-10">{{racun.utrosakOdrzavanjeBezPdv}}</td>
                        <td class="w-10"></td>
                        <td class="w-10">{{racun.utrosakOdrzavanjeBezPdv}}</td>
                        <td class="w-10">{{racun.pdv1}}</td>
                        <td class="w-10">{{racun.utrosakOdrzavanjePdv}}</td>
                        <td class="w-10">{{racun.utrosakOdrzavanje}}</td>
                    </tr>
                    <tr>
                        <td class="w-40" v-text="$t('toplanaApp.racun.ukupnoTekuceZaduzenje')"></td>                        
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10">{{racun.zaduzenjePoRacunu}}</td>
                    </tr>
                    <tr v-if="racun.popust">
                        <td class="w-40" colspan="2"  v-text="$t('toplanaApp.racun.popustOdobrenUGornjemObracunu',{'param':racun.popust})"></td>                                                
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                    </tr>
                    <tr>
                        <td class="w-40" colspan="2"  v-text="$t('toplanaApp.racun.dugIzPrethodnogPerioda')"></td>                                                
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10">{{racun.ukupnoZaduzenje}}</td>
                    </tr>
                    <tr>
                        <td class="w-40" colspan="2"  v-text="$t('toplanaApp.racun.ukupnoZaPlacanje')"></td>         
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-5"></td>
                        <td class="w-10"></td>
                        <td class="w-10"></td>
                        <td class="w-10">{{racun.zaPlacanje}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <hr class="new3">
        <div class="row">
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.datumRacuna')"></span>: {{racun.datumRacuna ? $d(Date.parse(racun.datumRacuna), 'short') : ''}}
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.valutaPlacanja')"></span>: {{racun.valutaPlacanja ? $d(Date.parse(racun.valutaPlacanja), 'short') : ''}}
            </div>
            <div class="col-3">
                <span v-text="$t('toplanaApp.racun.datumSaldiranja')"></span>: {{racun.datumSaldiranja ? $d(Date.parse(racun.datumSaldiranja), 'short') : ''}}
            </div>            
        </div>
        <div class="row">
            <div class="col-12">
                <span v-text="$t('toplanaApp.racun.porukaKupcu')"></span>
            </div>
            
        </div>
        </div>
        
        <div class="col-12" style="margin-top:20px">
            <div v-if="racun">
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <button type="button" 
                        v-on:click="stampanje()"
                        class="btn btn-primary">
                    <span v-text="$t('entity.action.stampanje')"></span>
                </button>
                
                
                  <button type="button" 
                        v-on:click="previousRacun()"
                        class="btn btn-primary">
                    <span v-text="$t('entity.action.previousracun')"></span>
                </button>
                
                <button type="button" 
                        v-on:click="nextRacun()"
                        class="btn btn-primary">
                    <span v-text="$t('entity.action.nextracun')"></span>
                </button>
                
                <!--
                <router-link v-if="racun.id" :to="{name: 'RacunEdit', params: {racunId: racun.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
                -->
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./racun-details.component.ts">
</script>
