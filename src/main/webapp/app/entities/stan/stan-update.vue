<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.stan.home.createOrEditLabel" v-text="$t('toplanaApp.stan.home.createOrEditLabel')">Create or edit a Stan</h2>
                <div>
                    <div class="form-group" v-if="stan.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stan.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.stan.povrsina')" for="stan-povrsina">Povrsina</label>
                        <input type="number" class="form-control" name="povrsina" id="stan-povrsina"
                            :class="{'valid': !$v.stan.povrsina.$invalid, 'invalid': $v.stan.povrsina.$invalid }" v-model.number="$v.stan.povrsina.$model"  required/>
                        <div v-if="$v.stan.povrsina.$anyDirty && $v.stan.povrsina.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stan.povrsina.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stan.povrsina.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.ulica')" for="stan-ulica">Ulica</label>
                                <input type="text" class="form-control" name="ulica" id="stan-ulica"
                                    :class="{'valid': !$v.stan.ulica.$invalid, 'invalid': $v.stan.ulica.$invalid }" v-model="$v.stan.ulica.$model" />
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.ulaz')" for="stan-ulaz">Ulaz</label>
                                <input type="number" class="form-control" name="ulaz" id="stan-ulaz"
                                    :class="{'valid': !$v.stan.ulaz.$invalid, 'invalid': $v.stan.ulaz.$invalid }" v-model.number="$v.stan.ulaz.$model" />
                            </div>

                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.broj')" for="stan-broj">Broj</label>
                                <input type="number" class="form-control" name="broj" id="stan-broj"
                                    :class="{'valid': !$v.stan.broj.$invalid, 'invalid': $v.stan.broj.$invalid }" v-model.number="$v.stan.broj.$model" />
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.ukljucen')" for="stan-ukljucen">Ukljucen</label>
                                <input type="checkbox" class="form-check" name="ukljucen" id="stan-ukljucen"
                                    :class="{'valid': !$v.stan.ukljucen.$invalid, 'invalid': $v.stan.ukljucen.$invalid }" v-model="$v.stan.ukljucen.$model" />
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.sifra')" for="stan-sifra">Sifra</label>
                                <input type="text" class="form-control" name="sifra" id="stan-sifra"
                                    :class="{'valid': !$v.stan.sifra.$invalid, 'invalid': $v.stan.sifra.$invalid }" v-model="$v.stan.sifra.$model" />
                                <div v-if="$v.stan.sifra.$anyDirty && $v.stan.sifra.$invalid">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">                    
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.grad')" for="stan-grad">Grad</label>
                                <input type="text" class="form-control" name="grad" id="stan-grad"
                                    :class="{'valid': !$v.stan.grad.$invalid, 'invalid': $v.stan.grad.$invalid }" v-model="$v.stan.grad.$model" />
                            </div>
                        </div>
                        <div class="col-4">                    
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.postanskiBroj')" for="stan-postanskiBroj">Postanski Broj</label>
                                <input type="text" class="form-control" name="postanskiBroj" id="stan-postanskiBroj"
                                    :class="{'valid': !$v.stan.postanskiBroj.$invalid, 'invalid': $v.stan.postanskiBroj.$invalid }" v-model="$v.stan.postanskiBroj.$model" />
                            </div>
                        </div>
                    </div>
					<div class="row">
					                       <div class="col-8">                    
					                           <div class="form-group">
					                               <label class="form-control-label" v-text="$t('toplanaApp.stan.brojMerila')" for="stan-brojMerila">Broj Merila</label>
					                               <input type="text" class="form-control" name="brojMerila" id="stan-brojMerila"
					                                   :class="{'valid': !$v.stan.brojMerila.$invalid, 'invalid': $v.stan.brojMerila.$invalid }" v-model="$v.stan.brojMerila.$model" />
					                           </div>
					                       </div>
					                      
					                   </div>
                     <div class="row">
                        <div class="col-4">   
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.tipPotrosaca')" for="stan-tipPotrosaca">Tip Potrosaca</label>
                                <select class="form-control" id="stan-tipPotrosaca" name="tipPotrosaca" v-model="stan.tipPotrosaca">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="stan.tipPotrosaca && tipPotrosacaOption.id === stan.tipPotrosaca.id ? stan.tipPotrosaca : tipPotrosacaOption" v-for="tipPotrosacaOption in tipPotrosacas" :key="tipPotrosacaOption.id">{{tipPotrosacaOption.tip}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-4">   
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.podstanica')" for="stan-podstanica">Podstanica</label>
                                <select class="form-control" id="stan-podstanica" name="podstanica" v-model="stan.podstanica">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="stan.podstanica && podstanicaOption.id === stan.podstanica.id ? stan.podstanica : podstanicaOption" v-for="podstanicaOption in podstanicas" :key="podstanicaOption.id">{{podstanicaOption.naziv}}: {{podstanicaOption.broj}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-4">   
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('toplanaApp.stan.vlasnik')" for="stan-vlasnik">Vlasnik</label>
                                <select class="form-control" id="stan-vlasnik" name="vlasnik" v-model="stan.vlasnik">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="stan.vlasnik && vlasnikOption.id === stan.vlasnik.id ? stan.vlasnik : vlasnikOption" v-for="vlasnikOption in vlasniks" :key="vlasnikOption.id">{{vlasnikOption.prezime}} {{vlasnikOption.ime}} {{vlasnikOption.email}}</option>
                                </select>
                            </div>
                        </div>
                     </div>                                     
                    
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stan.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stan-update.component.ts">
</script>
