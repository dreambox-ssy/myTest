1，APP的安裝
第一種辦法：把編譯后的JackTest.apk拷貝到手機裏面，然後點擊安裝。
第二種辦法：手機鏈接電腦，手機打開ADB調試（如何打開上網搜索），在電腦裏面運行命令終端，輸入adb install JackTest.apk的路徑。
 
2，运行代码的方法
(1)開發环境
該項目使用的是Android Studio 4.2開發；

3，項目工程結構簡要説明
整個項目分成4個module：app,biz_homepage,lib_common,lib_data
app: 程式啓動。
biz_homepage：主頁，包括界面開發以及業務邏輯。
lib_common: 公共library，包括Fragment管理，網絡鏈接，自繪組件等。
lib_data:數據庫的存儲以及操作.
