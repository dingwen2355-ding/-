<template>
    <div>
        <slot />
    </div>
</template>

<script>
export default {
    props: {
        to: {
            type: String,
            required: true
        }
    },
    computed: {
        isExternal() {
            return /^(https?:|mailto:|tel:)/.test(this.to)
        },
        type() {
            return this.isExternal ? 'a' : 'a'
        }
    },
    methods: {
        historytourl() {
            let _this = this
            if (this.isExternal) {
                window.open(this.to)
            } else {
                _this.$router.push(this.to)
            }
        },
        linkProps(to) {
            if (this.isExternal) {
                return {
                    href: to,
                    target: '_blank',
                    rel: 'noopener'
                }
            }
            return { to: to }
        }
    }
}
</script>
