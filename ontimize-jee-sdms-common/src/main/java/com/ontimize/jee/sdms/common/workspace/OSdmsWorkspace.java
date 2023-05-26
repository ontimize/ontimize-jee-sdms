package com.ontimize.jee.sdms.common.workspace;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;



/**
 * Class that represents a workspace.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OSdmsWorkspace {

    public static final String DEFAULT = "default";

    /** The name of the workspace. */
    private String name = OSdmsWorkspace.DEFAULT;

    /** The value of the workspace. */
    private String value;

    /** The patterns of the workspace. */
    private List<String> patterns = new ArrayList<>();

}
