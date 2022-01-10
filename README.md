#Simple Checklist

## Description

A simple check list app that lets you create specific checklists and populate them with checkitems.

## Technologies used

LiveData, ViewModel, RecycleView, RoomDB, ListAdapter with DiffUtil.

## How it works

provides Toast on item checked.

ListView shows Checklists with total checkitems in a specific list and items done/left to do. This information is also represented in a progress bar.

newest added item always takes position 0 in the recyclerView. 
