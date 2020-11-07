package com.project.client.constants;


/**
 * Definitions for directories
 */
public enum Directory {

    ROOT("/com/project/client"),

    ASSETS_FOLDER(ROOT.path + "/assets"),


    PAGE_FOLDER(ASSETS_FOLDER.path + "/pages");

    private final String path;

    Directory(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
