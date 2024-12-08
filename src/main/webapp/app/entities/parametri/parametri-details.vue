<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="parametri">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.parametri.detail.title')">Parametri</span> {{parametri.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.parametri.naziv')">Naziv</span>
                    </dt>
                    <dd>
                        <span>{{parametri.naziv}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.parametri.vrednost')">Vrednost</span>
                    </dt>
                    <dd>
                        <span>{{parametri.vrednost}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="parametri.id" :to="{name: 'ParametriEdit', params: {parametriId: parametri.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./parametri-details.component.ts">
</script>
