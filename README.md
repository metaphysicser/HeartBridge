# HeartBridge



## 数据包头协议

为了识别不同信息的功能和有关信息，现定义本系统中的数据包头协议：



一般格式：

消息类型&消息来源&转发对象



### 在服务器注册



register&name&null



在登陆后，向服务器注册自己的姓名



### 登陆验证

客户端向服务端发送用户输入的账号和密码，服务端查询数据库判断

check&null&null

消息类型：check

消息来源：null

转发对象：null



### 发送文字信息给个人

people_send&sender&receiver

消息类型：people_send

消息来源：sender

转发对象：receiver