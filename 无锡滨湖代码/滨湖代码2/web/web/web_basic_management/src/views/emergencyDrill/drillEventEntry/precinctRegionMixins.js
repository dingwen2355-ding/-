import request from '@/utils/request'
export const regionPrecinctMixin = {
  data() {
    return {
      precinctList: [],
      communityList: [],
      streetList: []
    }
  },
  methods: {
    // precinctKind：100-区 110-街道 120-社区 ;upPrecinctId： 上级id
    getPrecinctList() {
      let url = '/binhuapis/regional/precinct'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.precinctList = data
          this.communityList = this.precinctList.filter((item) => item.precinctKind === 120)
          this.streetList = this.precinctList.filter((item) => item.precinctKind === 110)
        }
      })
    }
  },
  watch: {
    communityList() {
      const filterCommunity = this.communityList.filter((item) => item.precinctName === this.searchForm.communityName)
      if (filterCommunity.length === 0) {
        this.searchForm.communityName = ''
      }
    },
    'searchForm.streetName'(val) {
      if (!val) {
        this.communityList = this.precinctList.filter((item) => item.precinctKind === 120)
        this.streetList = this.precinctList.filter((item) => item.precinctKind === 110)
      }
      const [street] = this.precinctList.filter((item) => {
        return item.precinctName === this.searchForm.streetName
      })
      if (!street) return
      this.communityList = this.precinctList.filter((item) => item.up_precinct_id === street.precinctId)
    },
    'searchForm.communityName'(val) {
      if (!val) return
      if (this.isCode === 1) return
      const [community] = this.precinctList.filter((item) => {
        return item.precinctName === this.searchForm.communityName
      })
      if (!community) return
      const [street] = this.precinctList.filter((item) => item.precinctId === community.up_precinct_id)
      this.searchForm.streetName = street.precinctName
    }
  }
}
