<template>
    <div id="footer" class="footer">
        <div class="row">
            <div class="col-6">
                <p  style="padding-left:10pt; text-align:left" v-text="$t('footerLeft')">This is your footer</p>
            </div>
            <div class="col-6">
                <p  style="padding-right:10pt" v-text="$t('footerRight')">This is your footer</p>
            </div>
        </div>
        
        
    </div>
</template>

<script lang="ts" src="./jhi-footer.component.ts">
</script>

<style scoped>
    .footer {
        text-align: right;
    }
</style>
