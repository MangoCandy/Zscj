package com.hnxind.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/22.
 */
public class Grid{
    public static String GRID_NAME="gridName";
    public static String GRID_PIC="gridPic";
    public static String GRID_ID="gridId";

    private String gridName;
    private String gridPic;
    private String gridId;

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getGridPic() {
        return gridPic;
    }

    public void setGridPic(String gridPic) {
        this.gridPic = gridPic;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }
}
