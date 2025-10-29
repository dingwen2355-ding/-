const getters = {
  ratio: state => state.app.ratio,
  precinctName: state => state.app.precinctName,
  precinctId: state => state.app.precinctId,
  eventId: state => state.app.eventId,
  resizeValue: state => state.view.resizeValue,
  intervalRefresh: state => state.view.intervalRefresh,
  centerViewModule: state => state.view.centerViewModule,
  infoWindowType: state => state.map.infoWindowType
}
export default getters
