<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.tipPotrosaca.home.createOrEditLabel" v-text="$t('toplanaApp.tipPotrosaca.home.createOrEditLabel')">Create or edit a TipPotrosaca</h2>
                <div>
                    <div class="form-group" v-if="tipPotrosaca.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="tipPotrosaca.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.tipPotrosaca.tip')" for="tip-potrosaca-tip">Tip</label>
                        <input type="number" class="form-control" name="tip" id="tip-potrosaca-tip"
                            :class="{'valid': !$v.tipPotrosaca.tip.$invalid, 'invalid': $v.tipPotrosaca.tip.$invalid }" v-model.number="$v.tipPotrosaca.tip.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.tipPotrosaca.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tip-potrosaca-update.component.ts">
</script>
