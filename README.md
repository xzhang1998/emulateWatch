# Emulating Apple Watch Bubble UI🫧

## Add dependency
```
implementation 'com.github.bumptech.glide:glide:4.15.1' //Image loading tool
```
## The Result
https://user-images.githubusercontent.com/89569765/232271299-6c2977a0-01b5-43eb-9c5d-2ef527fd7080.mov

## Development Process
### 1⃣️ Breakdown the demands
- Interleaved Arrangement: All applications are arranged in rows in an interleaved manner
- Support for Arbitrary Direction Scrolling: Users can scroll in any direction, and the application icons that are closer to the center of the screen gradually enlarge.
- Automatic Locking: When users swipe the screen, application icons automatically reposition based on proximity to the center, ensuring that the icons closest to the center and their surrounding visible icons are adjusted to the correct position.

### 2⃣️ Classes
|      Class    | Description |
| ------------- | ----------- |
| MyAdapter| Bind the data for RecyclerView
| MyLayoutManager| Customizing the arrangement, scrolling, and scaling effects of elements
| Applications | Pojo Class, defines the class for the data that are going to present in the recyclerView
| Position | assisting in custom elements positioning and arrangement within the LayoutManager

### 3⃣️ Methods in LayoutManager
<img width="1798" alt="image" src="https://user-images.githubusercontent.com/89569765/232278269-547e8a5b-7823-486a-825c-e099c2aaadc3.png">

|     Method    | Description |
| ------------- | ----------- |
| onMeasure() | Customize the width and height of the RecyclerView
| onLayoutChildren() | Layout file for arranging items in a custom manner
| relayoutChildren() | Breaking down the layout logic into three steps: 1) Retrieving the items to be laid out 2) Arranging the items 3) Recycling views that are no longer needed
| getNeedLayoutItems() | Breaking down the logic for obtaining items into two steps: 1) Generating all potentially usable items based on the desired arrangement. 2) Retrieving the portion of items that will be displayed on the screen
| initLayoutList() | Generate all potentially usable items, including their positional coordinates, based on the desired arrangement. Specifically, use BFS (Breadth-First Search) to expand from the center of the screen in six equilateral hexagonal directions
| initNeedLayoutItems() | Traverse the result of initLayoutList() and retrieve the item coordinates that are visible within the RecyclerView
| onLayout() | Iterate through the final list of item positions that need to be displayed on the screen, and call layoutDecorated() to layout each item on the screen
| getScale() | Calculate the scaling factor for each item based on its display position. Specifically, use the sine function where items closer to the center have smaller scaling factors, and those farther from the center have larger scaling factors
| scrollHorizontallyBy() | Record the horizontal distance of screen scrolling
| scrollVerticallyBy() | Record the vertical distance of screen scrolling

### 4⃣️ Technical Challenges
1. Designing the hexagonal layout: <br>
1) Need to calculate the horizontal and vertical coordinate relationships from the center of the hexagon to its six vertices, and then expand in six directions from the center using the BFS algorithm. <br>
2）Deduplication is very important for BFS. Need to overwrite the .equals() of class Position to deduplication in Set <br>
3）overwrite .equals(): Due to the involvement of numerous floating-point calculations during the hexagon expansion process, there may be discrepancies in precision. Therefore, in the deduplication process, as long as the distance between the element currently being checked and the elements in its corresponding set does not exceed the item's diameter, it is considered a duplicate. <br>
4）overwrite .hashcode(): Because of the precision issues, to ensure that items at the same position generate the same hashcode and then use the .equals() to do the comparison, take the rounded values of x and y, and then add their respective hashcodes together. <br>

2. Scaling the items: <br>
1）Initially, the getScale() method was based on the element's x and y position to determine its corresponding scale size. However, this approach didn't achieve a smooth scaling effect during scrolling, resulting in abrupt changes in the item's size. <br>
2）If I want the item size to change smoothly based on continuously changing x and y values, the degree of scaling must be calculated based on these x and y values. <br>
3）Observing the Apple Watch UI, I found that items closer to the periphery are scaled significantly, while those closer to the center experience less scaling. Therefore, I need to find a function that meets the criteria of rapid growth followed by rapid decay to achieve the desired scaling effect. <br>
4）chose to use sin() to calculate the horizontal and vertical coordinate values of items

### 5⃣️ "Ah-ha moments" in the development process:
1. Realizing the need to create a custom LayoutManager to achieve more flexible and versatile RecyclerView functionality.
2. Understanding that when the size of the RecyclerView is defined, the placement coordinates of its internal views are based on the top-left corner of the RecyclerView as the origin (0,0).
3. Recognizing the importance of promptly recycling unnecessary views after adding scroll effects to maintain a clean and efficient interface.

### 6⃣️ Optimization
1. When scrolling, maintain equal spacing between elements

## Appendix: Reflective Notes
![IMG_5982](https://user-images.githubusercontent.com/89569765/232280844-45f59dc8-c9b9-43b3-9685-c24e714d8c43.JPG)
![IMG_5981](https://user-images.githubusercontent.com/89569765/232280847-e826dd2c-7490-4ccd-93bc-8ba62e38885a.JPG)
![IMG_5980](https://user-images.githubusercontent.com/89569765/232280851-b1f7d85f-fd7b-49a0-8c9e-4157bf87360d.JPG)
![IMG_5979](https://user-images.githubusercontent.com/89569765/232280855-a6984e12-6813-48b1-9fac-352dc7fad535.JPG)
