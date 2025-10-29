<template>
  <div class="AllCheck">
    <Dictionaries v-show="$store.getters.dictionCheck" />
    <DictionaryList v-show="!$store.getters.dictionCheck" />
  </div>
</template>

<script>
import Dictionaries from './Dictionaries.vue'
import DictionaryList from './DictionaryList.vue'
export default {
  components: { Dictionaries, DictionaryList },
  data() {
    return {
      isCheck: true
    }
  },
  watch: {},
  mounted() {}
}
</script>

<style lang="scss">
.AllCheck {
  width: 100%;
  height: 100%;
}
</style>
