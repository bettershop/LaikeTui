/**
 * 分页参数
 */
export default {
  name: 'page',

  data() {
    return {
      tableData: [],
      loading: true,
      inputInfo: {
        name: '',
        date: [],
      },
      total: 20,
      pagesizes: [10, 25, 50, 100],
      pagination: {
        page: 1,
        pagesize: 10,
      },
      currpage: 1,
      current_num: 10,
      dictionaryNum: 1,
      pageSize: 10,
      showPagebox: true,
    }
  }

}
