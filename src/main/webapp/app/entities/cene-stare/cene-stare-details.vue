<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="ceneStare">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.ceneStare.detail.title')">CeneStare</span> {{ceneStare.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.ceneStare.kwh')">Kwh</span>
                    </dt>
                    <dd>
                        <span>{{ceneStare.kwh}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.ceneStare.fix')">Fix</span>
                    </dt>
                    <dd>
                        <span>{{ceneStare.fix}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.ceneStare.odrzavanje')">Odrzavanje</span>
                    </dt>
                    <dd>
                        <span>{{ceneStare.odrzavanje}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('toplanaApp.ceneStare.ostalo')">Ostalo</span>
                    </dt>
                    <dd>
                        <span>{{ceneStare.ostalo}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="ceneStare.id" :to="{name: 'CeneStareEdit', params: {ceneStareId: ceneStare.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cene-stare-details.component.ts">
</script>
