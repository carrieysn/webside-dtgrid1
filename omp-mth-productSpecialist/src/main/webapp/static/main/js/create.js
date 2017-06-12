$(function(){
	initEvent();	//初始化相关事件与控件
	validDatePicker.on('select', function(){
		productForm.check(false, "[name='valid_thru']");
	});
	
	//发布验证
	var productForm = $('#productForm').Validform({
		tiptype : 3,
		datatype:{
			"relativePrice": function(gets,obj,curform,regxp){
				if(regxp.money.test(gets)){
					var market_price_el = $("#productForm input[name='market_price']");
					var market_price = market_price_el.val();
					var discount_price_el =  $("#productForm input[name='discount_price']")
					var discount_price = discount_price_el.val();
					if(market_price && discount_price){
						if(parseFloat(gets) < 0){
							return '请输入正确的金额！';
						}
						if(parseFloat(market_price) < parseFloat(discount_price)){
							return false;
						}else{
							market_price_el.removeClass("Validform_error");
							market_price_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
							discount_price_el.removeClass("Validform_error");
							discount_price_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
						}
					}
					return;
				}
				return "请输入正确的金额！";
			},
			"minNum": function(gets,obj,curform,regxp){
				if(regxp.n.test(gets)){
					if(parseInt(gets) < 1){
						return "数量不能少于一！";
					}
					return true;
				}
				return "请填写数字！";
			},
			/*,
			"orderNum": function(gets,obj,curform,regxp){
				if(regxp.n.test(gets)){
					var min_buy_qty_el = $("#productForm input[name='min_buy_qty']");
					var min_buy_qty = min_buy_qty_el.val();
					var stock_qty_el = $("#productForm input[name='sale_qty']")
					var stock_qty = stock_qty_el.val();
					if(min_buy_qty && stock_qty){
						if(parseInt(min_buy_qty) > parseInt(stock_qty)){
							return false; 
						} else if(parseInt(min_buy_qty) < 1){
							return '起订量不能少于一！';
						} else if(parseInt(stock_qty) < 1){
							return '库存量不能少于一！';
						} else{
							min_buy_qty_el.removeClass("Validform_error");
							min_buy_qty_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
							stock_qty_el.removeClass("Validform_error");
							stock_qty_el.nextAll(".Validform_checktip").removeClass("Validform_wrong");
						}
					}
					return true;
				}
				return "请输入正确的数量！";
			}*/
		}
	});
	
	//发布
	$("#submit_btn").on('click', function () {
		//验证并初始化一些表单信息
		if(productForm.check(false)){
			//验证成功、将上传的图片数据进行格式化
			var productImgs = new Array();
			//产品图片
			$(".pic_td .product_img").each(function(){
				var img = {};
				img.path_id = $(this).attr("doc_id");
				img.title = $(this).parent().parent().parent().find(".pic_desc").val();
				productImgs.push(img);
			});
			if(productImgs.length < 1){
				M.alert('请至少上传一张产品图片');
				return false;
			} else{
				$("#pic_info").val(JSON.stringify(productImgs));
			}
			//详情图片
			var detailImgs = new Array();
			$(".pic_detail_td .product_img").each(function(){
				var img = {};
				img.path_id = $(this).attr("doc_id");
				img.title = $(this).parent().parent().parent().find(".pic_desc").val();
				detailImgs.push(img);
			});
			$("#pic_detail_info").val(JSON.stringify(detailImgs));
			
			//发布产品信息
			var min_buy_qty = parseInt($("#productForm input[name='min_buy_qty']").val() || "0");	//起订量
			var sale_qty = parseInt($("#productForm input[name='sale_qty']").val() || "0");	//库存
			if(sale_qty < min_buy_qty){
				layer.confirm("库存量少于起订量，是否确定发布？",{icon: 3}, function(index){
					layer.close(index);
					publish();
				});
			} else{
				layer.confirm("是否确认发布产品？", function(index){
					layer.close(index);
					publish();
				});
			}
		}
	});
	
	//发布产品信息
	function publish(){
		formatDeliveryArea();
		formatDisplayArea();
		var loadIdContract = layer.load();
		M.post(ctx + "/product/save", M.formatElement("#productForm"), function(result){
		 //发布成功
		 layer.closeAll();
		 M.alert('发布成功！', function(){
			 var goods_id = result.data; //产品编号
			 window.location.href = ctx + "/product/detail?goods_id=" + goods_id; 
		 });
		}, function(result){
			layer.close(loadIdContract);
			M.alert(result.msg || '发布失败!');
		});
		//格式化已选择的地区
		function formatDeliveryArea(){
			var areaArray = new Array();
			$(".delivery_area_div .selectContainer").each(function(){
				var _this = this;
				var district = $(this).find(".bind-district").val();
				var city = $(this).find(".bind-city").val();
				var province = $(this).find(".bind-province").val();
				var areaCode = district || city || province;
				if(areaCode){
					areaArray.push(areaCode);
				}
			});
			$("#delivery_area").val(areaArray.join(",") || "100000");  //默认为全国
			return false;
		}
		
		//格式化类目
		function formatDisplayArea(){
			var array = new Array();
			$("input[name='display_area_ck']:checked").each(function(){
				array.push($(this).val());
			});
			$("#display_area").val(array.join(","));
		}
	}
	
	//初始化相关事件与控件
	function initEvent(){
		bindAddTagEvent();	//绑定添加标签事件
		bindClearTagEvent();//绑定清除标签事件
		initUpload();		//初始化上传控件
		bindUploadEvent();	//绑定上传事件，弹出框，填入照片与描述
		initSelector();		//初始化地区选择
		initAreaCheck();	//初始化类目选择框
		//initProductSource();	//初始化链接显示
		
		//绑定添加标签事件
		function bindAddTagEvent(){
			$("#add_tag").click(function() {
				var TagObj = $(this).prevAll('.teg-input');
				var tag = TagObj.val();
				if (tag.length < 2) {
					M.alert('标签不能少于两个字！');
				} else {
					TagObj.val('').before("<small>" + tag + "</small>");
					var tags = new Array();
					$(".td-tag small").each(function(){
						tags.push($(this).text());
					});
					$("#tags_str").val(tags.join(","));
				}
			});
		}
		
		//绑定清除标签事件
		function bindClearTagEvent(){
			$("#clear_tag").click(function() {
				$(".td-tag small").remove();
				$("#tags_str").val('');
			});
		}
		
		//初始化上传控件
		function initUpload(){
			$('.upload-img').each(function(){
				var imgUpload = this;
				var uploader = new plupload.Uploader({
					runtimes: 'gears,html5,html4,flash,silverlight',
					browse_button : imgUpload, 	//触发文件选择对话框的按钮，为那个元素id
					url : ctx + '/file/upload.do', //服务器端的上传页面地址
					flash_swf_url : 'js/Moxie.swf', 		//swf文件，当需要使用swf方式进行上传时需要配置该参数
					silverlight_xap_url : 'js/Moxie.xap', 	//silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
					file_data_name: 'up_load_file',
					filters : {
						max_file_size : '200kb',
						mime_types: [
							{title : "Image files", extensions : "jpg"}
						]
					},
					init: {
						FilesAdded: function(up, files) {
							up.start();
						},
						UploadProgress: function(up, file) {
							$('#'+file.id).find('p').text(file.percent + '%');
						},
						Error: function(up,err){
							if(err.code == '-600'){
								M.alert('图片尺寸不符，请重新选择上传！');
							}
						},
						FileUploaded: function(up, file, responseObject) {
							var data = JSON.parse(responseObject.response)	// 上传接口返回的数据
							var doc_id = data.data;
							$(".audit-cen .preview").show();
							$(".audit-cen .preview img").attr("doc_id", doc_id).attr("src", ctx + "/file/preview.do?doc_id=" + doc_id);
							$(".audit-cen .upload-img").hide();
							$(imgUpload).hide().next("div").hide();
						}
					}
				});
				uploader.init();
			});
		}
		
		//绑定上传事件，弹出框，填入照片与描述
		function bindUploadEvent(){
			$(".add-img").not(".upload-img").bind("click", function(){
				var imgUpload = this;
				$(imgUpload).addClass("curret_add_img");
				layer.open({
				    type: 1,
				    title: '添加图片',
				    btn: ['确定', '取消'],
				    shadeClose: true,
				    shade: 0.8,
				    shadeClose: false,
				    area: ['600px', '500px'],
				    content: $("#export-div"),
				    yes: function(index, layero){
				    	var desc1 = $("#upload-img-desc").val();
						var doc_id = $(".audit-cen .preview img").attr("doc_id");
						var removeEl = $(imgUpload).parent().siblings('em');  //移除图片元素
						if(desc1.length>100){
							layer.msg('描述最多只能输入100字!');
							return false ;
							}else{
								if(doc_id){
									//添加图片信息
									var img = $('<img class="product_img" doc_id="' + doc_id + '" src="' + ctx + '/file/preview.do?doc_id=' + doc_id + '">');	// 需要展示的图片
									$(imgUpload).parent().siblings('p').find(".pic_desc").val(desc1);
									$(imgUpload).parent().siblings('p').find("label").text(desc1 || "添加描述");
									$(imgUpload).closest("p").find(".preview").append(img);
									$(imgUpload).hide().next("div").hide();
									removeEl.show();
									//删除图片事件
									removeEl.unbind("click").bind("click", function(){
										$(this).hide();
										$(imgUpload).closest("p").find(".preview").empty();
										$(imgUpload).parent().siblings('p').find(".pic_desc").val("");
										$(imgUpload).parent().siblings('p').find("label").text("添加描述");
										$(imgUpload).show().next("div").show();
									});
								}else{
									layer.msg('图片不能为空！');
									return false ;
								}
							}
						//关闭层
						layer.close(index);
						
				    },
					cancel: function(index){
				    	layer.close(index);
				    },
				    end: function(){  //关闭层事件
				    	//还原之前样式
				    	$(imgUpload).removeClass("curret_add_img");
				    	$(".audit-cen .preview").hide();
				    	$(".audit-cen .preview img").removeAttr("doc_id").removeAttr("src");
				    	$(".audit-cen .upload-img").show();
				    	$("#upload-img-desc").val("");
				    }
				}); 
			});
		}
	
		//初始化地区选择
		function initSelector(){
			M.area(".selectContainer");
			/*$(".delivery_area_div .selectContainer .add").first().bind("click.select", function(){
				//添加一个配送区域
				var selectedEl = $(this).closest(".selectContainer").clone(false);
				selectedEl.find("option").remove();
				selectedEl.find("._path_").val("");
				selectedEl.appendTo(".delivery_area_div");
				M.area(selectedEl);
				selectedEl.find(".add").text("-").unbind("click.select").bind("click.select", function(){
					$(this).closest(".selectContainer").remove();
				});
			});*/
		}
		
		//初始化类目选择框
		function initAreaCheck(){
			if(goods_display_area){
				var areas = goods_display_area.split(",");
				for(var i = 0;i < areas.length;i++){
					var value = areas[i];
					$("input[name='display_area_ck'][value='" + value + "']").attr("checked", true);
				}
			}
		}
		
		//初始化链接显示
//		function initProductSource(){
//			$("#category_select").change(function(){
//				var value = $(this).val();
//				if("试样" == value){
//					$("#product_source_tr").show();
//				} else{
//					$("#product_source_tr").hide();
//				}
//			}).change();
//		}
	}
	
	/**初始化供应商选择框
	initSupplierSelector();
	function initSupplierSelector(){
		var selector = $("#supplier_select").select2({
			placeholder: "请输入关键字",
			language: "zh-CN",
			minimumInputLength: 1,
			ajax: {
				url: ctx + "/stores/querySupplierListPage",
				dataType: 'json',
				cache: true,
				delay: 250,
				data: function(params) {
					var term = $.trim(params.term);
					var pageNum = params.page || 1;
					return {
						search_input: term,
						pageSize: 30,
						pageNum: pageNum
					}
				},
				processResults: function(data, params) { //结果处理
					params.page = params.page || 1;
					var results = data.data.result;
					if(results.length > 0){
						var more = (params.page * 30) < data.data.total;
						return {
							results: results,
							pagination:{
								more: more
							}
						}
					} else{
						return {
							results: results
						}
					}
				}
			}
		});
	}**/
});