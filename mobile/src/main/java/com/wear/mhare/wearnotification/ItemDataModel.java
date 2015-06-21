package com.wear.mhare.wearnotification;

import java.util.Date;

/**
 * Created by mphare on 6/21/2015.
 */
public class ItemDataModel
{
  public ItemDataModel(String itemName, String itemType, Date itemDate)
  {
    this.itemName = itemName;
    this.itemDate = itemDate;
    this.itemType = itemType;
  }

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

  public Date getItemDate()
  {
    return itemDate;
  }

  public void setItemDate(Date itemDate)
  {
    this.itemDate = itemDate;
  }

  String itemName;
  String itemType;
  Date   itemDate;
}
