<template>
  <div class="l-pagination">
    显示
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :pager-count="5"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pagination.pagesize"
      layout="sizes, slot, prev, pager, next"
      prev-text="上一页"
      next-text="下一页"
      :total="pagination.total"
    >
      <slot> 当前显示{{ showItem }}条，共 {{ pagination.total }} 条记录 </slot>
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "l-pagination",
  props: {
    pagination: {
      type: Object,
      default: () => {
        return {
          total: 1,
          pagesize: 10,
          currentPage: 1
        };
      }
    }
  },
  data() {
    return {
      size: 10,
      page: 1,
      currentPage: 1
    };
  },
  computed: {
    showItem() {
      let showItem1 = (this.page - 1) * this.size + this.size;
      if (showItem1 > this.pagination.total) {
        showItem1 = this.pagination.total;
      }

      let showItem = (this.page - 1) * this.size + 1 + "-" + showItem1;
      return showItem;
    }
  },
  created() {
    this.size = this.pagination.pagesize;
    this.page = this.pagination.currentPage;
  },
  methods: {
    handleSizeChange(val) {
      this.size = val;

      if (this.page == 1) {
        this.$emit("pagechange", { page: this.page, size: this.size });
      }
    },
    handleCurrentChange(val) {
      this.page = val;
      this.$emit("pagechange", { page: this.page, size: this.size });
    }
  }
};
</script>
