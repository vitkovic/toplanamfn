<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cene">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.cene.detail.title')">Cene</span> {{cene.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.cene.kwh')">Kwh</span>
                    </dt>
                    <dd>
                        <span>{{cene.kwh}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.cene.fix')">Fix</span>
                    </dt>
                    <dd>
                        <span>{{cene.fix}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.cene.odrzavanje')">Odrzavanje</span>
                    </dt>
                    <dd>
                        <span>{{cene.odrzavanje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.cene.ostalo')">Ostalo</span>
                    </dt>
                    <dd>
                        <span>{{cene.ostalo}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cene.id" :to="{name: 'CeneEdit', params: {ceneId: cene.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cene-details.component.ts">
</script>
