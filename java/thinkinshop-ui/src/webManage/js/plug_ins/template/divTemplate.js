import { index } from '@/api/plug_ins/template'
// var deviceWidth;
//         setHtmlFontSize();
//         setIframe();

//         setTimeout(function () {
//             $('#iframe_tem').css('zoom', 1)
//         }, 1000)

//         if (window.addEventListener) {
//             window.addEventListener(
//                 "resize",
//                 function () {
//                     setHtmlFontSize();
//                     setIframe();
//                 },
//                 false
//             );
//         }

//         window.onload = function () {
//             let len = $('.template-content-item').length
//             if (len < 10) {
//                 let res = 10 - len
//                 for (let i =0; i < res; i++) {
//                     let str = `<div class="template-content-item empty">
// <div class="template-content-item-cover"></div>
// </div>`
//                     $('.template-content').append(str)
//                 }
//             }
//             // debugger
//         }

//         function accMul(arg1, arg2) {
//             var m = 0,
//                 s1 = arg1.toString(),
//                 s2 = arg2.toString();
//             try {
//                 m += s1.split(".")[1].length;
//             } catch (e) {}
//             try {
//                 m += s2.split(".")[1].length;
//             } catch (e) {}
//             return (
//                 (Number(s1.replace(".", "")) * Number(s2.replace(".", ""))) /
//                 Math.pow(10, m)
//             );
//         }

//         function accDiv(arg1, arg2) {
//             var t1 = 0,
//                 t2 = 0,
//                 r1,
//                 r2;
//             try {
//                 t1 = arg1.toString().split(".")[1].length;
//             } catch (e) {}
//             try {
//                 t2 = arg2.toString().split(".")[1].length;
//             } catch (e) {}
//             with (Math) {
//                 r1 = Number(arg1.toString().replace(".", ""));
//                 r2 = Number(arg2.toString().replace(".", ""));
//                 return (r1 / r2) * pow(10, t2 - t1);
//             }
//         }

//        function setIframe() {
//             let iframe = window.document.getElementById("iframe");
//             console.log(iframe);
//             deviceWidth = document.documentElement.clientWidth;

//             console.log(deviceWidth)
//             let width = accMul(deviceWidth, 0.177083333);
//             console.log(width);
//             let height = accMul(deviceWidth, 0.315104167);
//             let transform = 'scale(' + accDiv(1, accDiv(1721, deviceWidth)) + ')';
//             console.log(transform);
//             $("#iframe").css("transform", transform);
//         }

//         function setHtmlFontSize() {
//             // 1920是设计稿的宽度，当大于1920时采用1920宽度，比例也是除以19.20
//             deviceWidth = document.documentElement.clientWidth;
//             document.getElementsByTagName("html")[0].style.cssText =
//                 "font-size:" + deviceWidth / 17.21 + "px !important";
//         }
export default {
    name: 'divTemplate',

    data() {
        return {
            imgList: [
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                },
                { 
                    img: require('../../../../assets/imgs/default.png')
                }
            ],

            H5_domain: ''
        }
    },

    created() {
        this.index()
    },

    methods: {
        async index() {
            const res = await index({
                api: 'admin.diy.index',
            })

            console.log(res);

            this.H5_domain = res.data.data.H5_domain
        },

        // 新建模板
        addCoupons() {

        },
        
        // 编辑
        editor() {
            this.$router.push({
                path: '/plug_ins/template/playlist',
                // query: {
                //     id: value.id
                // }
            })
        },

        // 应用
        Application() {

        },

        // 删除
        Delete() {

        }
    }
}