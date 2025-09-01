<template>
    <div class="row justify-content-center">
        <div class="col-8">

            <b-form-file style="margin-bottom:30px"
                v-model="file1"
                :state="Boolean(file1)"
                placeholder="Изаберите датотеку са изводом или је превуците..."
                drop-placeholder="Drop file here..."
                browse-text="Изаберите"
            ></b-form-file>
            <div style="margin-bottom:200px">
                <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                </button>
                <button type="button" id="save-entity"  class="btn btn-primary" v-on:click="save()">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                </button>
            </div>

     
        </div>
    </div>
</template>
<script lang="ts" src="./nove_stanice_stanja_component.ts">
</script>
