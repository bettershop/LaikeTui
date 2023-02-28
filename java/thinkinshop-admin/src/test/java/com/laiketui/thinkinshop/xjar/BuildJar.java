package com.laiketui.thinkinshop.xjar;

import io.xjar.XCryptos;

public class BuildJar {

    public static void main(String[] args) throws Exception {

        XCryptos.encryption()
                .from("/Users/wangxian/Documents/java/open/thinkinshop-api/thinkinshop/target/thinkinshop-1.0.0.jar")
                .use("pswd")
                .include("/com/laiketui/**/*.class")
                .exclude("/static/**/*")
                .to("thinkinshop-1.0.1.jar");

    }
}
