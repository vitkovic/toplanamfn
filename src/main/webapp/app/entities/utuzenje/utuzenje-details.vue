<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="utuzenje">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.utuzenje.detail.title')">Utuzenje</span> {{utuzenje.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.utuzenje.datum')">Datum</span>
                    </dt>
                    <dd>
                        <span>{{utuzenje.datum}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.utuzenje.stan')">Stan</span>
                    </dt>
                    <dd>
                        <div v-if="utuzenje.stan">
                            <router-link :to="{name: 'StanView', params: {stanId: utuzenje.stan.id}}">{{utuzenje.stan.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="utuzenje.id" :to="{name: 'UtuzenjeEdit', params: {utuzenjeId: utuzenje.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./utuzenje-details.component.ts">
</script>
