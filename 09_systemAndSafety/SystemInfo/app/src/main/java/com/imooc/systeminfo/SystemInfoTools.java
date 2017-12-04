package com.imooc.systeminfo;

import android.os.Build;

public class SystemInfoTools {

    public static String makeInfoString(String[] description, String[] prop) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prop.length; i++) {
            sb.append(description[i]).append(" : ").append(prop[i]).append("\r\n");
        }
        return sb.toString();
    }

    // android.os.Build 包含了系统编译时的大量设备配置信息
    public static String getBuildInfo() {
        String board = Build.BOARD; //主板
        String brand = Build.BRAND; //Android系统定制商
        String supported_abis = Build.SUPPORTED_ABIS[0];    //CPU指令集
        String device = Build.DEVICE;   //设备参数
        String display = Build.DISPLAY; //显示屏参数
        String fingerprint = Build.FINGERPRINT; //唯一编号
        String serial = Build.SERIAL;           //硬件序列号
        String id = Build.ID;                   //修订版本列表
        String manufacturer = Build.MANUFACTURER;   //硬件制造商
        String model = Build.MODEL;         //版本
        String hardware = Build.HARDWARE;   //硬件名
        String product = Build.PRODUCT;     //手机产品名
        String tags = Build.TAGS;           //描述Build的标签
        String type = Build.TYPE;           //类型
        String codename = Build.VERSION.CODENAME;   //当前开发代号
        String incremental = Build.VERSION.INCREMENTAL; //源码控制版本号
        String release = Build.VERSION.RELEASE;         //版本发型号
        String sdk_int = "" + Build.VERSION.SDK_INT;    //版本号
        String host = Build.HOST;       //Host值
        String user = Build.USER;       //User名
        String time = "" + Build.TIME;  //编译时间
        String[] prop = {
                board,
                brand,
                supported_abis,
                device,
                display,
                fingerprint,
                serial,
                id,
                manufacturer,
                model,
                hardware,
                product,
                tags,
                type,
                codename,
                incremental,
                release,
                sdk_int,
                host,
                user,
                time
        };
        String[] description = {
                "主板-board",
                "Android系统定制商-brand",
                "CPU指令集-supported_abis",
                "设备参数-device",
                "显示屏参数-display",
                "唯一编号-fingerprint",
                "硬件序列号-serial",
                "修订版本列表-id",
                "硬件制造商-manufacturer",
                "版本-model",
                "硬件名-hardware",
                "手机产品名-product",
                "描述Build的变迁-tags",
                "Builder类型-type",
                "当前开发代号-codename",
                "源码控制版本号-incremental",
                "发行版本-release",
                "SDK版本-sdk_int",
                "Host值-host",
                "User名-user",
                "编译时间-time"
        };
        return makeInfoString(description, prop);
    }

    // SystemProperty 包含许多系统配置属性值和参数，很多信息和上面android.os.build获取的值相同
    public static String getSystemPropertyInfo() {
        String os_version = System.getProperty("os.version");
        String os_name = System.getProperty("os.name");
        String os_arch = System.getProperty("os.arch");
        String user_home = System.getProperty("user.home");
        String user_name = System.getProperty("user.name");
        String user_dir = System.getProperty("user.dir");
        String user_timezone = System.getProperty("user.timezone");
        String path_separator = System.getProperty("path.separator");
        String line_separator = System.getProperty("line.separator");
        String file_separator = System.getProperty("file.separator");
        String java_vendor_url = System.getProperty("java.vendor.url");
        String java_class_path = System.getProperty("java.class.path");
        String java_class_version = System.getProperty("java.class.version");
        String java_vendor = System.getProperty("java.vendor");
        String java_version = System.getProperty("java.version");
        String java_home = System.getProperty("java_home");
        String[] prop = {
                os_version,
                os_name,
                os_arch,
                user_home,
                user_name,
                user_dir,
                user_timezone,
                path_separator,
                line_separator,
                file_separator,
                java_vendor_url,
                java_class_path,
                java_class_version,
                java_vendor,
                java_version,
                java_home
        };
        String[] description = {
                "OS版本-os_version",
                "OS名称-os_name",
                "OS架构-os_arch",
                "HOME属性-user_home",
                "Name属性-user_name",
                "Dir属性-user_dir",
                "时区-user_timezone",
                "路径分隔符-path_separator",
                "行分隔符-line_separator",
                "文件分隔符-file_separator",
                "java_vendor_url",
                "java_class_path",
                "java_class_version",
                "java_vendor",
                "java_version",
                "java_home"
        };
        return makeInfoString(description, prop);
    }
}
