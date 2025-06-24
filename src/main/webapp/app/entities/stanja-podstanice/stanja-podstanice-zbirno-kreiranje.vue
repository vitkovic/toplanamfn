<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="row">
                <div class="col-4">
                     <b-form-datepicker
                        id="datepicker-dateformat3"
                        :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                        placeholder="Датум није унет"
                        v-model="stanja.datumOcitavanja"
                        locale="sr" required
                    ></b-form-datepicker>  
                </div>
            </div>
            <div class="row" style="margin-top:20px" v-for="stanja in stanjaNaPodstanicama">                
                <div class="col-12">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title" 
                           v-text="stanja.podstanica.naziv"
                            ></h5>
                            <div class="row">                                    
                                <div class="col-3">                                        
                                    <label class="form-control-label" v-text="$t('toplanaApp.stanjaPodstanice.stanje')"></label>
                                    <input type="number" class="form-control" name="staroStanjePodstanice" id="nacrt-racuna-staroStanjePodstanice"
                                        :class="{'valid': stanja.stanje, 'invalid': !stanja.stanje }" v-model.number="stanja.stanje"
                                          required/> 
                                </div>
                            </div>
                            
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-3">
                    <button type="button" @click="save" id="save-entity" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
<script lang="ts" src="./stanja-podstanice-zbirno-kreiranje.component.ts">
</script>