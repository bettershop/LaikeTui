import { dictionaryList } from '@/api/Platform/numerical'
export const mixinstest = {
    data(){
        return {
            total:20,
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
    },
    created(){
      dictionaryList({
        api: 'saas.dic.getDictionaryInfo',
        key: '分页',
      }).then(res => {
        if(res.data.data.list.length !== 0) {
          let pagesize = res.data.data.list.map(item => {
              return parseInt(item.text)
          })
          pagesize = pagesize.sort(function(a, b){return a - b})
          console.log(pagesize);
          this.pagesizes = pagesize
        }
        
      })
    },
    methods:{

    },
    computed:{
    },
    watch: {
    }
}