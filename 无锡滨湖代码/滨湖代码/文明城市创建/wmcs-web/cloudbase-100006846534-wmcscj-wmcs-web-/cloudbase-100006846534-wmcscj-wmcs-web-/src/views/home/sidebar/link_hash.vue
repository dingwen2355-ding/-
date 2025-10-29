<template>
    <component :is="type" v-bind="linkProps(to)">
        <slot />
    </component>
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
            return this.isExternal ? 'a' : 'router-link'
        }
    },
    methods: {
        linkProps(to) {
            if (this.isExternal) {
                return {
                    href: to,
                    target: '_blank',
                    rel: 'noopener'
                }
            }
            return { to: { path: to } }
            // return { to: to }
        }
    }
}
</script>
