package com.formlaez.infrastructure.util;

public class AttachmentUtils {

    private static final String DOCUMENT_TEMPLATE_CLOUD_LOCATION = "Workspaces/{workspaceCode}/Forms/{formCode}/DocumentTemplates";

    public static String documentTemplateLocation(String workspaceCode, String formCode) {
        return DOCUMENT_TEMPLATE_CLOUD_LOCATION
                .replace("{workspaceCode}", workspaceCode)
                .replace("{formCode}", formCode);
    }
}
