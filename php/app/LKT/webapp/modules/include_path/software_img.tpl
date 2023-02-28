

{literal}
<div class="upload_picture" id="file_select_modal" data-backdrop="static" style="z-index: 99999999;">
	<div class="upload_flex" role="document">
		<div class="upload_auto">
			<div class="upload_title">
				选择文件
				<img src="images/icon/cha.png" class="cancel-group-list upload_cha"/>
			</div>
			<div class="upload_content" id="file">
				
				
				<div class="upload_view">
					<div class="upload_l">

						<template v-for="(item,index) in group_list" v-if="item.is_delete != 1">
							<div :class="index==selected?'item selected-group active':'item selected-group'" :data-index="index">
								<template v-if="index == edit_group">
									<div class="upload_input">
										<input class="form-control name-group" :value="item.name">
										
										<div class="upload_input_block">
											<div class=" save-group">确定</div>
											<div class="cancel-group">
												<a href="javascript:void(0);">取消</a>
											</div>
										</div>
									</div>
									
								</template>
								<template v-else>
									<div class="upload_list">
										<template v-if="item.is_default != 1">
											<div class="text-more upload_more">{{item.name}}</div>
											<div class="upload_setting">
												<img src="images/iIcon/bj.png" class="setting-group" :data-index="index"/>
											</div>
										</template>
										<template v-else>
											<div class="text-more upload_more upload_mask">{{item.name}}</div>
										</template>
									</div>
									<template v-if="item.is_default != 1">
										<div class="edit-block upload_edit_block"  v-if="index == edit_setting">
											<div class="edit-group" :data-index="index">编辑
											</div>
											<div class="delete-group" :data-index="index">删除
											</div>
										</div>
									</template>
								</template>
							</div>
						</template>
						<div class="add-group upload_add">
							<img src="images/iIcon/tj.png"/> 添加分组
						</div>
					</div>
					<div class="upload_r file-list">
						<!--标签-->
						<div class="upload_r_title">
							<!--全选-->
							<div class="upload-group upload_group">
								<label class="checkbox-label">
                                    <input type="checkbox" name="checkbox" value="1">
                                    <span class="label-icon"></span>
                                    <span class="label-text">全选</span>
                                </label>
							</div>
							
							<!--删除-->
							<div class="upload_dell">
								<a class="btn-danger delete-file-group reset" href="javascript:">
									<img src="images/iIcon/shc.png" class="ul_img1"/>
									<img src="images/iIcon/sha_h.png" class="ul_img2"/>
									删除</a>
							</div>
							
							<!--移动-->
							<div class="dropdown upload_dropdown">
								<button class="reset" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                   	 移动
                                </button>
                                <div class="upload_dropdown_l">
                                	<img src="images/iIcon/zf_h.png" class="ul_img1" />
                                	<img src="images/iIcon/yd.png" class="ul_img2" />
                                </div>
                                <div class="upload_dropdown_r">
                                	<img src="images/iIcon/jtx.png" class="ul_img1" />
                                	<img src="images/iIcon/jiant.png" class="ul_img2" />
                                </div>
								<div class="dropdown-menu upload_menu" aria-labelledby="dropdownMenuButton">
									<template v-for="(item,index) in group_list" >
										<a href="javascript:void(0)" class="btn-secondary batch-group dropdown-item text-more" :data-index="index" style="width: 100%;">{{item.name}}</a>
									</template>
								</div>
							</div>
							
							<!--上传-->
							<div class="upload-group upload_images">
								<a class="btn-primary upload-file-group" href="javascript:">
									<img src="images/iIcon/shangc.png" class="ul_img3"/>
									<img src="images/iIcon/shangc_h.png" class="ul_img4"/>
									  上传图片</a>
							</div>
						</div>
						
						<div class="upload_h"></div>
						<!--图片-->
						<template v-for="(item,index) in list">
							<div class="file-item text-center" :data-name="item.file_url" :data-url="item.file_url" :data-index="index" style="position: relative">
								<img :src="item.file_url" class="file-cover">
								<div class='mask' v-if="item.selected == 1"></div>
							</div>
						</template>
						
						<div class="file-loading text-center upload_loading">
							<img style="height: 1.14286rem;width: 1.14286rem" src="images/loading-2.svg">
						</div>
						
						<div class="text-center upload_text">
							<a style="display: none" href="javascript:" class="file-more">加载更多</a>
						</div>
						
					</div>
				</div>
				
				
				<div class="upload_footer">
					
					<div class="cancel-group-list ta_btn4">取消</div>
					<div class="save-group-list ta_btn3 buttom_hover">确定</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="pick_link_modal">
    <div class="modal fade pick-link-modal" data-backdrop="static">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">选择链接</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group mb-3">
                        <span class="input-group-addon">可选链接</span>
                        <select class="form-control link-list-select">
                            <option value="">点击选择链接</option>
                            <option v-for="(link,i) in link_list"
                                    v-if="in_array(link.open_type,open_type) != -1"
                                    v-bind:value="i">
                                {{link.name}}
                            </option>
                        </select>
                    </div>
                    <template v-if="selected_link">
                        <template v-if="selected_link.params && selected_link.params.length>0">
                            <div class="form-group row" v-for="(param,i) in selected_link.params">
                                <label class="col-sm-2 text-right col-form-label" :class="param.required">{{param.key}}</label>
                                <div class="col-sm-10">
                                    <input class="form-control paramVal" v-model="param.value">
                                    <div class="fs-sm text-muted" v-if="param.desc">{{param.desc}}</div>
                                </div>
                            </div>
                            <div class="fs-sm text-muted" v-if="is_required"><p style="color: red; text-align: center;">请填写标记 * 参数</p></div>
                        </template>
                        <div v-else class="text-center text-muted">此页面无需配置参数</div>
                    </template>
                    <div v-else class="text-center text-muted">请选择链接</div>
                </div>
                <div class="modal-footer">
                    <a class="btn btn-primary pick-link-confirm" href="javascript:">确定</a>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 地区选择模态框 -->
<div class="modal fade" id="district_pick_modal">
    <div class="modal-dialog">
        <div class="panel">
            <div class="panel-header">
                <span>选择地区</span>
                <a href="javascript:" class="panel-close" data-dismiss="modal">&times;</a>
            </div>
            <div class="panel-body">
                <table class="w-100">
                    <colgroup>
                        <col style="width: 33.333333%">
                        <col style="width: 33.333333%">
                        <col style="width: 33.333333%">
                    </colgroup>
                    <tr>
                        <td>省</td>
                        <td>市</td>
                        <td>县/区</td>
                    </tr>
                    <tr>
                        <td>
                            <select v-model="province_id" v-on:change="provinceChange" class="form-control">
                                <option :value="item.id" v-for="(item,index) in province_list">{{item.name}}
                                </option>
                            </select>
                        </td>
                        <td>
                            <select v-model="city_id" v-on:change="cityChange" class="form-control">
                                <option :value="item.id" v-for="(item,index) in city_list">{{item.name}}</option>
                            </select>
                        </td>
                        <td>
                            <select v-model="district_id" v-on:change="districtChange" class="form-control">
                                <option :value="item.id" v-for="(item,index) in district_list">{{item.name}}
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="panel-footer">
                <a class="btn btn-primary district-confirm-btn" href="javascript:">确定</a>
            </div>
        </div>
    </div>
</div>


{/literal}