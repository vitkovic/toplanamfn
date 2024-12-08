<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <h2 id="page-heading">
                <span v-text="$t('toplanaApp.racun.home.bankaTitle')" id="racun-heading"></span>
            </h2>
            <b-row class="mb-4 mt-4">
                <b-col sm="12">
                    <date-picker placeholder="Изаберите месец" v-model="mesec" type="month"></date-picker>
                </b-col>
            </b-row>
            <div style="margin-bottom:200px">
                <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                </button>
                <button type="button" id="save-entity"  class="btn btn-primary" v-on:click="posalji()">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                </button>
            </div>

            
        </div>
    </div>
</template>
<script lang="ts" src="./racun-banka.component.ts">
</script>
