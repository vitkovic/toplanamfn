<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="toplanaApp.cene.home.createOrEditLabel" v-text="$t('toplanaApp.cene.home.createOrEditLabel')">Create or edit a Cene</h2>
                <div>
                    <div class="form-group" v-if="cene.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cene.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.kwh')" for="cene-kwh">Kwh</label>
                        <input type="number" class="form-control" name="kwh" id="cene-kwh"
                            :class="{'valid': !$v.cene.kwh.$invalid, 'invalid': $v.cene.kwh.$invalid }" v-model.number="$v.cene.kwh.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.fix')" for="cene-fix">Fix</label>
                        <input type="number" class="form-control" name="fix" id="cene-fix"
                            :class="{'valid': !$v.cene.fix.$invalid, 'invalid': $v.cene.fix.$invalid }" v-model.number="$v.cene.fix.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.fixIskljucen')" for="cene-fix-iskljucen">Fix</label>
                        <input type="number" class="form-control" name="fix-iskljucen" id="cene-fix-iskljucen"
                            :class="{'valid': !$v.cene.fixIskljuceno.$invalid, 'invalid': $v.cene.fixIskljuceno.$invalid }" v-model.number="$v.cene.fixIskljuceno.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.odrzavanje')" for="cene-odrzavanje">Odrzavanje</label>
                        <input type="number" class="form-control" name="odrzavanje" id="cene-odrzavanje"
                            :class="{'valid': !$v.cene.odrzavanje.$invalid, 'invalid': $v.cene.odrzavanje.$invalid }" v-model.number="$v.cene.odrzavanje.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.ostalo')" for="cene-ostalo">Ostalo</label>
                        <input type="number" class="form-control" name="ostalo" id="cene-ostalo"
                            :class="{'valid': !$v.cene.ostalo.$invalid, 'invalid': $v.cene.ostalo.$invalid }" v-model.number="$v.cene.ostalo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.pdv1')" for="pdv1"></label>
                        <input type="number" class="form-control" name="pdv1" id="pdv1"
                            :class="{'valid': !$v.cene.pdv1.$invalid, 'invalid': $v.cene.pdv1.$invalid }" v-model.number="$v.cene.pdv1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.pdv2')" for="pdv2"></label>
                        <input type="number" class="form-control" name="pdv2" id="pdv2"
                            :class="{'valid': !$v.cene.pdv2.$invalid, 'invalid': $v.cene.pdv2.$invalid }" v-model.number="$v.cene.pdv2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('toplanaApp.cene.popust')" for="popust"></label>
                        <input type="number" class="form-control" name="popust" id="popust"
                            :class="{'valid': !$v.cene.popust1.$invalid, 'invalid': $v.cene.popust1.$invalid }" v-model.number="$v.cene.popust1.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cene.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cene-update.component.ts">
</script>
