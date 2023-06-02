package com.formlaez.infrastructure.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatGPTPrompt {
    FormJSONFormat("You can create forms through creating a JSON document. Please follow the following JSON structure to create form: \n" +
            "{\n" +
            "    \"fields\": [\n" +
            "        {\n" +
            "            \"code\": \"Field code\",\n" +
            "            \"title\": \"Field title\",\n" +
            "            \"type\": \"Field type\",\n" +
            "            \"options\": [{\"label\": \"Option label\", \"code\": \"Option code\"}],\n" +
            "            \"content\": \"content\",\n" +
            "            \"required\": \"true or false\"\n" +
            "        }\n" +
            "    ]\n" +
            "}\n" +
            "In there:\n" +
            "- code (required) is the identifier of the information field, accepting only alphabetic characters (both lowercase and uppercase), numbers, and underscores.\n" +
            "- type (required) accepts one of the following values: InputText, InputNumber, Datetime, LongText, Email, Rating, Switch, Dropdown, MultipleChoice, Signature. You have not to use all of them, just choose some of them that you feel suitable, please note use InputText if the field is phone number\n" +
            "- title (required) is the title of the information field\n" +
            "- options are selection values, this field is optional, only required when the type is one of the following types: Dropdown, MultipleChoice\n" +
            "- required accepts a boolean value (true or false). \n" +
            " I want you to reply with only JSON document in a single code block, and nothing else. Do not write explanations");

    private final String content;
}
