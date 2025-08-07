<template>
  <div v-if="visible" class="alert-dialog-overlay">
    <div class="alert-dialog-box">
      <p>{{ message }}</p>
      <div class="button-group">
        <button @click="$emit('cancel')">Одустани</button>
        <button @click="$emit('ok')">Потврди</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AlertDialog',
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
    message: {
      type: String,
      default: 'Are you sure?',
    },
  },
})
</script>

<style scoped>
.alert-dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}
.alert-dialog-box {
  background: white;
  padding: 20px 30px;
  border-radius: 8px;
  text-align: center;
}
.button-group {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  gap: 15px;
}
button {
  padding: 6px 16px;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}
button:first-child {
  background-color: #6c757d; /* Cancel = gray */
}
</style>
