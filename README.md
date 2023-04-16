# 模拟apple watch气泡首页效果🫧

## 添加依赖
```
implementation 'com.github.bumptech.glide:glide:4.15.1' //图片加载工具
```
## 效果展示
https://user-images.githubusercontent.com/89569765/232271299-6c2977a0-01b5-43eb-9c5d-2ef527fd7080.mov

## 开发过程
### 1⃣️ 拆解需求
- 排列：所有应用程序按行交错排列，并且最常用的在中心位置
- 支持任意方向滚动：且滚动时靠近屏幕中心位置的图标对应放大
- 自动锁定：当向任意位置随意滑动任意距离，定位到离屏幕中心最近的图标，将该图标及其周围可显示图标

### 2⃣️ 新建类及对应功能
|      Class    | Description |
| ------------- | ----------- |
| MyAdapter| Bind并向RecyclerView输送数据
| MyLayoutManager| 自定义元素在网页的排列方式、滚动和缩放等效果
| Applications | Pojo Class, 定义了将要显示在recyclerView中的数据内容
| Position | 辅助LayoutManager中的自定义元素位置排列

### 3⃣️ LayoutManager中的方法及对应功能
<img width="1798" alt="image" src="https://user-images.githubusercontent.com/89569765/232278269-547e8a5b-7823-486a-825c-e099c2aaadc3.png">

|     Method    | Description |
| ------------- | ----------- |
| onMeasure() | 自定义RecyclerView的宽高
| onLayoutChildren() | 布局文件，把需要放置的item按照自定义方式布局
| relayoutChildren() | 拆解布局逻辑，把布局细分为：1）拿到需要布局的item 2）布局 3）回收不需要的view
| getNeedLayoutItems() | 拆解拿到item的逻辑，分为：1）把所有可能使用的item按照需要排布的方式generate出来 2）取出会显示在屏幕上的部分
| initLayoutList() | 把所有可能使用的item按照需要排布的方式generate出来（包括它们的位置坐标）；具体地，使用BFS，由屏幕中心向等边六边形的六个方向扩散
| initNeedLayoutItems() | 遍历initLayoutList()的结果，把item坐标在recyclerView中能够显示的取出来
| onLayout() | 遍历最终要显示在屏幕上的item位置，调用layoutDecorated(）把item布局到屏幕上
| getScale() | 根据每个item即将显示的位置，计算该item需要缩放的大小；具体地，使用sin函数，离中心越近缩放程度越小，离中心越远，缩放程度越大
| scrollHorizontallyBy() | 记录横向滑动屏幕的距离
| scrollVerticallyBy() | 记录纵向滑动屏幕的距离

### 4⃣️ 开发过程中的技术难点
1. 设计六边形布局的方式：<br>
1）需要计算出六边形中心到六个角的横纵坐标关系，再通过BFS算法从中心向六个方向扩散 <br>
2）BFS中很重要的一点是去重，需要对自定义class Position重写.equals()方法，才能通过Set达到去重的效果 <br>
3）重写.equals(): 由于六边形扩展过程中涉及到很多小数运算，精度会有差距，因此在查重过程中，只要当前正在查重的元素和其对应的set中元素距离不超过item直径即视作重复 <br>
4）重写.hashcode(): 必须使相同位置的item形成一样的hashcode才会进入.equals()，考虑到精度问题，取x,y四舍五入的值，cast为Integer再分别取hashcode相加即可 <br>

2. 缩放item的大小: <br>
1）起初getScale()方法是根据元素x,y的位置返回该item对应缩放大小的，但是这样在进行滑动操作的时候不能够达到平滑缩放的效果，会出现item突然变大/小的情况 <br>
2）如果希望item大小随着不断变动的x,y值而变化，则scale的程度必须是由x,y值参与计算出来的 <br>
3）观察apple watch的表盘，离外围越近的item缩放地程度很大，离中心点越近的item缩放程度比较小，因此需要寻找一个函数满足增长速率先快速增长又快速下降的函数 <br>
4）于是选择了sin()函数，联合item 的横纵坐标值进行计算

### 5⃣️ 开发过程中的 “原来如此” 时刻
1. 需要自定义layoutManager才能够实现更为灵活多变的recycler view功能
2. 如果规定了recycler view的大小，则其内部view的放置坐标是以recycler view的左上角作为原点(0,0)的
3. 加入滚动效果后必须及时回收不需要的view以保持页面的干净

### 6⃣️ 优化方向
1. 滚动时间距保持相等

## 附录：思考笔记
![IMG_5982](https://user-images.githubusercontent.com/89569765/232280844-45f59dc8-c9b9-43b3-9685-c24e714d8c43.JPG)
![IMG_5981](https://user-images.githubusercontent.com/89569765/232280847-e826dd2c-7490-4ccd-93bc-8ba62e38885a.JPG)
![IMG_5980](https://user-images.githubusercontent.com/89569765/232280851-b1f7d85f-fd7b-49a0-8c9e-4157bf87360d.JPG)
![IMG_5979](https://user-images.githubusercontent.com/89569765/232280855-a6984e12-6813-48b1-9fac-352dc7fad535.JPG)
