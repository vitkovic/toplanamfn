<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="tipPotrosaca">
                <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.tipPotrosaca.detail.title')">TipPotrosaca</span> {{tipPotrosaca.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('toplanaApp.tipPotrosaca.tip')">Tip</span>
                    </dt>
                    <dd>
                        <span>{{tipPotrosaca.tip}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="tipPotrosaca.id" :to="{name: 'TipPotrosacaEdit', params: {tipPotrosacaId: tipPotrosaca.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tip-potrosaca-details.component.ts">
</script>
