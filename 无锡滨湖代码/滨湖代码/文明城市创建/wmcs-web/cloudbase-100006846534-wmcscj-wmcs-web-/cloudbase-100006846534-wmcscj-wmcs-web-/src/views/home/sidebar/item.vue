<script>
export default {
    name: 'MenuItem',
    functional: true,
    props: {
        icon: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: ''
        }
    },
    render(h, context) {
        const { icon, title } = context.props
        const vnodes = []
        if (icon) {
            if (icon.includes('el-icon')) {
                vnodes.push(<i slot='icon' class={[icon, 'sub-fa-icon']} />)
            } else if (icon.includes('fa')) {
                vnodes.push(<i slot='icon' class={[icon, 'sub-fa-icon']} />)
            } else {
                vnodes.push(<svg-icon icon-class={icon}/>)
            }
        }
        if (title) {
            vnodes.push(<span>{(title)}</span>)
        }
        return vnodes
    }
}
</script>
<style>
.sub-fa-icon{
    width: 24px;
    height: 15px;
    text-align: center;
    margin-right: 5px;
}
</style>
