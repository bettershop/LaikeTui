<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class Plugin {

    public function Plugin_interface() {
        $name = [];
        $handler = opendir(MO_Plugin_DIR );
        while( ($filename = readdir($handler)) !== false ){
            // 略过linux目录的名字为'.'和‘..'的文件
            if($filename != '.' && $filename != '..'){
                // 输出文件名
                $name[] = $filename;
            }
        }
        return $name;

    }
    function get_plugins($plugin_folder = '') {

        $wp_plugins = array ();
        $plugin_root = MO_Plugin_DIR;
        if ( !empty($plugin_folder) ){
            $plugin_root .= $plugin_folder;
        }

        $plugins_dir = @ opendir( $plugin_root); // 打开目录
        $plugin_files = array();
        if ( $plugins_dir ) {
            while (($file = readdir( $plugins_dir ) ) !== false ) { // 打开一个目录，读取它的内容

                if ( substr($file, 0, 1) == '.' ){
                    continue;
                }

                if ( is_dir( $plugin_root.'/'.$file ) ) { // 函数检查指定的文件是否是目录。
                    $plugins_subdir = @ opendir( $plugin_root.'/'.$file ); // 打开目录
                    if ( $plugins_subdir ) {
                        while (($subfile = readdir( $plugins_subdir ) ) !== false ) {
                            if ( substr($subfile, 0, 1) == '.' ){
                                continue;
                            }
                            if ( substr($subfile, -4) == '.php' ){
                                $plugin_files[] = "$file/$subfile";
                            }
                        }
                        closedir( $plugins_subdir ); // 关闭
                    }
                } else {
                    if ( substr($file, -4) == '.php' )
                        $plugin_files[] = $file;
                }
            }
            closedir( $plugins_dir ); // 关闭
        }
        if ( empty($plugin_files) ){ // 为空
            return $wp_plugins;
        }

        foreach ( $plugin_files as $plugin_file ) {
            if ( !is_readable( "$plugin_root/$plugin_file" ) ){ // 函数判断指定文件名是否可读(不可读)。
                continue;
            }
            $plugin_data = $this->get_plugin_data( "$plugin_root/$plugin_file", false, false ); //Do not apply markup/translate as it'll be cached. 不要应用标记/翻译，因为它将被缓存。

            if ( empty ( $plugin_data['Name'] ) ){
                continue;
            }

            $wp_plugins[plugin_basename( $plugin_file )] = $plugin_data;
        }

        uasort( $wp_plugins, '_sort_uname_callback' ); // 使用用户自定义的比较函数对数组 $wp_plugins 中的元素按键值进行排序

        return $wp_plugins;
    }
    
    function get_plugin_data( $plugin_file, $markup = true, $translate = true ) {

        $default_headers = array(
            'Name' => 'Plugin Name', // 插件名称
            'PluginURI' => 'Plugin URI', // 插件路径
            'Version' => 'Version', // 版本
            'Description' => 'Description', // 描述
            'Author' => 'Author', // 作者
            'AuthorURI' => 'Author URI',
            'TextDomain' => 'Text Domain', // 文本域
            'DomainPath' => 'Domain Path', // 域名路径
            'Network' => 'Network', // 网络
            '_sitewide' => 'Site Wide Only', // 站点
        );
        $plugin_data = $this->get_file_data( $plugin_file, $default_headers, 'plugin' );
        return $plugin_data;
    }
    
    
    function get_file_data( $file, $default_headers, $context = '' ) {
        // 我们不需要对文件进行写入，所以只需打开即可阅读。
        // We don't need to write to the file, so just open for reading.
        $fp = fopen( $file, 'r' );

        // 只将文件中的第一个8KIB拉进去。
        // Pull only the first 8kiB of the file in.
        $file_data = fread( $fp, 8192 );
        // PHP将关闭文件句柄，但我们是好公民。
        // PHP will close file handle, but we are good citizens.
        fclose( $fp );

        // 确保我们只赶上CR线结束。
        // Make sure we catch CR-only line endings.
        $file_data = str_replace( "\r", "\n", $file_data );


        if ( $context && $extra_headers = apply_filters( "extra_{$context}_headers", array() ) ) {
            // 通过合并两个数组来创建一个新数组，其中的一个数组元素为键名，另一个数组元素为键值
            $extra_headers = array_combine( $extra_headers, $extra_headers ); // keys equal values
            // 合并一个或多个数组
            $all_headers = array_merge( $extra_headers, (array) $default_headers );
        } else {
            $all_headers = $default_headers;
        }
        foreach ( $all_headers as $field => $regex ) {
            // 执行匹配正则表达式
            if ( preg_match( '/^[ \t\/*#@]*' . preg_quote( $regex, '/' ) . ':(.*)$/mi', $file_data, $match ) && $match[1] ){
                $all_headers[ $field ] = _cleanup_header_comment( $match[1] );
            }else{
                $all_headers[ $field ] = '';
            }
        }
        return $all_headers;
    }

}
?>