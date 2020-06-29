# Mqtt-app
MQTT App

Bài tập lớn : Mạng cảm biến không dây

Đề tài : Assigment 6* MQTT & MQTT-SN

Nhóm 15 :
1. Trần Ngọc Trâm  			:1713587
2. Phạm Phương Thanh   	:1713112
3. Võ Trần Ngọc Lượng  	:1712095
-------------------------------------
# Prerequisites
* Cài đặt Paho Android Service thông qua Gradle bằng cách thêm các dòng sau vào Build.gradle(module:app)
```
repositories {
    maven {
    	url "https://repo.eclipse.org/content/repositories/paho-releases/"
    }
}


dependencies {
    implementation('org.eclipse.paho:org.eclipse.paho.android.service:1.0.2') {
        exclude module: 'support-v4'
    }
//		Đối với phiên bản mới của Android Studio thì cần thêm dòng dưới
//		implementation('androidx.localbroadcastmanager:localbroadcastmanager:1.0.0')
}
```

------------------------------------
# Các file thành phần chính:
## Activity
* MainActivity.java & activity_main.xml

Giao diện chính, thực hiện kết nối Mqtt, Pub/Sub 

* AdvancedOptions.java & activity_advanced_options.xml

Giao diện phụ, thay đổi địa chỉ Broker, Port, Topic pub/sub
## Notification Service
* MainService.java

Cho phép chạy Service thông báo 
* NotifyMng.java

Gồm các hàm thực hiện tạo kênh thông báo và hiển thị thông báo

