尚硅谷发布的“尚品甄选”微服务项目的具体实现，内有两个端。admin管理端、user客户端。管理端是一个单体项目，需要用户登录之后才能访问。具体的功能是显示商品的一些具体操作，包括基本的增、删、改、查。后台管理人员的一些操作。
user客户端就是一个商城项目，包括商品的购买，添加购物车，修改客户的信息等一系列操作。本次是使用微服务结构。使用spring cloud和spring cloud Alibaba的一些组件。