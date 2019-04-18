/**
 * Created by Administrator on 2017/9/14.
 */

    // Custom example logic
$(document).ready(function () {
    var video_picker = $('.video-picker');
    video_picker.each(function (i) {
        var picker = this;
        var el = $(this);
        var btn = el.find('.video-picker-btn');
        var url = el.data('url');
        var input = el.find('.video-picker-input');
        var view = el.find('.video-preview');

        function uploaderVideo() {

            var el_id = $.randomString(32);
            btn.attr("id", el_id);

            var uploader = new plupload.Uploader({
                runtimes: 'html5,flash,silverlight,html4',
                browse_button: el_id, // you can pass an id...
                url: url,
                flash_swf_url: 'Moxie.swf',
                silverlight_xap_url: 'Moxie.xap',

                filters: {
                    max_file_size: '50mb',
                    mime_types: [
                        {title: "Video files", extensions: "mp4"}
                    ]
                },

                init: {
                    PostInit: function () {

                    },

                    FilesAdded: function (up, files) {
                        $('.form-error').hide();
                        uploader.start();
                        btn.btnLoading("正在上传");
                        uploader.disableBrowse(true);

                        plupload.each(files, function (file) {
                            console.log(file)
                            view.html('<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>');
                        });
                    },
                    FileUploaded: function (uploader, file, responseObject) {
                        if (responseObject.status == undefined || responseObject.status != 200) {
                            return true;
                        }
                        var res = $.parseJSON(responseObject.response);
                        if (res.code != 0) {
                            $($("#" + file.id).parent('.video-preview')).append('<div>'+res.msg+'</div>');
                            return true;
                        }
                        $(input).val(res.data.url);
                        $('.video-check').prop('href',res.data.url);
                        $('.video-preview').find('span').html('100%');
                        $(input).change();
                    },

                    UploadProgress: function (up, file) {
                        var percent = file.percent - 1;
                        $($("#" + file.id).find('b')[0]).html('<span>' + percent + "%</span>");
//                            document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                    },

                    Error: function (up, err) {
                        $('.form-error').html('文件大小超出配置').show();
//                            document.getElementById('console').appendChild(document.createTextNode("\nError #" + err.code + ": " + err.message));
                    },
                    UploadComplete: function (uploader, files) {
                        btn.btnReset();
                        uploader.destroy();
                        uploaderVideo();
                    }
                }
            });
            uploader.init();
        }

        uploaderVideo();
    });
});