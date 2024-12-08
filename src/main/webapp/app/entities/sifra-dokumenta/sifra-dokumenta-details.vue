<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sifraDokumenta">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.sifraDokumenta.detail.title')">SifraDokumenta</span> {{sifraDokumenta.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.sifraDokumenta.sifra')">Sifra</span>
                    </dt>
                    <dd>
                        <span>{{sifraDokumenta.sifra}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="sifraDokumenta.id" :to="{name: 'SifraDokumentaEdit', params: {sifraDokumentaId: sifraDokumenta.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sifra-dokumenta-details.component.ts">
</script>
