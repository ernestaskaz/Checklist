#Simple Checklist

## Description

A simple check list app that lets you create specific checklists and populate them with checkitems.

## Technologies used

LiveData, ViewModel, RecycleView, RoomDB, ListAdapter with DiffUtil.

## How it works


- Listview shows created Checklists in a recyclerView with total checkitems in a specific list and items done/left to do. This information is also represented in a progress bar.
- On specific Checklist click, user is redirected to Checkitems list of said Checklist.
- onSwipe deletes Checklist and all of its items.
- User can quickly add new Checklist with a floating action button at the bottom.

![Screenshot_20220107-113112_Checklist](https://user-images.githubusercontent.com/6989478/148769621-3aa950fe-b401-442c-bcdd-8473a3191205.jpg)


- In this view, user can quickly add Checkitem. Newest added item always takes position 0(top) in the recyclerView. 
- onSwipe deletes Checkitem.
- onChecked item changes position to bottom, so only unchecked items (still to-do) are conveniently at the top.
- provides Toast on item checked.
- onUncheckitem returns to its original position.
- additional function to uncheck ALL items in a specific checklist.

![Screenshot_20220107-194312_Checklist](https://user-images.githubusercontent.com/6989478/148769622-c588236c-b341-401c-935b-267965566362.jpg)

