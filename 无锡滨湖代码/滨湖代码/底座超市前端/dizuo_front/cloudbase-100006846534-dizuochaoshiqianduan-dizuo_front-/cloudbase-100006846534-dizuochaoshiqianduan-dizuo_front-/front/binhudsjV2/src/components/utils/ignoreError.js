import Vue from "vue";

Vue.config.warnHandler = function(msg) {
  if (
    !msg.includes(
      "Avoid mutating a prop directly since the value will be overwritten whenever the parent component re-renders."
    )
  ) {
    return console.warn && console.warn(msg);
  }
};
