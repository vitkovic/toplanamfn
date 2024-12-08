<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="parametriIstorija">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.parametriIstorija.detail.title')">ParametriIstorija</span> {{parametriIstorija.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.parametriIstorija.vrednost')">Vrednost</span>
                    </dt>
                    <dd>
                        <span>{{parametriIstorija.vrednost}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.parametriIstorija.vaziOd')">Vazi Od</span>
                    </dt>
                    <dd>
                        <span>{{parametriIstorija.vaziOd}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.parametriIstorija.vaziDo')">Vazi Do</span>
                    </dt>
                    <dd>
                        <span>{{parametriIstorija.vaziDo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.parametriIstorija.parametri')">Parametri</span>
                    </dt>
                    <dd>
                        <div v-if="parametriIstorija.parametri">
                            <router-link :to="{name: 'ParametriView', params: {parametriId: parametriIstorija.parametri.id}}">{{parametriIstorija.parametri.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="parametriIstorija.id" :to="{name: 'ParametriIstorijaEdit', params: {parametriIstorijaId: parametriIstorija.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./parametri-istorija-details.component.ts">
</script>
