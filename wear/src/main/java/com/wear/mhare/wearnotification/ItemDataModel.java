package com.wear.mhare.wearnotification;

/**
 * Created by mphare on 6/21/2015.
 */
public class ItemDataModel
{
  String itemName;
  String itemType;
  long   itemDate;

  public String getItemName()
  {
    return itemName;
  }

  public void setItemName(String itemName)
  {
    this.itemName = itemName;
  }

  public String getItemType()
  {
    return itemType;
  }

  public void setItemType(String itemType)
  {
    this.itemType = itemType;
  }

  public long getItemDate()
  {
    return itemDate;
  }

  public void setItemDate(long itemDate)
  {
    this.itemDate = itemDate;
  }
}
