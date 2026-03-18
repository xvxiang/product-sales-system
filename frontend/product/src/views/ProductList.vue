<template>
  <div class="product-container">
    <!-- 1. 搜索区域 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="商品编码">
          <el-input v-model="searchForm.productCode" placeholder="请输入编码" clearable />
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="searchForm.name" placeholder="请输入名称" clearable />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="searchForm.categoryId" placeholder="全部" clearable style="width: 150px">
            <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="全部" clearable style="width: 120px">
            <el-option label="上架" :value="1" />
            <el-option label="下架" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 2. 操作按钮区域 -->
    <el-card class="action-card">
      <el-button type="primary" icon="Plus" @click="openDialog('add')">新增商品</el-button>
      <el-button type="danger" icon="Delete" :disabled="selectedIds.length === 0" @click="handleBatchDelete">
        批量删除
      </el-button>
      <el-button type="success" icon="Upload" @click="triggerImport">导入 Excel</el-button>
      <el-button type="warning" icon="Download" @click="handleExport">导出数据</el-button>
      
      <!-- 隐藏的文件上传输入框 -->
      <input ref="fileInput" type="file" style="display: none" accept=".xlsx, .xls" @change="handleFileUpload" />
    </el-card>

    <!-- 3. 数据表格 -->
    <el-card class="table-card">
      <el-table 
        :data="tableData" 
        border 
        style="width: 100%" 
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="productCode" label="商品编码" width="120" />
        <el-table-column prop="name" label="商品名称" min-width="150" />
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column prop="stock" label="库存" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="openDialog('edit', scope.row)">编辑</el-button>
            <el-button link type="primary" size="small" @click="showChart(scope.row)">销量趋势</el-button>
            <el-button link type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="searchForm.pageNum"
          v-model:page-size="searchForm.pageSize"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="fetchData"
          @current-change="fetchData"
        />
      </div>
    </el-card>

    <!-- 4. 新增/编辑 弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogType === 'add' ? '新增商品' : '编辑商品'" width="500px">
      <el-form :model="formData" label-width="80px" :rules="rules" ref="formRef">
        <el-form-item label="商品编码" prop="productCode">
          <el-input v-model="formData.productCode" :disabled="dialogType === 'edit'" />
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="formData.categoryId" placeholder="请选择分类" style="width: 100%">
            <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="formData.price" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input-number v-model="formData.stock" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">上架</el-radio>
            <el-radio :label="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 5. 销量趋势图表弹窗 -->
    <el-dialog v-model="chartVisible" title="销售趋势" width="600px">
      <div ref="chartDom" style="width: 100%; height: 300px"></div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Plus, Delete, Upload, Download } from '@element-plus/icons-vue';
import * as echarts from 'echarts';
import { 
  getProductList, addProduct, updateProduct, deleteProduct, 
  batchDeleteProduct, getSalesTrend, importProduct, exportProduct,
  getCategoryList 
} from '@/api/product';

// --- 数据定义 ---
const loading = ref(false);
const tableData = ref([]);
const total = ref(0);
const categoryList = ref([]);
const selectedIds = ref([]);

// 搜索表单
const searchForm = reactive({
  pageNum: 1,
  pageSize: 10,
  productCode: '',
  name: '',
  categoryId: '',
  status: ''
});

// 弹窗相关
const dialogVisible = ref(false);
const dialogType = ref('add'); // 'add' or 'edit'
const formRef = ref(null);
const formData = reactive({
  id: null,
  productCode: '',
  name: '',
  categoryId: '',
  price: 0,
  stock: 0,
  status: 1
});

const rules = {
  productCode: [{ required: true, message: '请输入商品编码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  stock: [{ required: true, message: '请输入库存', trigger: 'blur' }]
};

// 图表相关
const chartVisible = ref(false);
const chartDom = ref(null);
let myChart = null;

// --- 方法定义 ---

// 获取分类列表
const fetchCategories = async () => {
  try {
    const res = await getCategoryList();
    // 假设后端返回结构是 { code: 200, data: [...] }，如果直接返回数组则不用 .data
    categoryList.value = res.data || res; 
  } catch (error) {
    console.error('获取分类失败', error);
  }
};

// 获取商品列表
const fetchData = async () => {
  loading.value = true;
  try {
    const res = await getProductList(searchForm);
    // 适配后端返回结构，通常是 res.data.records 和 res.data.total
    // 请根据你后端实际返回调整，这里假设是 PageResult 对象
    const pageData = res.data || res;
    tableData.value = pageData.list || pageData.records || [];
    total.value = pageData.total || 0;
  } catch (error) {
    console.error('获取列表失败', error);
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  searchForm.pageNum = 1;
  fetchData();
};

const resetSearch = () => {
  searchForm.productCode = '';
  searchForm.name = '';
  searchForm.categoryId = '';
  searchForm.status = '';
  handleSearch();
};

// 打开弹窗
const openDialog = (type, row = null) => {
  dialogType.value = type;
  dialogVisible.value = true;
  if (type === 'edit' && row) {
    Object.assign(formData, row);
  } else {
    // 重置表单
    formData.id = null;
    formData.productCode = '';
    formData.name = '';
    formData.categoryId = '';
    formData.price = 0;
    formData.stock = 0;
    formData.status = 1;
  }
  // 清除校验结果
  nextTick(() => formRef.value?.clearValidate());
};

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return;
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await addProduct(formData);
          ElMessage.success('添加成功');
        } else {
          await updateProduct(formData);
          ElMessage.success('更新成功');
        }
        dialogVisible.value = false;
        fetchData();
      } catch (error) {
        console.error('保存失败', error);
      }
    }
  });
};

// 删除
const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除该商品吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteProduct(id);
      ElMessage.success('删除成功');
      fetchData();
    })
    .catch(() => {});
};

// 批量删除
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id);
};

const handleBatchDelete = () => {
  ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 个商品吗？`, '提示', { type: 'warning' })
    .then(async () => {
      await batchDeleteProduct(selectedIds.value);
      ElMessage.success('批量删除成功');
      fetchData();
    })
    .catch(() => {});
};

// 导入
const fileInput = ref(null);
const triggerImport = () => {
  fileInput.value.click();
};

const handleFileUpload = async (event) => {
  const file = event.target.files[0];
  if (!file) return;
  
  try {
    await importProduct(file);
    ElMessage.success('导入成功');
    fetchData();
  } catch (error) {
    console.error('导入失败', error);
  } finally {
    event.target.value = ''; // 清空以便重复上传
  }
};

// 导出
const handleExport = async () => {
  try {
    const blob = await exportProduct({ type: 'current' }); // 这里可以根据需求传参
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = 'products.xlsx';
    link.click();
    ElMessage.success('导出成功');
  } catch (error) {
    console.error('导出失败', error);
  }
};

// 图表
const showChart = async (row) => {
  chartVisible.value = true;
  await nextTick();
  if (!chartDom.value) return;
  
  if (myChart) myChart.dispose();
  myChart = echarts.init(chartDom.value);
  
  try {
    const res = await getSalesTrend(row.id);
    const data = res.data || res; // 假设返回 { dates: [], values: [] }
    
    const option = {
      title: { text: `${row.name} - 近七日销量` },
      tooltip: {},
      xAxis: { data: data.dates || [] },
      yAxis: {},
      series: [{
        name: '销量',
        type: 'bar',
        data: data.values || [],
        itemStyle: { color: '#409EFF' }
      }]
    };
    myChart.setOption(option);
  } catch (error) {
    ElMessage.error('获取销量数据失败');
  }
};

// 生命周期
onMounted(() => {
  fetchCategories();
  fetchData();
  
  // 监听窗口大小变化自适应图表
  window.addEventListener('resize', () => myChart?.resize());
});
</script>

<style scoped>
.product-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}
.search-card, .action-card, .table-card {
  margin-bottom: 20px;
}
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>