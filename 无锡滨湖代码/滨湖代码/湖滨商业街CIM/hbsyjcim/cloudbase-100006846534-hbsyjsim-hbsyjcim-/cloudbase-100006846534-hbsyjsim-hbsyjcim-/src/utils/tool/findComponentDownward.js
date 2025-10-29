// 由一个组件，向下找到最近的指定组件const comB = findComponentDownward(this, 'componentB');
function findComponentDownward (context, componentName) {
    const childrens = context.$children
    let children = null
    if (childrens.length) {
      for (const child of childrens) {
        const name = child.$options.name
        if (name === componentName) {
          children = child
          break
        } else {
          children = findComponentDownward(child, componentName)
          if (children) break
        }
      }
    }
    return children
  }
  export { findComponentDownward }
  