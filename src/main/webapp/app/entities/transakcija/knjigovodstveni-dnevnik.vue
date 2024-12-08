<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <h2 class="jh-entity-heading"><span v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.title')"></span> {{transakcija.id}}</h2>
            <router-link :to="{name: 'KnjigovodstveniDnevnikAnaliticki'}" >
                <h4 class="ml-5 mt-4" style="color:#132F75; font-weight:500" v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.poreskiAnaliticki')"></h4>
            </router-link>
            <router-link :to="{name: 'SintetickiDnevnik'}" >
                <h4 class="ml-5 mt-4" style="color:#132F75; font-weight:500" v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.poreskiSinteticki')"></h4>
            </router-link>
            <router-link :to="{name: 'RekapitulacijaSifraPromeneDatum'}" >
                <h4 class="ml-5 mt-4 mb-5" style="color:#132F75; font-weight:500" v-text="$t('toplanaApp.transakcija.knjigovodstveniDnevnik.rekapitulacijaPoVrstiObavezeIDatumima')"></h4>
            </router-link>                          
        </div>
    </div>
</template>

<script lang="ts" src="./knjigovodstveni-dnevnik.component.ts">
</script>
